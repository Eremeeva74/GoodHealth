package com.eremeeva.goodhealth.ui.mno

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eremeeva.goodhealth.R
import com.eremeeva.domain.models.MNOData
import java.util.Date
import java.util.Locale

@Composable
fun MNOItemDialog(
    mnoViewModel: MNOViewModel,
    onBackClicked: () -> Unit
){
    Dialog(onDismissRequest = { onBackClicked() },
        DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ) {
        var value1 by remember { mutableStateOf("") }
        var value2 by remember { mutableStateOf("") }
        var value3 by remember { mutableStateOf("") }

        val formatter = SimpleDateFormat(stringResource(R.string.datetimeformat), Locale.getDefault())

        Card(modifier = Modifier
            .fillMaxWidth()
            ,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            shape = RoundedCornerShape(16.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup ()
                    .padding(10.dp)
                    .border(width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))
            ) {
                val focusRequester = remember { FocusRequester() }

                Text(
                    text = stringResource(R.string.mnoitem_title),
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp),
                    style = typography.bodyMedium
                )
                TextField(
                    value = value1,
                    onValueChange = { value1 = it },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 10.dp, end = 20.dp)
                        .focusRequester(focusRequester)
                    ,
                    singleLine = true,
                    label = { Text(text = "МНО", style = typography.bodyMedium) },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor =  MaterialTheme.colorScheme.tertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedLabelColor =  MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        errorTextColor = MaterialTheme.colorScheme.onError,
                        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
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
                    label = { Text(text = "% по Квику", style = typography.bodyMedium) },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor =  MaterialTheme.colorScheme.tertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedLabelColor =  MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        errorTextColor = MaterialTheme.colorScheme.onError,
                        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
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
                    label = { Text(text = "Сек.", style = typography.bodyMedium) },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor =  MaterialTheme.colorScheme.tertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedLabelColor =  MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        errorTextColor = MaterialTheme.colorScheme.onError,
                        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = value3.isNotEmpty() && !isValidText(value3)
                )
            }

            Row(modifier = Modifier
                .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly){
                ElevatedButton(
                    onClick = {
                        if (value1.isNotEmpty() && isValidText(value1) &&
                            value2.isNotEmpty() && isValidText(value2) &&
                            value3.isNotEmpty() && isValidText(value3)){
                            val item = MNOData(
                                id = null,
                                creationDate = formatter.format(Date()),
                                value1 = value1.toDouble(),
                                value2 = value2.toDouble(),
                                value3 = value3.toDouble()
                            )
                            mnoViewModel.handleMNOIntent(MNOIntent.Add(item))
                            onBackClicked()
                        }
                    },
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary)
                ){
                    Text( text = stringResource(R.string.mnoitem_button_add), style = typography.labelMedium )
                }

                ElevatedButton(
                    onClick = { onBackClicked() },
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary)
                ){
                    Text( text = stringResource(R.string.mnoitem_button_close), style = typography.labelMedium )
                }
            }
        }
    }
}

fun isValidText(text: String): Boolean {
    try{
        text.toDouble()
        return true
    }
    catch(_: Exception){
    }

    return false
}



