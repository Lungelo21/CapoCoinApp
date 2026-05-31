package com.example.capocoinapp.Services

import android.app.Application
import com.example.capocoinapp.data.dao.AchievementsDAO
import com.example.capocoinapp.data.dao.CategoryDAO
import com.example.capocoinapp.data.dao.TransactionsDAO
import com.example.capocoinapp.data.entities.Transactions
import java.text.SimpleDateFormat
import java.util.Locale

class AchievementService(
    private val achievementsDao: AchievementsDAO,
    private val transactionsDAO: TransactionsDAO,
    private val categoryDAO: CategoryDAO,
    private val application: Application
)
{
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun trackTransactionsAchievements(transactions: Transactions)
    {

    }

    fun trackCategoryAchievements()
    {

    }

    fun SaveAchievements()
    {

    }
}