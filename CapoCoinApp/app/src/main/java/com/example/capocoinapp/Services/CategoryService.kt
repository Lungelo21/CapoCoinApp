package com.example.capocoinapp.Services

//Import to call the Category entity
import com.example.capocoinapp.data.entities.Category

//
import com.example.capocoinapp.data.dao.CategoryDAO

//Import to call coroutine
import kotlinx.coroutines.flow.Flow


public class CategoryService(private val categoryDao: CategoryDAO)
{
    val baseIcons = listOf(
        CategoryIcon
    )


    fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()

    //Create suspended function to prevent duplicated names of categories
    suspend fun createCategory(category: Category)
    {
        //Insert the category by calling dao function
        categoryDao.insertCategory(category)
    }
}