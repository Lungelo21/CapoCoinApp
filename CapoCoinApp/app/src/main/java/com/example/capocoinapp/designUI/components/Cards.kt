package com.example.capocoinapp.designUI.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import com.example.capocoinapp.data.entities.Category
import com.example.capocoinapp.ui.theme.BackgroundColor
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.ProgressBarBlue
import com.example.capocoinapp.ui.theme.ProgressBarOrange
import com.example.capocoinapp.ui.theme.TextGreen
import com.example.capocoinapp.ui.theme.TextRed
import com.example.capocoinapp.ui.theme.TextWhite
import java.text.NumberFormat
import java.util.Calendar


@Composable
fun CardComponent(
    cardTitle: String,
    cardSubTitle: String?,
    cardAmount: String?,
    cardSubAmount: String?,
    cardColor: String,
    cardIcon: String?,
    cardTransactionType: String?,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (cardIcon != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = getColorFromString(cardColor),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getIconFromString(cardIcon),
                        contentDescription = null,
                        tint = TextWhite,
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cardTitle,
                        style = CapoType.cardTitle
                    )

                    if (cardAmount != null) {
                        Text(
                            text = formatAmount(cardAmount, cardTransactionType),
                            style = CapoType.cardTitle,
                            color = colorAmount(cardTransactionType)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (cardSubTitle != null) {
                        Text(
                            text = cardSubTitle,
                            style = CapoType.cardSubTitle
                        )
                    }

                    if (cardSubAmount != null) {
                        Text(
                            text = cardSubAmount,
                            style = CapoType.cardSubTitle
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardBox(cards: List<@Composable () -> Unit>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .wrapContentHeight()
            .background(BackgroundColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        cards.forEach { card ->
            card()
        }
    }
}

// If the entry is a transaction, the amount will be formatted,
// If not, the raw string will be displayed
fun formatAmount(amount: String, type: String?): String {

    val prefix: String = when (type) {
        "income" -> "+ R"
        "expense" -> "- R"
        else -> ""
    }

    return "$prefix${amount}"
}

// Sets amount text to green or red depending on income or expense
fun colorAmount(type: String?): Color {
    val amountColor: Color = when (type) {
        "income" -> TextGreen
        "expense" -> TextRed
        else -> TextWhite
    }

    return amountColor
}

@Composable
fun MoreCard(
    cardTitle: String,
    cardSubTitle: String?,
    cardIcon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = cardIcon,
                contentDescription = null,
                tint = TextWhite,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = cardTitle,
                    style = CapoType.cardTitle
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (cardSubTitle != null) {
                    Text(
                        text = cardSubTitle,
                        style = CapoType.cardSubTitle
                    )
                }
            }
        }
    }
}

@Composable
fun inputCard(
    value: String,
    placeholder: String,
    icon: ImageVector,
    enabled: Boolean,
    onValueChange: (String) -> Unit
) {
    // Card for the transaction title input
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Adding row for the Icon
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = TextWhite
            )

            // Adding horizontal spacer between icon and Title input
            Spacer(modifier = Modifier.width(12.dp))

            // Text field for user to enter transaction title
            TextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                placeholder = { Text(placeholder, style = CapoType.cardTitle) },
                textStyle = CapoType.cardTitle,
                singleLine = true,

                /*
                * Author: Siddharth Jaswal
                * Link: https://stackoverflow.com/a/78464953
                *
                * Stack Overflow original thread: https://stackoverflow.com/questions/64542659/jetpack-compose-custom-textfield-design/78464953#78464953
                * */

                // ensures that the container colour is set to CardBG like the rest of the card
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = CardBG,
                    focusedContainerColor = CardBG,
                    disabledContainerColor = CardBG,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCategoryDropDown(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit,
    placeholderText: String,
    enabled: Boolean
) {
    // stores whether the dropdown is expanded or not
    var dropdownExpand by remember { mutableStateOf(false) }

    // sorts categories alphabetically
    val sortedCategories = categories.sortedBy { it.categoryTitle }

    // limits how many categories pop up (10)
    val categoryLimit = sortedCategories.take(10)
    // shows the rest of the categories beyond the first 10
    val viewMore = sortedCategories.size > 10


    // Card for the select category dropdown
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Row for the drop down
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // takes the selectedCategory that the user clicks on and finds the icon for that specific category
            selectedCategory?.let {
                Icon(
                    // image found through running the getIconFromString method with category icon passes in parameter
                    imageVector = getIconFromString(it.categoryIcon),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp),
                    tint = TextWhite
                )

                Spacer(modifier = Modifier.width(12.dp))
            }
            // Exposed DropDown menu box for category
            ExposedDropdownMenuBox(
                expanded = dropdownExpand,
                // when user expands drop down
                onExpandedChange = {
                    if (enabled) dropdownExpand = !dropdownExpand
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Text Field containing the selected category title
                TextField(
                    value = selectedCategory?.categoryTitle ?: "",
                    onValueChange = {},
                    readOnly = true,
                    enabled = enabled,
                    placeholder = { Text(placeholderText, style = CapoType.cardTitle, color = TextWhite) },

                    textStyle = CapoType.cardTitle.copy(
                        color =  TextWhite
                    ),
                    // this is to add the arrow to let the user know that the box is a dropdown
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpand)
                                   },

                    /*
                    * Author: Siddharth Jaswal
                    * Link: https://stackoverflow.com/a/78464953
                    *
                    * Stack Overflow original thread: https://stackoverflow.com/questions/64542659/jetpack-compose-custom-textfield-design/78464953#78464953
                    * */

                    // ensures that the container colour is set to CardBG like the rest of the card
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = CardBG,
                        focusedContainerColor = CardBG,
                        disabledContainerColor = CardBG,

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        focusedTrailingIconColor = TextWhite,
                        unfocusedTrailingIconColor = TextWhite
                    ),

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                // Exposed drop down menu for category
                ExposedDropdownMenu(
                    expanded = dropdownExpand,
                    // onDismissRequest is set to the dropdown being not expanded
                    onDismissRequest = { dropdownExpand = false }
                ) {
                    // for each category (which is limited to 10 indexes)
                    categoryLimit.forEach { category ->

                        // Dropdown menu item containing name for icon and category name
                        DropdownMenuItem(
                            text = {
                                // Row for icon and Text
                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    // icon for category
                                    Icon(
                                        imageVector = getIconFromString(category.categoryIcon),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Black
                                    )

                                    // horizontal spacer between icon and text
                                    Spacer(modifier = Modifier.width(8.dp))

                                    // Text for category title
                                    Text(
                                        text = category.categoryTitle,
                                        style = CapoType.cardTitle,
                                        color = Color.Black
                                    )
                                }
                            },
                            // oon users click on a category, sets categorySelected to that category and dropdown closes
                            onClick = {
                                onCategorySelected(category)
                                dropdownExpand = false
                            }
                        )
                    }

                    // if there are more than 10 indexes, it shows a dropdownmenu item at the bottom of the list for View all categories
                    if (viewMore) {
                        DropdownMenuItem(
                            text = {
                                Text("View More", style = CapoType.cardTitle)
                            },
                            onClick = {
                                dropdownExpand = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTransactionTypeDropDown(
    transactionTypes: List<String>,
    selectedTransactionType: String,
    onTransactionTypeSelected: (String) -> Unit,
    placeholderText: String,
    enabled: Boolean
) {
    // stores whether the dropdown is expanded or not
    var dropdownExpand by remember { mutableStateOf(false) }

    // Card for the TransactionTypeDropDown
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Row including the transaction type
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // DropDown menu box for transaction types
            ExposedDropdownMenuBox(
                expanded = dropdownExpand,
                onExpandedChange = {
                    if (enabled) dropdownExpand = !dropdownExpand
                },

                modifier = Modifier.fillMaxWidth()
            ) {
                // textfield for the chosen transaction type
                TextField(
                    value = selectedTransactionType,
                    onValueChange = {},
                    readOnly = true,
                    enabled = enabled,
                    placeholder = { Text(placeholderText, style = CapoType.cardTitle) },
                    textStyle = CapoType.cardTitle,

                    /*
                    * Author: Santosh Yadav
                    * Link: https://medium.com/@santosh_yadav321/dropdown-menu-with-icon-in-jetpack-compose-5ebebae75851
                    * */
                    // this is to add the arrow to let the user know that the box is a dropdown
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpand)
                    },

                    /*
                    * Author: Siddharth Jaswal
                    * Link: https://stackoverflow.com/a/78464953
                    *
                    * Stack Overflow original thread: https://stackoverflow.com/questions/64542659/jetpack-compose-custom-textfield-design/78464953#78464953
                    * */

                    // ensures that the container colour is set to CardBG like the rest of the card
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = CardBG,
                        focusedContainerColor = CardBG,
                        disabledContainerColor = CardBG,

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,

                        focusedTrailingIconColor = TextWhite,
                        unfocusedTrailingIconColor = TextWhite
                    ),

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                // Dropdown menu when expanded
                ExposedDropdownMenu(
                    expanded = dropdownExpand,
                    onDismissRequest = { dropdownExpand = false }
                ) {
                    // for each transactionTypes
                    transactionTypes.forEach { transactionTypes ->

                        // Menu item for each transaction type
                        DropdownMenuItem(
                            text = {
                                Text( // Text for each type
                                    text = transactionTypes,
                                    style = CapoType.cardTitle,
                                    color = Color.Black
                                )
                            },
                            // when one is clicked the onTransactionTypeSelected becomes the transaction type clicked
                            onClick = {
                                onTransactionTypeSelected(transactionTypes)
                                dropdownExpand = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerCard(
    selectedTransactionDate: String,
    onTransactionDateSelected: (String) -> Unit,
    placeholderText: String,
    enabled: Boolean,
    icon: ImageVector = Icons.Default.DateRange
) {
    // current and local context
    val context = LocalContext.current

    // card for the date picker card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {

                if (enabled) {
                    // calendar current instance
                    val calendar = Calendar.getInstance()

                    /*
                    * Author: GeeksForGeeks
                    * Link: https://www.geeksforgeeks.org/kotlin/date-picker-in-android-using-jetpack-compose/
                    * */

                    // Date Picker dialog
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->

                            /*
                            * Author: Deccon Tech
                            * Link: https://www.youtube.com/watch?v=BH8fqeIKQRo
                            * */

                            // format by year month day of month
                            val formatted =
                                String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                            onTransactionDateSelected(formatted)
                        },
                        // gets year, month and day in month from calendar
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Row for the icon and Date in format
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size((44.dp)),
                tint = TextWhite
            )

            // horizontal spacer between icon and date text
            Spacer(modifier = Modifier.width(12.dp))

            // date text
            Text(
                text = if (selectedTransactionDate.isEmpty()) placeholderText else selectedTransactionDate,
                style = CapoType.cardTitle,
                color = TextWhite
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerCard(
    selectedTransactionTime: String,
    onTransactionTimeSelected: (String) -> Unit,
    placeholderText: String,
    enabled: Boolean,
    icon: ImageVector = Icons.Default.AccessTime
) {
    // current local context
    val context = LocalContext.current

    // card for the time picker card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {

                if (enabled) {
                    // gets instance of calendar
                    val calendar = Calendar.getInstance()
                    /*
                    * Author: GeeksForGeeks
                    * Link: https://www.geeksforgeeks.org/kotlin/time-picker-in-android-using-jetpack-compose/
                    * */
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            // formats the time
                            val formatted = String.format("%02d:%02d", hourOfDay, minute)
                            // formats the amount selected
                            onTransactionTimeSelected(formatted)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Row for the icon and time of transaction
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size((44.dp)),
                tint = TextWhite
            )
            //spacer between icon and time
            Spacer(modifier = Modifier.width(12.dp))

            // Time
            Text(   // If the entered time hasnt been shown yet, show the placeholderText otherwise show the selectedTime for the Transaction
                text = if (selectedTransactionTime.isEmpty()) placeholderText else selectedTransactionTime,
                style = CapoType.cardTitle,
                color =  TextWhite // sets chosen time to TextWhite
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachImageCard(
    imageUri: Uri?,
    onImageSelected: (Uri?) -> Unit,
    placeholderText: String,
    enabled: Boolean,
    icon: ImageVector = Icons.Default.AttachFile
) {
    // photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> //takes image uri
        onImageSelected(uri)
    }

    // card for attach image photo
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {

                //launches photo picker launcher
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(// allows image only
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },

        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
    ) {
        // row for icon and text for if image attached
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
             //checks if img is null
            if (imageUri == null) {
                // icon
                Icon(
                    imageVector = icon,
                    tint = TextWhite,
                    contentDescription = null,
                    modifier = Modifier.size((44.dp))
                )
            } else {
                // img

                /*
                * Author:Mun Bonecci
                * Link: https://medium.com/@munbonecci/how-to-use-coil-with-rememberasyncimagepainter-in-jetpack-compose-to-load-svg-f4d39cb829fb
                * */

                Image(// shows preview for attached photo
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            //spacing between the img and the text
            Spacer(modifier = Modifier.width(12.dp))

            // text for if img is attached or not
            Text(
                text = if (imageUri == null) placeholderText else "Image Attached",
                style = CapoType.cardTitle,
            )
        }
    }
}

@Composable
fun FinalAmountCard(
    transactionAmount: String,
    onAmountClicked: () -> Unit,
    cardIcon: ImageVector

) {
    // Final amount card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onAmountClicked() },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Row for the amount
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // amount text
            Text(
                text = "Amount: ",
                style = CapoType.cardTitle
            )
            //spacers
            Spacer(modifier = Modifier.width(8.dp))

            Spacer(modifier = Modifier.weight(1f))

            //transaction amount
            Text(
                text = "R $transactionAmount",
                style = CapoType.cardTitle
            )

            Spacer(modifier = Modifier.width(12.dp))

            //icon for calc
            Icon(
                imageVector = cardIcon,
                contentDescription = "Adjust value",
                tint = TextWhite
            )
        }
    }
}

@Composable
fun LogTransactionButton(
    symbol: String,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .then(modifier)
    ) {
        // Text for Confirm Amount
        Text(
            text = symbol,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}


@Composable
fun UserProfileCard(
    firstName: String,
    lastName: String,
    level: Int,
    profileTitle: String,
    currentXP: Int,
    nextLevelXP: Int,
    onClick: () -> Unit = {}
) {
    // variables to capture xpRemaining, progress bar total and progress bar percent
    val xpRemaining = nextLevelXP - currentXP
    val progressFloat = currentXP.toFloat() / nextLevelXP.toFloat()
    val progressPercent = (progressFloat * 100).toInt()

    // Card for UserProfile Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        //Defining the top of the card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
        ) {

            // Row for the Icon, Users full name, level number and profile title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // profile icon

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    modifier = Modifier.size(56.dp),
                    tint = TextWhite
                )

                // Spacer between Icon and Details
                Spacer(modifier = Modifier.width(12.dp))

                // Column for User Details
                Column {

                    // Text with users first and last name
                    Text(
                        text = "$firstName $lastName",
                        style = CapoType.cardTitle
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Text with the users level and profile title
                    Text(
                        text = "Level $level: $profileTitle",
                        style = CapoType.cardTitle,
                    )
                }
            }
        }
        // Column for the xp needed to reach the next level
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Text which shows the amount of xp users needs to lvl up
            Text(
                text = "$xpRemaining more xp to Level ${level + 1}",
                style = CapoType.cardTitle,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                // Fills the progress bar from left to right
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressFloat)
                        .align(Alignment.CenterStart)
                        .background(ProgressBarBlue)
                )

                // Percentage of xp to next level
                Text(
                    text = "$progressPercent%",
                    modifier = Modifier.fillMaxWidth(),
                    style = CapoType.cardTitle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}


@Composable
fun MilestoneAchievementCard(
    milestoneTitle: String,
    milestoneSubTitle: String,
    milestonesTimesEarned: String?
) {
    // Card for Milestone achievements
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {

                // Milestone Title and Times Earned Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Milestone Title Text
                    Text(
                        text = milestoneTitle,
                        style = CapoType.cardTitle
                    )
                    // Milestone Times Earned Amount Text
                    Text(
                        text = "Times Earned: ${milestonesTimesEarned ?: "0"}",
                        style = CapoType.cardSubTitle
                    )
                }

                // Spacing out the two rows
                Spacer(modifier = Modifier.height(6.dp))

                // Milestone SubTitle Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Mileston Subtitle Text
                    Text(
                        text = milestoneSubTitle,
                        style = CapoType.cardSubTitle
                    )

                }
            }
        }
    }
}


@Composable
fun BudgetCard(
    cardTitle: String,
    cardMin: Double?,
    cardMax: Double?,
    cardIcon: String,
    cardColor: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            //Icon format is a placeholder, will be replaced with logic
            // to automatically change according to category
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(
                        color = getColorFromString(cardColor),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getIconFromString(cardIcon),
                    contentDescription = null,
                    tint = TextWhite,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardTitle,
                    style = CapoType.cardTitle

                )
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.width(70.dp),
                    text = cardMin.toString(),
                    style = CapoType.cardTitle,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.width(70.dp),
                    text = cardMax.toString(),
                    style = CapoType.cardTitle,
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    cardTitle: String,
    cardColor: String,
    cardIcon: String?,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (cardIcon != null) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .background(
                            color = getColorFromString(cardColor),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getIconFromString(cardIcon),
                        contentDescription = null,
                        tint = TextWhite,
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardTitle,
                    style = CapoType.cardTitle,
                    fontSize = 20.sp

                )
            }
        }
    }
}

// Returns the corresponding icon from the string stored in the database
fun getIconFromString(category: String) = when (category) {
    "Food" -> Icons.Default.Restaurant
    "Movie" -> Icons.Default.Movie
    "Groceries" -> Icons.Default.ShoppingCart
    "Shopping" -> Icons.Default.ShoppingBag
    "Transport" -> Icons.Default.DirectionsBus
    "Flight" -> Icons.Default.Flight
    "Education" -> Icons.Default.School
    "Gift" -> Icons.Default.CardGiftcard
    "Subscriptions" -> Icons.Default.Payments
    "Gym" -> Icons.Default.FitnessCenter
    "Healthcare" -> Icons.Default.MedicalServices
    "House payments" -> Icons.Default.Construction
    "Salary" -> Icons.Default.AttachMoney
    "Sports" -> Icons.Default.SportsSoccer
    "Hobbies" -> Icons.Default.AutoAwesome
    "Culture" -> Icons.Default.Museum
    "Donations" -> Icons.Default.Handshake
    "Self Care" -> Icons.Default.Spa
    "Gaming" -> Icons.Default.SportsEsports
    "Pets" -> Icons.Default.Pets
    "Music" -> Icons.Default.MusicNote
    "Tech" -> Icons.Default.Devices
    "Savings" -> Icons.Default.Savings
    "Car Payments" -> Icons.Default.DirectionsCar
    "Celebration" -> Icons.Default.Celebration
    else -> Icons.Default.QuestionMark
}

fun getColorFromString(hex: String) = Color(hex.toColorInt())

@Composable
fun HomeCard(
    totalSpent: Double,
    budget: Double,
    daysRemaining: Int,
    onClick: () -> Unit = {}
) {
    // Formats the number to look nicer: 20000.0 -> R20 000,00
    val budgetString = NumberFormat.getCurrencyInstance().format(budget)
    val totalRemaining = NumberFormat.getCurrencyInstance().format(budget - totalSpent)
    val dailyRemaining =
        NumberFormat.getCurrencyInstance().format((budget - totalSpent) / daysRemaining.toDouble())

    val progressFloat = (totalSpent / budget).toFloat()
    val progressPercent = (progressFloat * 100).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Monthly Expenses",
                    style = CapoType.cardTitle,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "You can spend $dailyRemaining per day for $daysRemaining days",
                    style = CapoType.cardTitle
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "$totalRemaining remaining of $budgetString",
                style = CapoType.cardTitle,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Progress bar box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = BackgroundColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                )

                LinearProgressIndicator(
                    progress = { progressFloat },
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    color = ProgressBarOrange,
                    trackColor = Color.Transparent,
                    drawStopIndicator = {}
                )

                Text(
                    text = "$progressPercent%",
                    modifier = Modifier
                        .fillMaxSize(),
                    style = CapoType.cardTitle,
                    textAlign = TextAlign.Center
                )
                /*
                 * Author: Android
                 * Link: https://developer.android.com/develop/ui/compose/components/progress
                 * DateAccessed: 28/04/2026
                 * */
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CapoCoinAppTheme {
        CardBox(
            cards = listOf(
                {
                    MoreCard(
                        "Settings",
                        "Change app settings",
                        Icons.Default.Settings
                    )
                }
//                {
//                    HomeCard(1300.0, 2000.0, 15)
//                }
//                {
//                    CardComponent(
//                        "Dinner Night",
//                        "Empire Steak",
//                        "200",
//                        "5:00 PM",
//                        "Teal",
//                        "Food",
//                        "expense"
//                    )
//                },
//                {
//                    CardComponent(
//                        "Salary",
//                        "Dunder Mifflin",
//                        "30 000",
//                        "9:45 AM",
//                        Icons.Default.Payments,
//                        "income"
//                    )
//                },
//                {
//                    BudgetCard(
//                        "Gym",
//                        200.0,
//                        400.0,
//                        "Gym",
//                        "Teal"
//                    )
//                },
//                {
//                    CategoryCard(
//                        "Food",
//                        "Grey",
//                        "Food"
//                    )
//                }
            )
        )
    }
}