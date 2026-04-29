package com.example.capocoinapp

//Calling import call for composable annotation
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

//Import the Experimental Material API which is used to allow for experimental code

//Import calling mutable state of to help hold user entered data

//Import to call remember saveable which will be used to save user entered data

//Import used to call the Category Service and its methods

//Import used to call the Category View Model for validation

//Import to get the value via the by

//Import to set the value via the by

//import called to use design components such as App Scaffolding

//Import called to use the bottom Nav bar instantiated in design ui components

//Import called to use the top Nav bar instantiated in design ui components

//Import called to use the CapoCoin App Theme instantiated in design ui components

//Import called for allowing UI elements to  be used

//Import for the exposed dropdown

//Import for the text field to be outlined

//Import called to allow scroll capability

//Import called to remember the scrolls current state

//Import used to allow colour graphics to be used

//Import to call the Circle Shape

//Importing material icons

//importing question mark icon for default fallback icon

//Importing the Primary button from buttons.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.designUI.components.PrimaryButton
import com.example.capocoinapp.ui.theme.TextWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategory(viewModel: CategoryViewModel, service: CategoryService, navController: NavHostController) {
    var transactionType by rememberSaveable { mutableStateOf("") }
    var categoryTitle by rememberSaveable { mutableStateOf("") }
    var iconColour by rememberSaveable { mutableStateOf("") }
    var selectedIcon by rememberSaveable { mutableStateOf("") }

    var minBudget by rememberSaveable { mutableStateOf(0.0) }
    var maxBudget by rememberSaveable { mutableStateOf(0.0) }

    var transactionTypeExpanded by rememberSaveable { mutableStateOf(false) }
    var iconColourExpanded by rememberSaveable { mutableStateOf(false) }
    var iconExpanded by rememberSaveable { mutableStateOf(false) }

    val currentColourHex = service.selectableColours[iconColour] ?: "#000000"
    val currentIcon = service.getIcon(selectedIcon)

    /*
    CapoCoinAppTheme{
        val navController = rememberNavController()
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Add Category"
        ) { paddingValues ->*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(paddingValues)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        /*
                                            * Author: Dhivya
                                            * Link:  https://medium.com/@dhivyakgf/rememberscrollstate-in-jetpack-compose-45405074fe85
                                            * DateAccessed: 27/04/2026
                                            * */
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        //Dropdown method for the Transaction type
        ExposedDropdownMenuBox(
            expanded = transactionTypeExpanded,
            onExpandedChange = { transactionTypeExpanded = !transactionTypeExpanded }
        )
        /*
                 * Author: Kotlin Programming Language
                 * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-exposed-dropdown-menu-box.html
                 * DateAccessed: 27/04/2026
                 * */
        {
            OutlinedTextField(
                value = transactionType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Transaction Type", color = TextWhite) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = transactionTypeExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = TextWhite,
                    unfocusedBorderColor = Color(0xFF9E9E9E)
                )

            )
            /*
                    * Author: Kotlin Programming Language
                    * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-outlined-text-field.html
                    * DateAccessed: 27/04/2026
                    * */
            ExposedDropdownMenu(
                expanded = transactionTypeExpanded,
                onDismissRequest = { transactionTypeExpanded = false }
            )
            /*
                    * Author: Kotlin Programming Language
                    * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-exposed-dropdown-menu-box.html
                    * DateAccessed: 27/04/2026
                    * */
            {
                service.transactionTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            transactionType = type
                            transactionTypeExpanded = false
                        }
                    )
                }
            }
        }

        //Field entry for the Category Title
        OutlinedTextField(
            value = categoryTitle,
            onValueChange = { categoryTitle = it },
            label = { Text("Category Title", color = TextWhite) },
            placeholder = { Text("Rent, Education, Salary, etc.", color = Color(0xFF9E9E9E)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TextWhite,
                unfocusedBorderColor = Color(0xFF9E9E9E)
            )
        )
        /*
                    * Author: Kotlin Programming Language
                    * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-outlined-text-field.html
                    * DateAccessed: 27/04/2026
                    * */

        //Dropdown for the selectable colours
        ExposedDropdownMenuBox(
            expanded = iconColourExpanded,
            onExpandedChange = { iconColourExpanded = !iconColourExpanded }
        )
        /*
                 * Author: Kotlin Programming Language
                 * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-exposed-dropdown-menu-box.html
                 * DateAccessed: 27/04/2026
                 * */
        {
            OutlinedTextField(
                value = iconColour,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select a Colour", color = TextWhite) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = TextWhite,
                    unfocusedBorderColor = Color(0xFF9E9E9E)
                ),
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                Color(android.graphics.Color.parseColor(currentColourHex)),
                                CircleShape
                            )
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = iconColourExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            /*
                    * Author: Kotlin Programming Language
                    * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-outlined-text-field.html
                    * DateAccessed: 27/04/2026
                    * */
            ExposedDropdownMenu(
                expanded = iconColourExpanded,
                onDismissRequest = { iconColourExpanded = false })
            {
                service.selectableColours.forEach { (colourName, colourHex) ->
                    DropdownMenuItem(
                        text = { Text(colourName) },
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        Color(android.graphics.Color.parseColor(colourHex)),
                                        CircleShape
                                    )
                            )
                        },
                        onClick = { iconColour = colourName; iconColourExpanded = false }
                    )
                }
            }

        }

        //Dropdown for the selectable icons
        ExposedDropdownMenuBox(
            expanded = iconExpanded,
            onExpandedChange = { iconExpanded = !iconExpanded }
        )
        /*
                 * Author: Kotlin Programming Language
                 * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-exposed-dropdown-menu-box.html
                 * DateAccessed: 27/04/2026
                 * */
        {
            OutlinedTextField(
                value = selectedIcon,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select an Icon", color = TextWhite) },
                leadingIcon = {
                    Icon(      //Setting a null value in the case no icon is selected or found -> ?
                        imageVector = currentIcon ?: Icons.Default.QuestionMark,
                        contentDescription = null,
                        tint = TextWhite
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = iconExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = TextWhite,
                    unfocusedBorderColor = Color(0xFF9E9E9E)
                )
            )
            /*
                    * Author: Kotlin Programming Language
                    * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-outlined-text-field.html
                    * DateAccessed: 27/04/2026
                    * */
            ExposedDropdownMenu(
                expanded = iconExpanded,
                onDismissRequest = { iconExpanded = false },
                modifier = Modifier.heightIn(max = 280.dp) //Allows for scrolling if many icons are selectable
            ) {
                service.baseIcons.forEach { (name, icon) ->
                    /*
                            * Author: Sumit Ohja
                            * Link: https://medium.com/@sumit-dev-07/foreach-loop-f7bcfb3032ab
                            * DateAccessed: 27/04/2026
                            * */
                    DropdownMenuItem(
                        text = { Text(name) },
                        leadingIcon = { Icon(icon, contentDescription = null) },
                        onClick = { selectedIcon = name; iconExpanded = false }
                    )
                }
            }
        }

        //Spacing out the entry fields from the Add Category Button
        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
        {
            //Save button calling the Primary button method from Buttons.kt
            PrimaryButton(
                buttonText = if (transactionType.isNotBlank() &&
                    categoryTitle.isNotBlank() &&
                    iconColour.isNotBlank() &&
                    selectedIcon.isNotBlank()
                ) {
                    "Save Category"
                } else {
                    ""
                },
                enabled = transactionType.isNotBlank() &&
                        categoryTitle.isNotBlank() &&
                        iconColour.isNotBlank() &&
                        selectedIcon.isNotBlank(),
                onClick = {
                    Log.d("UI_Check", "PrimaryButton Clicked!") // See if the UI even registers the touch
                    viewModel.addCategory(
                        type = transactionType,
                        categoryTitle = categoryTitle,
                        categoryColour = iconColour,
                        categoryIcon = selectedIcon,
                        minBudget = minBudget,
                        maxBudget = maxBudget
                    )

                    navController.navigate("Categories")
                }
            )
        }
    }
    //}
    //}
//}
}