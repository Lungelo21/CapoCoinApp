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

                    message = "Transaction saved!"
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