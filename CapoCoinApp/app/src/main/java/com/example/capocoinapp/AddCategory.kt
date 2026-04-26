package com.example.capocoinapp

//Calling import call for composable annotation
import androidx.compose.runtime.Composable

//Import the Experimental Material API which is used to allow for experimental code
import androidx.compose.material3.ExperimentalMaterial3Api

//Import calling mutable state of to help hold user entered data
import androidx.compose.runtime.mutableStateOf

//Import to call remember saveable which will be used to save user entered data
import androidx.compose.runtime.saveable.rememberSaveable

//Import used to call the Category Service and its methods
import com.example.capocoinapp.Services.CategoryService

//Import used to call the Category View Model for validation
import com.example.capocoinapp.data.ViewModels.CategoryViewModel

//Import to get the value via the by
import androidx.compose.runtime.getValue

//Import to set the value via the by
import androidx.compose.runtime.setValue

//import called to use design components such as App Scaffolding
import com.example.capocoinapp.designUI.components.AppScaffold

//Import called to use the bottom Nav bar instantiated in design ui components
import com.example.capocoinapp.designUI.components.BottomNavBar

//Import called to use the top Nav bar instantiated in design ui components
import com.example.capocoinapp.designUI.components.TopNavBar

//Import called to use the CapoCoin App Theme instantiated in design ui components
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

//Import called to allow columns to be used
import androidx.compose.foundation.layout.Column

//Import used to allow modifier to be used
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding

import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.material3.ExposedDropdownMenuBox

import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.ExposedDropdownMenuDefaults

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon

import androidx.compose.material3.Button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategory(viewModel: CategoryViewModel, service: CategoryService)
{
    var transactionType by rememberSaveable { mutableStateOf("") }
    var categoryTitle by rememberSaveable { mutableStateOf("") }
    var iconColour by rememberSaveable { mutableStateOf("") }
    var selectedIcon by rememberSaveable { mutableStateOf(service.baseIcons[0]) }

    var dropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var iconExpanded by rememberSaveable { mutableStateOf(false) }

    val transactionTypes = listOf("Expense", "Income")

    CapoCoinAppTheme{
        AppScaffold(
            topBar = { TopNavBar() },
            bottomBar = { BottomNavBar() },
            pageTitle = "Add Category"
        ){ _ ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "New Category", style = MaterialTheme.typography.headlineMedium)

                // --- 1. TRANSACTION TYPE DROPDOWN ---
                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { dropdownExpanded = !dropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = transactionType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Transaction Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        transactionTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    transactionType = type
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // --- 2. CATEGORY TITLE ---
                OutlinedTextField(
                    value = categoryTitle,
                    onValueChange = { categoryTitle = it },
                    label = { Text("Category Title (e.g. Netflix, Rent)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // --- 3. ICON SELECTION DROPDOWN ---
                ExposedDropdownMenuBox(
                    expanded = iconExpanded,
                    onExpandedChange = { iconExpanded = !iconExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedIcon,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Choose Icon") },
                        leadingIcon = {
                            // This shows a live preview of the currently selected icon
                            Icon(
                                imageVector = service.getIconForName(selectedIcon),
                                contentDescription = null
                            )
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = iconExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = iconExpanded,
                        onDismissRequest = { iconExpanded = false }
                    ) {
                        service.baseIcons.forEach { name ->
                            DropdownMenuItem(
                                text = { Text(name) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = service.getIconForName(name),
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    selectedIcon = name
                                    iconExpanded = false
                                }
                            )
                        }
                    }
                }

                // --- 4. SAVE BUTTON ---
                Button(
                    onClick = {
                        viewModel.addCategory(
                            type = transactionType,
                            categoryTitle = categoryTitle,
                            categoryColour = iconColour,
                            categoryIcon = selectedIcon
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save Category")
                }
            }

        }
    }
}