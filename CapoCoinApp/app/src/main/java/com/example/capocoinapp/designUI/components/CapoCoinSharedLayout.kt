package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapoCoinSharedLayout(
    screenTitle: String,
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF292929)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick =
                            {
                                navController.navigate("UserProfile")
                            }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "User Profile",
                            tint = Color(0xFFD9D9D9),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick =
                            {
                                navController.navigate("Settings")
                            }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFFD9D9D9),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF292929),
                    navigationIconContentColor = Color(0xFFD9D9D9),
                    actionIconContentColor = Color(0xFFD9D9D9)
                )
            )
        },

        bottomBar = {
            NavigationBar(containerColor = Color(0xFF292929))
            {
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Home, "Home", tint = Color(0xFFE9B44C)) },
                    label = { Text("Home", color = Color(0xFFD4AF37)) },
                    selected = false,
                    onClick = { navController.navigate("Home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Sell, "Transactions", tint = Color(0xFFE9B44C)) },
                    label = {
                        Text(
                            "Transactions",
                            color = Color(0xFFD4AF37),
                            maxLines = 1,
                            fontSize = 11.sp
                        )
                    },

                    selected = false,
                    onClick = { navController.navigate("Transactions") }
                )
                NavigationBarItem(
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFFD4AF37), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Transactions",
                                modifier = Modifier.size(28.dp),
                                tint = Color(0xFF1A2421)
                            )
                        }
                    },
                    selected = false,
                    onClick = { navController.navigate("AddTransactions") },
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.PieChart, "Analytics", tint = Color(0xFFE9B44C)) },
                    label = { Text("Analytics", color = Color(0xFFD4AF37)) },
                    selected = false,
                    onClick = { navController.navigate("Analytics") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.MoreHoriz, "More", tint = Color(0xFFE9B44C)) },
                    label = { Text("More", color = Color(0xFFD4AF37)) },
                    selected = false,
                    onClick = { navController.navigate("More") }
                )
            }
        }
    )
    { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun HeaderNavBar() {
    val iconSize = 50.dp
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF292929),
                shape = RoundedCornerShape(
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = TextWhite,
                modifier = Modifier
                    .size(iconSize)
                    .clickable{
                        //ToDo: Add path to accounts screen
                    }
            )

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = TextWhite,
                modifier = Modifier
                    .size(iconSize)
                    .clickable{
                        //ToDo: Add path to settings screen
                    }
            )
        }
    }
}

@Composable
fun HeadingText(titleText: String) {
    Text(
        text = titleText,
        style = CapoType.heading,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CapoCoinAppTheme {
        HeaderNavBar()
    }
}