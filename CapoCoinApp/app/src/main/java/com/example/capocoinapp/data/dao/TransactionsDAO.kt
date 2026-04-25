package com.example.capocoinapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.capocoinapp.data.entities.Transactions
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransactions(transactions: Transactions)

    @Query("SELECT * FROM transactions ORDER BY transactionID DESC")
    fun getAllTransactions(): Flow<List<Transactions>>

    //@Query("SELECT * FROM transactions WHERE transactionName = :transactionNameInput LIMIT 1")
    //fun getTransactions(transactionNameInput: String): Transactions
}