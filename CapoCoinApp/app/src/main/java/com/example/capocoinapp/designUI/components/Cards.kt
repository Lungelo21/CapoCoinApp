package com.example.capocoinapp.designUI.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
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
import androidx.compose.material.icons.filled.Fastfood
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.capocoinapp.ui.theme.TextGreen
import com.example.capocoinapp.ui.theme.TextRed
import com.example.capocoinapp.ui.theme.TextWhite


@Composable
fun CardComponent(
    cardTitle: String,
    cardSubTitle: String?,
    cardAmount: String?,
    cardSubAmount: String?,
    cardIcon: ImageVector,
    cardTransactionType: String?,
    onClick: () -> Unit = {}
) {
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
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icon passed as parameter
            Icon(
                imageVector = cardIcon,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = TextWhite
            )

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
fun inputCard(
    value: String,
    placeholder: String,
    icon: ImageVector,
    enabled: Boolean,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = TextWhite
            )

            Spacer(modifier = Modifier.width(12.dp))

            TextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                placeholder = { Text(placeholder, style = CapoType.cardTitle) },
                textStyle = CapoType.cardTitle,
                singleLine = true,
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
    var dropdownExpand by remember { mutableStateOf(false) }

    // sorts categories alphabetically
    val sortedCategories = categories.sortedBy { it.categoryTitle }

    // limits how many categories pop up (10)
    val categoryLimit = sortedCategories.take(10)
    // shows the rest of the categories beyond the first 10
    val viewMore = sortedCategories.size > 10



    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            selectedCategory?.let {
                Icon(
                    imageVector = getIconFromString(it.categoryIcon),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp),
                    tint = TextWhite
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            ExposedDropdownMenuBox(
                expanded = dropdownExpand,
                onExpandedChange = {
                    if (enabled) dropdownExpand = !dropdownExpand
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = selectedCategory?.categoryTitle?: "",
                    onValueChange = {},
                    readOnly = true,
                    enabled = enabled,
                    placeholder = { Text(placeholderText, style = CapoType.cardTitle) },

                    textStyle = CapoType.cardTitle,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpand,
                    onDismissRequest = { dropdownExpand = false }
                ) {
                    categoryLimit.forEach { category ->

                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically){

                                Icon(
                                    imageVector = getIconFromString(category.categoryIcon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = TextWhite
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = category.categoryTitle,
                                    style = CapoType.cardTitle
                                )
                            }
                                   },
                            onClick = {
                                onCategorySelected(category)
                                dropdownExpand = false
                            }
                        )
                    }

                    if(viewMore){
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
    var dropdownExpand by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ExposedDropdownMenuBox(
                expanded = dropdownExpand,
                onExpandedChange = {
                    if (enabled) dropdownExpand = !dropdownExpand
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = selectedTransactionType,
                    onValueChange = {},
                    readOnly = true,
                    enabled = enabled,
                    placeholder = { Text(placeholderText, style = CapoType.cardTitle) },

                    textStyle = CapoType.cardTitle,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpand,
                    onDismissRequest = { dropdownExpand = false }
                ) {
                    transactionTypes.forEach { transactionTypes ->

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = transactionTypes,
                                    style = CapoType.cardTitle
                                )
                            },
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
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {

                if (enabled) {
                    val calendar = Calendar.getInstance()

                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val formatted = "$dayOfMonth/${month + 1}/$year"
                            onTransactionDateSelected(formatted)
                        },
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
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size((44.dp)),
                tint = TextWhite
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = if (selectedTransactionDate.isEmpty()) placeholderText else selectedTransactionDate,
                style = CapoType.cardTitle,
                color = if (selectedTransactionDate.isEmpty()) Color.Gray else TextWhite
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
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {

                if (enabled) {

                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->

                            // Converts to 12 hr format
                            var amPM = if (hourOfDay >= 12) "PM" else "AM"

                            val hour12 = when {
                                hourOfDay == 0 -> 12
                                hourOfDay > 12 -> hourOfDay - 12
                                else -> hourOfDay
                            }

                            val formatted = String.format("%02d:%02d %s", hour12, minute, amPM)
                            onTransactionTimeSelected(formatted)
                        },
                        12,
                        0,
                        true
                    ).show()
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size((44.dp)),
                tint = TextWhite
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(   // If the entered time hasnt been shown yet, show the placeholderText otherwise show the selectedTime for the Transaction
                text = if (selectedTransactionTime.isEmpty()) placeholderText else selectedTransactionTime,
                style = CapoType.cardTitle,
                color = if (selectedTransactionTime.isEmpty()) Color.Gray else TextWhite // sets chosen time to TextWhite otherwise it remains gray
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
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        onImageSelected(uri)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {

                photoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },

        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageUri == null) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size((44.dp))
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onAmountClicked() },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = CardBG),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Amount: ",
                style = CapoType.cardTitle
            )

            Spacer(modifier = Modifier.width(8.dp))

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "R $transactionAmount",
                style = CapoType.cardTitle
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                imageVector = cardIcon,
                contentDescription = "Adjust value",
                tint = TextWhite
            )
        }
    }
}

@Composable
fun BudgetCard(
    cardTitle: String,
    cardMin: Double?,
    cardMax: Double?,
    cardIcon: ImageVector,
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
                        color = Accent,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Icon passed as parameter
                Icon(
                    imageVector = cardIcon,
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
    budgetMin: Double?,
    budgetMax: Double?,
    daysRemaining: Int?,
    onClick: () -> Unit = {}
) {
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
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }
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
                    CardComponent(
                        "Dinner Night",
                        "Empire Steak",
                        "200",
                        "5:00 PM",
                        Icons.Default.Fastfood,
                        "expense"
                    )
                },
                {
                    CardComponent(
                        "Salary",
                        "Dunder Mifflin",
                        "30 000",
                        "9:45 AM",
                        Icons.Default.Payments,
                        "income"
                    )
                },
                {
                    BudgetCard(
                        "Gym",
                        200.0,
                        400.0,
                        "Gym",
                        "Teal"
                    )
                },
                {
                    CategoryCard(
                        "Food",
                        "Grey",
                        "Food"
                    )
                }
            )
        )
    }
}