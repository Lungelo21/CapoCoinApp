package com.example.capocoinapp.Services

//Import to call the Category entity
import com.example.capocoinapp.data.entities.Category

//
import com.example.capocoinapp.data.dao.CategoryDAO

//Import to call coroutine
import kotlinx.coroutines.flow.Flow

//Import to call material icons for the base icons for categories
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart

//Import to call image vector for icons
import androidx.compose.ui.graphics.vector.ImageVector

public class CategoryService(private val categoryDao: CategoryDAO) {
    val baseIcons = listOf(
        "Food",
        "Movie",
        "Groceries",
        "Shopping",
        "Transport",
        "Flight",
        "Education",
        "Gift",
        "Subscriptions",
        "Gym",
        "Healthcare",
        "House payments",
        "Salary"
    )

    fun getAllowedIcons(): List<String> = baseIcons

    fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()

    //Create suspended function to prevent duplicated names of categories
    suspend fun createCategory(category: Category) {
        //Insert the category by calling dao function
        categoryDao.insertCategory(category)
    }

    fun getIconForName(name: String): ImageVector {
        return when (name) {
            // Icons from your photo
            "Food" -> Icons.Default.Restaurant
            "Movie" -> Icons.Default.Movie
            "Groceries" -> Icons.Default.ShoppingCart
            "Shopping" -> Icons.Default.ShoppingBag
            "Transport" -> Icons.Default.DirectionsBus
            "Flight" -> Icons.Default.Flight
            "Education" -> Icons.Default.School
            "Gift" -> Icons.Default.CardGiftcard
            "Subscriptions" -> Icons.Default.Payments
            "Gym" -> Icons.Default.FitnessCenter
            "Healthcare" -> Icons.Default.MedicalServices
            "House payments" -> Icons.Default.Construction
            "Salary" -> Icons.Default.AttachMoney

            else -> throw IllegalArgumentException("Icon name $name not found in list of icons")
        }
    }
}