package com.example.capocoinapp.data.ViewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capocoinapp.data.dao.TransactionsDAO
import com.example.capocoinapp.data.entities.Transactions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionViewModel(
    private val dao: TransactionsDAO
) : ViewModel() {

    //Validation

    // UI Feedback message (e.g., "Transaction Saved!")
    var message by mutableStateOf("")
        private set

    fun clearMessage() {
        message = ""
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

            message = when {
                type.isBlank() -> "Please select a Transaction type"
                name.isBlank() -> "Please enter a title"
                amountDouble == null || amountDouble <= 0 -> "Please enter a valid amount"
                categoryID == 0 -> "Please select a category"
                else -> {

                    //Storing the current date and time
                    val calendar = Calendar.getInstance()

                    val dateLogged = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                    val timeLogged = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(calendar.time)

                    val transaction = Transactions(
                        transactionType = type,
                        transactionName = name,
                        transactionAmount = amountDouble,
                        categoryID = categoryID,
                        transactionDate = date,
                        transactionTime = time,
                        dateLogged = dateLogged,
                        timeLogged = timeLogged,
                        uploadedPhotoPath = photoPath
                    )

                    dao.insertTransactions(transaction)
                    "Transaction saved!"
                }
            }
        }
    }

    // Get all transactions for your view transactions screen
    fun getAllTransactions(): Flow<List<Transactions>> {
        return dao.getAllTransactions()
    }
}

// Factory to inject the DAO
class TransactionViewModelFactory(private val dao: TransactionsDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(dao) as T
        }
        throw IllegalArgumentException("Error Occurred")
    }
}