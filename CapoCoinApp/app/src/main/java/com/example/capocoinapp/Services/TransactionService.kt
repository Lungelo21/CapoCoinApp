package com.example.capocoinapp.Services

//Import to call the Transactions entity
import com.example.capocoinapp.data.entities.Transactions

//Import to call coroutine
import kotlinx.coroutines.flow.Flow
import com.example.capocoinapp.data.dao.TransactionsDAO


public class TransactionService(private val transactionsDAO: TransactionsDAO) {

    // function to get all transactions from the DAO
    fun getAllTransactions() = transactionsDAO.getAllTransactions()

    // function to create the transaction
    suspend fun createTransaction(transactions: Transactions){
        transactionsDAO.insertTransactions(transactions)
    }





}