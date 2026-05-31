package com.example.capocoinapp.Services

//Import to call the Category entity

//

//Import to call coroutine

//Import to call material icons for the base icons for categories

//Import for all base icons that will be selectable by the user
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.capocoinapp.data.dao.CategoryDAO
import com.example.capocoinapp.data.entities.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


public class CategoryService(private val categoryDao: CategoryDAO) {

    val transactionTypes = listOf("Expense", "Income")

    //Using mapOf(similar to c# dictionary) to hold the key base icons
    val baseIcons = mapOf(
        "Food" to Icons.Default.Restaurant,
        "Movie" to Icons.Default.Movie,
        "Groceries" to Icons.Default.ShoppingCart,
        "Shopping" to Icons.Default.ShoppingBag,
        "Transport" to Icons.Default.DirectionsBus,
        "Flight" to Icons.Default.Flight,
        "Education" to Icons.Default.School,
        "Gift" to Icons.Default.CardGiftcard,
        "Subscriptions" to Icons.Default.Payments,
        "Gym" to Icons.Default.FitnessCenter,
        "Healthcare" to Icons.Default.MedicalServices,
        "House payments" to Icons.Default.Construction,
        "Salary" to Icons.Default.AttachMoney,
        "Sports" to Icons.Default.SportsSoccer,
        "Hobbies" to Icons.Default.AutoAwesome,
        "Culture" to Icons.Default.Museum,
        "Donations" to Icons.Default.Handshake,
        "Self Care" to Icons.Default.Spa,
        "Gaming" to Icons.Default.SportsEsports,
        "Pets" to Icons.Default.Pets,
        "Music" to Icons.Default.MusicNote,
        "Tech" to Icons.Default.Devices,
        "Savings" to Icons.Default.Savings,
        "Car Payments" to Icons.Default.DirectionsCar,
        "Celebration" to Icons.Default.Celebration
    )
    /*
     * Author: GeeksforGeeks
     * Link: https://www.geeksforgeeks.org/kotlin/kotlin-map-mapof/
     * DateAccessed: 27/04/2026
     * */

    val selectableColours = mapOf(
        "Grey"  to "#4A4A4A",
        "Deep Purple" to "#6A1B9A",
        "Vibrant Green" to "#00833F",
        "Neon Pink" to "#F062D0",
        "Dark Navy" to "#2D344B",
        "Teal" to "#0097A7",
        "Mustard Gold" to "#A68930",
        "Dark Chocolate" to "#3E2723",
        "Deep Red" to "#880E4F",

        "Emerald" to "#2E7D32",
        "Sky Blue" to "#0288D1",
        "Amethyst" to "#9C27B0",
        "Burnt Orange" to "#BF360C",
        "Slate" to "#455A64",
        "Lavender" to "#B39DDB",
        "Rose" to "#AD1457"
    )
    /*
     * Author: GeeksforGeeks
     * Link: https://www.geeksforgeeks.org/kotlin/kotlin-map-mapof/
     * DateAccessed: 27/04/2026
     * */

    //Function to get the icons
    fun getIcon(iconName: String): ImageVector
    {
        return baseIcons[iconName] ?: Icons.Default.QuestionMark
    }

    //Function to get the colour for the icon
    fun getColour(colour: String): String
    {
        return selectableColours[colour] ?: "#000000"
    }

    //Using a Query to get the full list of available categories
    fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()

    fun getCategoryById(id: Int): Flow<Category> = categoryDao.getCategoryById(id)

    // Fetches the category colour and icon from the id
    fun getCategoryUIById(id: Int): Flow<Pair<String, ImageVector>> {
        return categoryDao.getCategoryById(id).map { category ->
            val colour = getColour(category.categoryColour ?: "")
            val icon = getIcon(category.categoryIcon ?: "")
            Pair(colour, icon)
        }
    }

    //Create suspended function to prevent duplicated names of categories
    suspend fun createCategory(category: Category) {
        //Insert the category by calling dao function
        categoryDao.insertCategory(category)
    }

    // suspended function to update categories in the
    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    //Having a method used to populate the base icons that will be seen by all users upon entry to the app
    suspend fun populateDefaultCategories() {

        //Saving the four base categories
        val defaultCategories = listOf(
            Category(
                transactionType = "Income",
                categoryTitle = "Salary",
                categoryIcon = "Salary",
                categoryColour = "Dark Navy",
                minBudget = 0.0,
                maxBudget = 0.0
            ),
            Category(
                transactionType = "Expense",
                categoryTitle = "Food",
                categoryIcon = "Food",
                categoryColour = "Grey",
                minBudget = 0.0,
                maxBudget = 0.0
            ),
            Category(
                transactionType = "Expense",
                categoryTitle = "Groceries",
                categoryIcon = "Groceries",
                categoryColour = "Vibrant Green",
                minBudget = 0.0,
                maxBudget = 0.0
            ),
            Category(
                transactionType = "Expense",
                categoryTitle = "Transport",
                categoryIcon = "Transport",
                categoryColour = "Slate",
                minBudget = 0.0,
                maxBudget = 0.0
            )
        )

        //Create the categories so that the user will be able to see them upon running the code
        defaultCategories.forEach { createCategory(it) }
    }

}