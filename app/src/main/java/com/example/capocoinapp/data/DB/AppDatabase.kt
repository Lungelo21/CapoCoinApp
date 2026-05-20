package com.example.capocoinapp.data.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.capocoinapp.data.dao.CategoryDAO
import com.example.capocoinapp.data.dao.TransactionsDAO
import com.example.capocoinapp.data.dao.UserDAO
import com.example.capocoinapp.data.entities.Category
import com.example.capocoinapp.data.entities.User
import com.example.capocoinapp.data.entities.Transactions


@Database(
    entities = [User::class, Transactions::class, Category::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun transactionDao(): TransactionsDAO
    abstract fun categoryDao(): CategoryDAO

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


}