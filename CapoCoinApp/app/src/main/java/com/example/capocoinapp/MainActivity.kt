package com.example.capocoinapp

// import for nav host
// import for composable
// import for navController
// import for shared layout referenced from the designUI folder
// import for authentication layout referenced from the designUI folder

//

// import for nav host
// import for composable
// import for navController
// import for shared layout referenced from the designUI folder
// import for authentication layout referenced from the designUI folder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.data.DB.AppDatabase
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.ViewModels.CategoryViewModelFactory
import com.example.capocoinapp.data.ViewModels.TransactionViewModel
import com.example.capocoinapp.data.ViewModels.TransactionViewModelFactory
import com.example.capocoinapp.data.ViewModels.UserViewModel
import com.example.capocoinapp.data.ViewModels.ViewModelFactory
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CapoCoinAuthenticationLayout
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import com.example.capocoinapp.Services.TransactionService
import kotlinx.coroutines.launch

import com.example.capocoinapp.data.entities.User
import com.example.capocoinapp.designUI.components.AppScaffold

//


class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        ViewModelFactory(db.userDao())
    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        CategoryViewModelFactory(CategoryService(db.categoryDao()))


    }
    private val transactionViewModel: TransactionViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        TransactionViewModelFactory(db.transactionDao())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            CapoCoinAppTheme {

                // Declaring navController
                val navController = rememberNavController()

                // Nav Host wraps all composable routes
                NavHost(
                    navController = navController, startDestination = "Login"
                ){
                    composable("Home") {
                        HomeScreen(navController)
                    }
                    composable("Transactions"){
                        TransactionsScreen(navController)
                    }
                    composable("AddTransaction"){
                        AddTransaction(
                            navController = navController,
                            categoryViewModel = categoryViewModel,
                            transactionViewModel = transactionViewModel
                        )
                    }
                    composable("Analytics"){
                        AnalyticsScreen(navController)
                    }
                    composable("More"){
                        MoreScreen(navController)
                    }
                    composable("Categories"){
                        CategoriesScreen(navController = navController,
                            categoryService = CategoryService(AppDatabase.getDatabase
                            (applicationContext).categoryDao()))
                    }
                    composable("BottomNavBar"){
                        BottomNavBar(navController)
                    }
                    composable("TopNavBar"){
                        TopNavBar(navController)
                    }
                    composable("UserBudget"){
                        UserBudgetScreen(navController=navController,
                            categoryViewModel = categoryViewModel,
                            categoryService = CategoryService(AppDatabase.getDatabase
                                (applicationContext).categoryDao()))
                    }
                    composable("TransactionDetails"){
                        TransactionsDetailsScreen(navController)
                    }
                    composable("UserProfile"){
                        UserProfileScreen(navController)
                    }

                    // composable route to Login Screen
                    // ToDo: move to  dedicated screen file
                    composable("Login"){
                        // Ensures the Authentication layout is applied to the Login Screen
                        CapoCoinAuthenticationLayout(screenTitle = "Login", navController = navController){ padding ->

                            LaunchedEffect(userViewModel.isLoggedIn) {
                                if (userViewModel.isLoggedIn) {
                                    navController.navigate("Home")
                                    userViewModel.clearLoginState()
                                    userViewModel.clearMessage()
                                }
                            }
                            Login(
                                modifier = Modifier.padding(padding),
                                onLoginClick = { email, password ->
                                    userViewModel.loginUser(email,password)
                                },
                                onRegisterClick = {
                                    navController.navigate("Register")
                                }
                            )
                        }
                    }

                    // composable route to Register Screen
                    composable("Register"){
                        // Ensures the Authentication layout is applied to the Login Screen
                        CapoCoinAuthenticationLayout(
                            screenTitle = "Register",
                            navController = navController
                        ){ padding ->
                            LaunchedEffect(userViewModel.message) {
                                if (userViewModel.message == "User registered successfully") {
                                    navController.navigate("Login")
                                    userViewModel.clearMessage()
                                }
                            }
                            Register(
                                modifier = Modifier.padding(padding),
                                message = userViewModel.message,
                                navController= navController,
                                onRegisterClick = { name, username, email, password, confirmPassword ->

                                    userViewModel.registerUser(
                                        name = name,
                                        username = username,
                                        email = email,
                                        password = password,
                                        confirmPassword = confirmPassword
                                    )


                                }
                            )

                        }
                    }

                    // composable route to Add Categories Screen
                    composable("AddCategories"){
                        // Ensures the Global UI layout is applied to the Add Categories Screen
                        AppScaffold(
                            topBar = { TopNavBar(navController) },
                            bottomBar = { BottomNavBar(navController) },
                            pageTitle = "Add Category"
                        ){ padding ->
                            Box(modifier = Modifier.padding(padding))
                            {
                                AddCategory(viewModel = categoryViewModel,
                                service = CategoryService(AppDatabase.getDatabase
                                    (applicationContext).categoryDao()),
                                navController = navController)
                            }
                        }
                    }

                    //composable route to Category Totals Screen
                    composable("CategoryTotals")
                    {
                        // Ensures the Global UI layout is applied to the Categories Totals Screen
                        AppScaffold(
                            topBar = { TopNavBar(navController) },
                            bottomBar = { BottomNavBar(navController) },
                            pageTitle = "Category Totals"
                        ){ padding ->
                            Box(modifier = Modifier.padding(padding))
                            {
                                CategoryTotalsScreen(service = TransactionService
                                    (AppDatabase.getDatabase
                                    (applicationContext).transactionDao()),
                                    navController = navController)
                            }
                        }
                    }


                }

            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CapoCoinAppTheme {
        Greeting("Android")
    }
}