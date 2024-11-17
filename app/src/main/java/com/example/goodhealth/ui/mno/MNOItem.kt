package com.example.goodhealth.ui.mno

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.goodhealth.R
import com.example.goodhealth.data.database.model.MNOEntity
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MNOItem(
    mnoViewModel: MNOViewModel,
    onBackClicked: () -> Unit
) {
    var value1 by remember { mutableStateOf("") }
    var value2 by remember { mutableStateOf("") }
    var value3 by remember { mutableStateOf("") }

    var isValid1 by remember { mutableStateOf(false) }
    var isValid2 by remember { mutableStateOf(false) }
    var isValid3 by remember { mutableStateOf(false) }

    val formatter = SimpleDateFormat(stringResource(R.string.dateformat), Locale.getDefault())

    Scaffold(
        topBar = {
            /*TODO*/
        },
        bottomBar = {
            Column(){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                )
                {
                    IconButton(
                        onClick = {
                            isValid1 = !value1.isEmpty() && isValidText(value1)
                            isValid2 = !value2.isEmpty() && isValidText(value2)
                            isValid3 = !value3.isEmpty() && isValidText(value3)

                            if (isValid1 && isValid2 && isValid3){
                                mnoViewModel.add(
                                    MNOEntity(id = null, creationDate = formatter.format(Date()),
                                    value1 = value1.toDouble(), value2 = value2.toDouble(), value3 = value3.toDouble()
                                    )
                                )
                                onBackClicked()
                            }
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary)
                    )
                    {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = null,
                            modifier = Modifier
                            ,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                    }

                    IconButton(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                        ,
                        onClick = {
                            value1 = ""
                            value2 = ""
                            value3 = ""
                        }
                    )
                    {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier
                            ,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                )
                {
                    Text(
                        text = stringResource(R.string.button_add),
                        style = typography.titleSmall
                    )

                    Text(
                        text = stringResource(R.string.button_clear),
                        style = typography.titleSmall
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .border(width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
        ) {
            val focusRequester = remember { FocusRequester() }

            TextField(
                value = value1,
                onValueChange = { value1 = it },
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, bottom = 10.dp, end = 20.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                    }
                ,
                singleLine = true,
                label = { Text(text = "МНО", style = typography.labelMedium) },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = value1.isNotEmpty() && !isValidText(value1)
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            TextField(
                value = value2,
                onValueChange = { value2 = it },
                singleLine = true,
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                ,
                label = { Text(text = "% по Квику", style = typography.labelMedium) },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = value2.isNotEmpty() && !isValidText(value2)
            )

            TextField(
                value = value3,
                onValueChange = { value3 = it },
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, bottom = 20.dp, end = 20.dp)
                ,
                singleLine = true,
                label = { Text(text = "Сек.", style = typography.labelMedium) },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = value3.isNotEmpty() && !isValidText(value3)
            )
        }
    }
}


fun isValidText(text: String): Boolean {
    val result = false

    try{
        text.toDouble()
        return true
    }
    catch(_: Exception){
    }

    return result
}


