package com.example.capocoinapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.capocoinapp.data.dto.CategoryTotal
import com.example.capocoinapp.data.entities.Transactions
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: Transactions)

    @Query("SELECT * FROM transactions ORDER BY transactionID DESC")
    fun getAllTransactions(): Flow<List<Transactions>>

    //@Query("SELECT * FROM transactions WHERE transactionName = :transactionNameInput LIMIT 1")
    //fun getTransactions(transactionNameInput: String): Transactions

    @Query("SELECT c.categoryTitle AS categoryTotalID, c.CategoryTitle, c.categoryColour, c.categoryIcon, SUM(t.transactionAmount) as totalAmount " +
            "FROM transactions t " +
            "INNER JOIN categories c ON  t.categoryID = c.categoryID " +
            "WHERE (:startDate = '' OR :endDate = '' OR t.transactionDate BETWEEN :startDate AND :endDate) " +
            "GROUP BY c.categoryID")
    fun getCategoryTotals(startDate: String, endDate: String): Flow<List<CategoryTotal>>

    @Query("SELECT * FROM transactions " +
            "WHERE (:startDate = '' OR :endDate = '' OR transactionDate BETWEEN :startDate AND :endDate)" +
            "ORDER BY transactionID DESC")
    fun getFilterTransactions(startDate: String, endDate: String): Flow<List<Transactions>>

    @Query("SELECT * FROM transactions WHERE transactionID = :id LIMIT 1")
    fun getTransactionById(id: Int): Flow<Transactions?>

}