package com.example.capocoinapp.data.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.Supabase.SupabaseClient
import com.example.capocoinapp.data.entities.Category
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val service: CategoryService
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
                Log.d("ViewModelCheck", "Database is empty. Checking Supabase for existing cloud stored categories")

                try{
                    val supabaseCategories = SupabaseClient.client.postgrest["categories"].select()
                        .decodeList<Category>()

                    if(supabaseCategories.isNotEmpty())
                    {
                        Log.d("ViewModelCheck", "Found ${supabaseCategories.size} categories. Syncing Room to Supabase DB.")

                        // Loop through and insert remotely stored category into Room DB via service call
                        supabaseCategories.forEach {
                            service.createCategory(it)
                        }
                    }
                    else
                    {
                        Log.d("ViewModelCheck", "No remote categories found. Populating default local categories.")
                        // Calling the service method to populate defaults if cloud is also empty
                        service.populateDefaultCategories()
                    }
                }
                catch (e: Exception){
                    Log.e("ViewModelCheck", "Error while syncing to Supabase: ${e.message}. Populating defaults categories.")

                    //Calling the service method to populate defaults
                    service.populateDefaultCategories()
                }

            }
            else
            {
                //Else if the current categories isn't empty, prompt the log that there is
                //no need to populate the database with default categories
                Log.d("ViewModelCheck", "Categories already exist. No population of categories will occur")
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

                            //Inserting category to Supabase
                            SupabaseClient.client.postgrest["categories"].insert(newCategory)

                            //logging successful db entry
                            android.util.Log.d("ViewModelCheck", "Database Insert Successful:")

                            //Prompting user of successful category entry into db
                            "Category: '$categoryTitle' was added!"
                        } catch (e: Exception) {
                            //Logging failed insertion into db
                            android.util.Log.d(
                                "ViewModelCheck",
                                "Database Insert Failed: ${e.message}"
                            )

                            //Prompting error message
                            "Category: '$categoryTitle' failed to be added!" // Returns error message
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
            service.updateCategory(updatedCategory)
            message = "Budget updated"

        }
    }
}


// Factory to inject the CategoryDAO
class CategoryViewModelFactory(private val service: CategoryService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(service) as T
        }
        throw IllegalArgumentException("Error Occurred")
    }
}