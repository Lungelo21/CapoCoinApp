package com.example.capocoinapp.data.ViewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.Supabase.SupabaseClient
import com.example.capocoinapp.Utils.isInternetAvailable
import com.example.capocoinapp.data.entities.Category
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val service: CategoryService,
    private val application: Application,
    val achievementViewModel: AchievementViewModel
) : ViewModel() {

    // UI Feedback message
    var message by mutableStateOf("")
        private set

    fun clearMessage() {
        message = ""
    }

    //Using init to make sure this will be actioned as the code is first run
    /*
     * Author: Ranjeet
     * Link: https://medium.com/@ranjeet123/init-block-in-kotlin-518b050cada1
     * DateAccessed: 29/04/2026
     * */
    init
    {
        viewModelScope.launch {

            val currentUserID =
                try
                {
                    SupabaseClient.client.auth.currentUserOrNull()?.id
                }
                catch (e: Exception)
                {
                    //Prompting logcat error message
                    Log.e("CategoryVMCheck", "Error fetching authenticated User ID: ${e.message}")

                    //Setting the currentUserID to null
                    null
                }


            //Getting the current categories first stored in the DB
            val currentCategories = service.getAllCategories().first()

            //Checking if the currently saved db entries is empty -> doesn't exist
            if(currentCategories.isEmpty())
            {
                //Checking if currentUserID is null so that correct populating of remote supabase storage is completed
                if(currentUserID != null) {
                    //Log for checking when default categories are being populated into the db
                    Log.d(
                        "CategoryVMCheck",
                        "Database is empty. Populating default categories for user with ID: ${currentUserID}"
                    )

                    //Calling the service method to populate defaults
                    service.populateDefaultCategories(currentUserID)

                    // Fetch default populated categories to load to Supabase
                    val localDefaultCategories = service.getAllCategories().first()

                    viewModelScope.launch {

                        var isSynced = false

                        while (!isSynced) {
                            if (application.isInternetAvailable()) {
                                try {
                                    Log.d(
                                        "CategoryVMCheck",
                                        "Syncing locally populated categories to Supabase"
                                    )

                                    // Send populated list to Supabase
                                    SupabaseClient.client.postgrest["categories"].upsert(
                                        localDefaultCategories
                                    )

                                    //Prompting user via log cat for successful sync
                                    Log.d(
                                        "CategoryVMCheck",
                                        "Supabase successfully synced with all default categories!"
                                    )

                                    //Ending loop after sync is completed
                                    isSynced = true
                                } catch (e: Exception) {
                                    Log.e(
                                        "CategoryVMCheck",
                                        "Error while syncing locally populated default categories: ${e.message}. Retrying after 10 seconds."
                                    )

                                    delay(10000)//Delay by 10 seconds
                                }
                            } else {
                                Log.d(
                                    "CategoryCategorySyncCheck",
                                    "Currently Offline. Waiting for Internet connection to sync defaults categories"
                                )

                                delay(5000) //Delay for 5 seconds while checking for Internet Connection
                            }
                        }
                    }
                }
                else
                {
                    //Prompt logcat errors
                    Log.w("CategoryVMCheck", "Database is empty, user is currently offline/unauthenticated. Default category population stopped to maintain FK constraints ")
                }
            }
            else
            {
                //Else if the current categories isn't empty, prompt the log that there is
                //no need to populate the database with default categories
                Log.d("CategoryVMCheck", "Categories already exist. No population of categories will occur")
            }

            Log.d("CategoryVMCheck", "Syncing RoomDB to Supabase DB")

            //Launch co routine to sync remote with room db (assuming users made entries while offline)
            viewModelScope.launch{
                var isSynced = false

                while(!isSynced)
                {
                    if(application.isInternetAvailable())
                    {
                        try {
                            Log.d("CategorySyncCheck", "Syncing local (Offline) data to Supabase")

                            //Upserting Category entries
                            SupabaseClient.client.postgrest["categories"].upsert(currentCategories)
                            {
                                //If duplicate is found relate to the category title and userID
                                onConflict = "categoryTitle,userID"
                            }

                            Log.d("CategorySyncCheck", "Successfully synced Supabase with Local data")

                            //Setting to true to end loop because of successful sync
                            isSynced = true
                        }
                        catch (e: Exception)
                        {
                            Log.e("CategorySyncCheck", "Error while syncing, attempting again after 10 seconds. ${e.message}.")

                            //Attempting sync after 10 second delay
                            delay(10000)
                        }
                    }
                    else
                    {
                        Log.d("CategorySyncCheck", "Currently offline. Waiting for Internet connection before attempting to sync...")

                        //5 second delay for securing Internet Connection to sync
                        delay(5000)
                    }
                }
            }

            try {
                if(application.isInternetAvailable()) {
                    //Retrieve the current user ID
                    val currentUserID = SupabaseClient.client.auth.currentUserOrNull()?.id

                    //Checking if that ID is not null (authenticated)
                    if(currentUserID != null){

                        //Retrieve the categories filtered by the authenticated ID
                        val supabaseCategories = SupabaseClient.client.postgrest["categories"]
                            .select{
                                filter{
                                    eq("userID", currentUserID)
                            }
                        }.decodeList<Category>()

                        //Checking is categories isn't empty
                        if (supabaseCategories.isNotEmpty()) {
                            Log.d(
                                "CategoryVMCheck",
                                "Found ${supabaseCategories.size} categories. Syncing Room to Supabase"
                            )

                            //Get current local categories
                            val localCategories = service.getAllCategories().first()

                            // Updates existing records and inserts new ones seamlessly
                            supabaseCategories.forEach { remoteCategory ->
                                //Check if the transaction already exists
                                var existsLocally = localCategories.any { localCategory ->
                                    localCategory.categoryTitle.equals(remoteCategory.categoryTitle, ignoreCase = true)
                                    && localCategory.userID == remoteCategory.userID
                                }

                                if(!existsLocally)
                                {
                                    Log.d("CategoryVMCheck", "Inserting new remote category: ${remoteCategory.categoryTitle}")

                                    //Creating the category on the remote
                                    service.createCategory(remoteCategory)
                                }
                                else
                                {
                                    Log.d("CategoryVMCheck", "Category '${remoteCategory.categoryTitle}' already exists locally. Not creating duplicated categories.")
                                }
                            }

                            Log.d("CategoryVMCheck", "Successfully synced from remote to local!")
                        }
                    }
                    else
                    {
                        Log.w("CategoryVMCheck", "No current users found.")
                    }
                }
            }
            catch (e: Exception)
            {
                // Fails silently if user is offline, allowing them to use existing local data
                Log.e("CategoryVMCheck", "Sync failed: ${e.message}")
            }
        }
    }

    // Function to add a new category (e.g., "Salary" or "Groceries")
    fun addCategory(type: String, categoryTitle: String, categoryColour: String, categoryIcon: String, minBudget: Double, maxBudget: Double, userID: String) {
            viewModelScope.launch {
                message = when {
                    type.isBlank() -> "Please select a transaction type for category"
                    categoryTitle.isBlank() -> "Please enter a category title"
                    categoryColour.isBlank() -> "Please select a colour"
                    categoryIcon.isBlank() -> "Please select an icon"
                    else -> {
                        try {
                            val newCategory = Category(
                                transactionType = type,
                                categoryTitle = categoryTitle,
                                categoryColour = categoryColour,
                                categoryIcon = categoryIcon,

                                // Added to change in More User Budget
                                minBudget = minBudget,
                                maxBudget = maxBudget,

                                userID = userID
                            )
                            //Log service calling dao method
                            Log.d(
                                "CategoryVMCheck",
                                "Attempting service.createCategory..."
                            )

                            //service calling createCategory method from dao
                            service.createCategory(newCategory)

                            //Check for Achievement 3: Baby Steps

                            //Sourcing all categories
                            val allCategories = service.getAllCategories().first()

                            //Checking if the number of categories is 5
                            if (allCategories.size == 5)
                            {
                                //Calling the method through the AchievementViewModel with the parsed Achievement title
                                achievementViewModel.unlockAchievement("Baby Steps")
                            }

                            viewModelScope.launch {

                                var uploaded = false

                                while (!uploaded)
                                {
                                    if (application.isInternetAvailable())
                                    {
                                        try
                                        {
                                            //Inserting category to Supabase
                                            SupabaseClient.client.postgrest["categories"].upsert(newCategory)
                                            {
                                                //If duplicate is found relate to the category title and userID
                                                onConflict = "categoryTitle,userID"
                                            }

                                            Log.d("CategorySyncCheck", "Successfully synced custom category to Supabase.")

                                            uploaded = true //Ending loop
                                        }
                                        catch (e: Exception)
                                        {
                                            Log.e("CategorySyncCheck", "Supabase category insert failed, retrying in 10s: ${e.message}")

                                            delay(10000) //Delaying insert by 10 seconds to
                                                                    // see if Internet connection can be found to
                                                                    // successfully insert to Supabase
                                        }

                                    }
                                    else
                                    {
                                        Log.d("CategorySyncCheck", "Offline mode. Retrying category connection check in 5 seconds...")

                                        delay(5000)//Delaying check by 5 seconds
                                    }
                                }
                            }

                            //Prompting user of successful category entry into db
                            "Category: '$categoryTitle' was added!"
                        }
                        catch (e: Exception)
                        {
                            //Logging failed insertion into db
                            Log.d("CategoryVMCheck", "Database Insert Failed: ${e.message}")

                            //Prompting error message
                            "Error while adding Category: '$categoryTitle'" // Returns error message
                        }
                    }
                }
            }
    }

    // Get all categories to populate your dropdown/selection menus
    fun getAllCategories(): Flow<List<Category>> {
        return service.getAllCategories()
    }

    fun getCategoryById(id: Int): Flow<Category> {
        return service.getCategoryById(id)
    }

    //Function to get the icons
    fun getIcon(iconName: String): ImageVector {
        return service.getIcon(iconName)
    }

    //Function to get the colour for the icon
    fun getColour(colour: String): String {
        return service.getColour(colour)
    }

    fun getCategoryUIById(id: Int): Flow<Pair<String, ImageVector>> {
        return service.getCategoryUIById(id)
    }


    // Update fo the User Budget to Change Min and Max Budget Goals
    fun updateCategoryBudget(
        category: Category,
        minBudget: Double,
        maxBudget: Double
    ) {
        viewModelScope.launch {
            val updatedCategory = category.copy(
                minBudget = minBudget,
                maxBudget = maxBudget
            )
            try
            {
                service.updateCategory(updatedCategory)

                message = "Budget updated"

                Log.d("CategoryVMCheck",
                    "Local budget updated successfully for: ${category.categoryTitle}")

                //Co routine to sync after updating budget
                viewModelScope.launch {
                    var updatedRemote = false

                    while (!updatedRemote)
                    {
                        if (application.isInternetAvailable())
                        {
                            try
                            {
                                //Updating/Inserting Category after changing budget
                                SupabaseClient.client.postgrest["categories"]
                                    .update(
                                        mapOf(
                                            "minBudget" to minBudget,
                                            "maxBudget" to maxBudget
                                        )
                                    ) {
                                        filter {
                                            eq("categoryID", category.categoryID)
                                            eq("userID", category.userID)
                                        }
                                    }

                                Log.d(
                                    "CategorySyncCheck",
                                    "Successfully synced updated budget for '${category.categoryTitle}' to Supabase.")

                                message = "Budget updated successfully"

                                updatedRemote = true //Ending loop
                            }
                            catch (e: Exception)
                            {
                                Log.e("CategorySyncCheck", "Update Failed, retrying in 10 seconds: ${e.message}")
                                delay(10000) //Delaying upsert by 10 seconds to
                                                        // see if Internet connection can be found to
                                                        // successfully upsert to Supabase
                            }
                        }
                        else
                        {
                            Log.d("CategorySyncCheck", "Offline. Waiting for internet connection to sync budget updates for '${category.categoryTitle}'...")
                            delay(5000) // Testing after 5 seconds if an Internet Connection is found
                        }
                    }
                }
            }
            catch (e: Exception)
            {
                Log.e("CategoryVMCheck", "Failed to update budget locally: ${e.message}")
                message = "Failed to update budget locally."
            }
        }
    }
}


// Factory to inject the CategoryDAO
class CategoryViewModelFactory(private val service: CategoryService, private val achievementViewModel: AchievementViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {

            // Extract the Application context via the APPLICATION_KEY
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(service, application, achievementViewModel) as T
        }
        throw IllegalArgumentException("Error Occurred")
    }
}