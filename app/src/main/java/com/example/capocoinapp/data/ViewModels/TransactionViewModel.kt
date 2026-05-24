package com.example.capocoinapp.data.ViewModels
import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.capocoinapp.Services.TransactionService
import com.example.capocoinapp.Supabase.SupabaseClient
import com.example.capocoinapp.Utils.isInternetAvailable
import com.example.capocoinapp.data.dao.TransactionsDAO
import com.example.capocoinapp.data.dto.TransactionsDTO
import com.example.capocoinapp.data.dto.toEntity
import com.example.capocoinapp.data.entities.Transactions
import com.example.capocoinapp.data.entities.toDTO
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TransactionViewModel(
    private val dao: TransactionsDAO,
    private val application: Application // Injected via factory extras bundle cleanly
) : ViewModel() {

    //Validation

    // UI Feedback message (e.g., "Transaction Saved!")
    var message by mutableStateOf("")
        private set

    fun clearMessage() {
        message = ""
    }

    //Using init to make sure this will be actioned as the code is first run
    /*
     * Author: Ranjeet
     * Link: https://medium.com/@ranjeet123/init-block-in-kotlin-518b050cada1
     * DateAccessed: 22/05/2026
     * */

    init {

        viewModelScope.launch {

            // Fetch transactions stored in room
            val currentTransactions = dao.getAllTransactions().first()

            if (currentTransactions.isEmpty()) {
                Log.d("TransactionVMCheck", "No local transactions found. Waiting for user input.")

            } else {
                Log.d("TransactionVMCheck", "Transactions exist locally. Ensuring remote Supabase DB is caught up...")

                // syncs roomdb to remote supabase
                viewModelScope.launch {
                    var synced = false
                    while (!synced) {
                        if (application.isInternetAvailable()) {
                            try {
                                Log.d("SyncCheck", "Startup Transaction Sync: Pushing local ledger to Supabase...")

                                // .upsert() inserts records written offline and leaves existing ones untouched
                                //SupabaseClient.client.postgrest["transactions"].upsert(currentTransactions)

                                val dtoPayload = currentTransactions.map { it.toDTO() }
                                SupabaseClient.client.postgrest["transactions"].upsert(dtoPayload)

                                Log.d("SyncCheck", "Startup Transaction Sync: Remote ledger successfully updated!")
                                synced = true // Safely exit loop
                            } catch (e: Exception) {
                                Log.e("SyncCheck", "Startup Transaction Sync failed, retrying in 10s: ${e.message}")
                                delay(10000) // Wait 10 seconds before trying again
                            }
                        }
                        else {
                            Log.d("SyncCheck", "Startup Transaction Sync: Offline. Waiting for internet connection...")
                            delay(5000) // Test connection again in 5 seconds
                        }
                    }
                }
            }

            // Pulls any supabase records to roomdb
            try {
                if (application.isInternetAvailable()) {
                    val supabaseTransactionsDTOs = SupabaseClient.client.postgrest["transactions"].select()
                        .decodeList<TransactionsDTO>()

                    if (supabaseTransactionsDTOs.isNotEmpty()) {
                        Log.d("TransactionVMCheck", "Found ${supabaseTransactionsDTOs.size} transactions on remote. Syncing to Room...")

                        supabaseTransactionsDTOs.forEach { dto ->
                            dao.insertTransactions(dto.toEntity())
                        }
                        Log.d("TransactionVMCheck", "Successfully pulled remote transaction records!")
                    }
                }
            } catch (e: Exception) {
                // Fails silently if device is offline on first remote pull
                Log.e("TransactionVMCheck", "Initial remote transaction pull failed: ${e.message}")
            }
        }
    }



    fun addTransaction(
        type: String,
        name: String,
        amount: String, // String from TextField
        categoryID: Int,
        date: String,
        time: String,
        photoPath: String?
    ) {
        viewModelScope.launch {
            val amountDouble = amount.toDoubleOrNull()

            // list of error messages
            val errors = mutableListOf<String>()

            // checks if the transaction type is empty
            if(type.isBlank()) {
                errors.add("Please select a Transaction type")
            }
            // checks if the transaction name is empty
            if(name.isBlank()){
                errors.add("Please enter a title")
            }
            // checks if the transaction amount is null or less than 0
            if(amountDouble == null || amountDouble <= 0) {
                errors.add("Please enter a valid amount")
            }
            // checks if the category id is empty
            if(categoryID == 0) {
                errors.add("Please select a category")
            }
            // checks if the date of transaction is empty
            if(date.isBlank()) {
                errors.add("Please enter a date")
            }
            // checks if the time of transaction is empty
            if(time.isBlank()) {
                errors.add("Please enter the time the transaction was made at")
            }

            // checks if errors list is not empty, if fill, message composes the errors and returns them
            if(errors.isNotEmpty()) {
                message = errors.joinToString("\n")
                return@launch
            }

            //Storing the current date and time
            val calendar = Calendar.getInstance()

            val dateLogged = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            val timeLogged = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(calendar.time)

            val transaction = Transactions(
                transactionType = type,
                transactionName = name,
                transactionAmount = amountDouble!!,
                categoryID = categoryID,
                transactionDate = date,
                transactionTime = time,
                dateLogged = dateLogged,
                timeLogged = timeLogged,
                uploadedPhotoPath = photoPath
            )

            dao.insertTransactions(transaction)

            viewModelScope.launch {

                var uploaded = false // set upload to false
                val app = application // application context

                while (!uploaded) { // continuously run while upload is true (continuously syncs roomdb to supabase)
                    if (app.isInternetAvailable()) {
                        try {
                            SupabaseClient.client.postgrest["transactions"].insert(transaction.toDTO())
                            Log.d("SyncCheck", "Successfully synced offline transaction to Supabase.")
                            uploaded = true // Breaks the loop
                        } catch (e: Exception) {
                            Log.e("SyncCheck", "Server error, retrying in 10 seconds: ${e.message}")
                            kotlinx.coroutines.delay(10000) // Wait 10 seconds before retrying server errors
                        }
                    } else { // if app has no internet connection
                        Log.d("SyncCheck", "Device's internet connection offline. Retrying connection check in 5 seconds...")
                        kotlinx.coroutines.delay(5000) // Check connection again in 5 seconds
                    }
                }
                // insert transaction into supabase client
                //SupabaseClient.client.postgrest["transactions"].insert(transaction.toDTO())
            }
        }


    }

    // Get all transactions for your view transactions screen
    fun getAllTransactions(): Flow<List<Transactions>> {
        return dao.getAllTransactions()
    }

    fun getFilterTransactions(startDate: String, endDate: String): Flow<List<Transactions>>{
        return dao.getFilterTransactions(startDate, endDate)
    }

    fun getTransactionById(id: Int?): Flow<Transactions?> {
        return if (id != null) {
            dao.getTransactionById(id)
        } else {
            flowOf(null)
        }
    }
}

// Factory to inject the DAO
class TransactionViewModelFactory(private val dao: TransactionsDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {

            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Error Occurred")
    }
}