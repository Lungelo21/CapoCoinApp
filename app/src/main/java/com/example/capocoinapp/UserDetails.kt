package com.example.capocoinapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.capocoinapp.data.ViewModels.UserDetailsViewModel
import com.example.capocoinapp.data.dao.UserDAO
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.designUI.components.InfoCard
import com.example.capocoinapp.designUI.components.PasswordCard
import com.example.capocoinapp.designUI.components.PhotoSection

@Composable
fun UserDetails(
    navController: NavController,
    userID: String,
    userDetailsDAO: UserDAO,
    userDetailsViewModel: UserDetailsViewModel = viewModel(factory = UserDetailsViewModel.UserDetailsViewModelFactory(userDetailsDAO, userID))
){

    val userDetails by userDetailsViewModel.userDetails.collectAsState()

    val error by userDetailsViewModel.error.collectAsState()

    CapoCoinAppTheme {

        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 2) },
            pageTitle = "User Details"
        ) { _ ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
            {
                PhotoSection(
                    name = userDetails?.name,
                    username = userDetails?.username
                )

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if(userDetails != null){

                        InfoCard(
                            icon = Icons.Outlined.Person,
                            label = "Full name",
                            value = userDetails?.name ?: ""
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        InfoCard(
                            icon = Icons.Outlined.AlternateEmail,
                            label = "Username",
                            value = userDetails?.username ?: ""
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        InfoCard(
                            icon = Icons.Outlined.Email,
                            label = "Email",
                            value = userDetails?.email ?: ""
                        )

                        //Spacer(modifier = Modifier.height(8.dp))

//                        PasswordCard(
//                            icon = Icons.Outlined.Password,
//                            label = "Password",
//                            password = ""
//                        )
                    }

                }
            }
        }
    }



}