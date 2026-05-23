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
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val service: CategoryService,
    private val application: Application
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
            //Getting the current categories first stored in the DB
            val currentCategories = service.getAllCategories().first()

            //Checking if the currently saved db entries is empty -> doesn't exist
            if(currentCategories.isEmpty())
            {
                //Log for checking when default categories are being populated into the db
                Log.d("ViewModelCheck", "Database is empty. Populating default categories for the user to use")

                //Calling the service method to populate defaults
                service.populateDefaultCategories()

                // Fetch default populated categories to load to Supabase
                val localDefaultCategories = service.getAllCategories().first()

                try
                {
                    Log.d("ViewModelCheck", "Syncing locally populated categories to Supabase")

                    // Send populated list to Supabase
                    SupabaseClient.client.postgrest["categories"].insert(localDefaultCategories)

                    Log.d("ViewModelCheck", "Supabase successfully synced with all default categories!")
                }
                catch (e: Exception)
                {
                    Log.e("ViewModelCheck", "Error while syncing locally populated default categories: ${e.message}")
                }
            }
            else
            {
                //Else if the current categories isn't empty, prompt the log that there is
                //no need to populate the database with default categories
                Log.d("ViewModelCheck", "Categories already exist. No population of categories will occur")
            }

            Log.d("ViewModelCheck", "Syncing RoomDB to Supabase DB")

            try {
                val supabaseCategories = SupabaseClient.client.postgrest["categories"].select()
                    .decodeList<Category>()

                if (supabaseCategories.isNotEmpty())
                {
                    Log.d("ViewModelCheck", "Found ${supabaseCategories.size} categories. Syncing Room to Supabase")

                    // Updates existing records and inserts new ones seamlessly
                    supabaseCategories.forEach {
                        service.createCategory(it)
                    }

                    Log.d("ViewModelCheck", "Successfully synced to remote!")
                }
            }
            catch (e: Exception)
            {
                // Fails silently if user is offline, allowing them to use existing local data
                Log.e("ViewModelCheck", "Sync failed: ${e.message}")
            }
        }
    }

    // Function to add a new category (e.g., "Salary" or "Groceries")
    fun addCategory(type: String, categoryTitle: String, categoryColour: String, categoryIcon: String, minBudget: Double, maxBudget: Double) {
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
                                maxBudget = maxBudget
                            )
                            //Log service calling dao method
                            android.util.Log.d(
                                "ViewModelCheck",
                                "Attempting service.createCategory..."
                            )

                            //service calling createCategory method from dao
                            service.createCategory(newCategory)

                            viewModelScope.launch {

                                var uploaded = false

                                while (!uploaded)
                                {
                                    if (application.isInternetAvailable())
                                    {
                                        try
                                        {
                                            //Inserting category to Supabase
                                            SupabaseClient.client.postgrest["categories"].insert(newCategory)

                                            Log.d("SyncCheck", "Successfully synced custom category to Supabase.")

                                            uploaded = true //Ending loop
                                        }
                                        catch (e: Exception)
                                        {
                                            Log.e("SyncCheck", "Supabase category insert failed, retrying in 10s: ${e.message}")

                                            delay(10000) //Delaying insert by 10 seconds to
                                                                    // see if Internet connection can be found to
                                                                    // successfully insert to Supabase
                                        }

                                    }
                                    else
                                    {
                                        Log.d("SyncCheck", "Offline mode. Retrying category connection check in 5 seconds...")

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
                            android.util.Log.d("ViewModelCheck", "Database Insert Failed: ${e.message}")

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

                Log.d("ViewModelCheck", "Local budget updated successfully for: ${category.categoryTitle}")

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
                                SupabaseClient.client.postgrest["categories"].upsert(updatedCategory)

                                Log.d("SyncCheck", "Successfully synced updated budget for '${category.categoryTitle}' to Supabase.")

                                updatedRemote = true //Ending loop
                            }
                            catch (e: Exception)
                            {
                                Log.e("SyncCheck", "Update Failed, retrying in 10 seconds: ${e.message}")
                                delay(10000) //Delaying upsert by 10 seconds to
                                                        // see if Internet connection can be found to
                                                        // successfully upsert to Supabase
                            }
                        }
                        else
                        {
                            Log.d("SyncCheck", "Offline. Waiting for internet connection to sync budget updates for '${category.categoryTitle}'...")
                            delay(5000) // Testing after 5 seconds if an Internet Connection is found
                        }
                    }
                }
            }
            catch (e: Exception)
            {
                Log.e("ViewModelCheck", "Failed to update budget locally: ${e.message}")
                message = "Failed to update budget locally."
            }
        }
    }
}


// Factory to inject the CategoryDAO
class CategoryViewModelFactory(private val service: CategoryService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {

            // Extract the Application context via the APPLICATION_KEY
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(service, application) as T
        }
        throw IllegalArgumentException("Error Occurred")
    }
}