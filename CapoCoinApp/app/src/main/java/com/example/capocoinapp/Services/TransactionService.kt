package com.example.capocoinapp.Services

//Import to call the Transactions entity

//Import to call coroutine
import com.example.capocoinapp.data.dao.TransactionsDAO
import com.example.capocoinapp.data.dto.CategoryTotal
import com.example.capocoinapp.data.entities.Transactions
import kotlinx.coroutines.flow.Flow


public class TransactionService(private val transactionsDAO: TransactionsDAO) {

    // function to get all transactions from the DAO
    fun getAllTransactions() = transactionsDAO.getAllTransactions()

    // function to create the transaction
    suspend fun createTransaction(transactions: Transactions) {
        transactionsDAO.insertTransactions(transactions)
    }

    //Making a method call the DAO query
    fun getCategoryTotals(startDate: String, endDate: String): Flow<List<CategoryTotal>>
    {
        return transactionsDAO.getCategoryTotals(startDate, endDate)
    }

    fun getFilterTransactions(startDate: String, endDate: String): Flow<List<Transactions>>{
        return transactionsDAO.getFilterTransactions(startDate, endDate)
    }
}