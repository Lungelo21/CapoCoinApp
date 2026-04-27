package com.example.capocoinapp.data.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capocoinapp.data.dao.CategoryDAO
import com.example.capocoinapp.data.entities.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val dao: CategoryDAO
) : ViewModel() {

    // UI Feedback message
    var message by mutableStateOf("")
        private set

    fun clearMessage() {
        message = ""
    }

    // Function to add a new category (e.g., "Salary" or "Groceries")
    fun addCategory(categoryTitle: String, categoryColour: String, categoryIcon: String,minBudget:Double,maxBudget:Double) {
        viewModelScope.launch {
            message = when {
                categoryTitle.isBlank() -> "Please enter a category title"
                categoryColour.isBlank() -> "Please select a colour"
                categoryIcon.isBlank() -> "Please select an icon"
                else -> {
                    val newCategory = Category(
                        categoryTitle = categoryTitle,
                        categoryColour = categoryColour,
                        categoryIcon = categoryIcon,

                        // Added to change in More User Budget
                        minBudget = minBudget,
                        maxBudget = maxBudget
                    )
                    dao.insertCategory(newCategory)
                    "Category: '$categoryTitle' was added!"
                }
            }
        }
    }

    // Get all categories to populate your dropdown/selection menus
    fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories()
    }
}

// Factory to inject the CategoryDAO
class CategoryViewModelFactory(private val dao: CategoryDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(dao) as T
        }
        throw IllegalArgumentException("Error Occurred")
    }
}