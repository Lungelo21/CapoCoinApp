package com.example.capocoinapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.capocoinapp.data.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM categories ORDER BY categoryID DESC")
    fun getAllCategories(): Flow<List<Category>>

    //@Query("SELECT * FROM categories WHERE categoryName = :categoryNameInput LIMIT 1")
    //fun getCategories(categoryNameInput: String): Category

}