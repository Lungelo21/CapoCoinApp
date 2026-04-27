package com.example.capocoinapp.data.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.data.dao.CategoryDAO
import com.example.capocoinapp.data.entities.Category
import kotlinx.coroutines.flow.Flow
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

    // Function to add a new category (e.g., "Salary" or "Groceries")
    fun addCategory(type: String, categoryTitle: String, categoryColour: String, categoryIcon: String) {
        viewModelScope.launch {
            message = when {
                type.isBlank() -> "Please select a transaction type for category"
                categoryTitle.isBlank() -> "Please enter a category title"
                categoryColour.isBlank() -> "Please select a colour"
                categoryIcon.isBlank() -> "Please select an icon"
                else -> {
                    val newCategory = Category(
                        transactionType = type,
                        categoryTitle = categoryTitle,
                        categoryColour = categoryColour,
                        categoryIcon = categoryIcon
                    )
                    service.createCategory(newCategory)
                    "Category: '$categoryTitle' was added!"
                }
            }
        }
    }

    // Get all categories to populate your dropdown/selection menus
    fun getAllCategories(): Flow<List<Category>> {
        return service.getAllCategories()
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