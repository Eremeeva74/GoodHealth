package com.eremeeva.goodhealth.ui.pressure

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eremeeva.domain.models.PressureData
import com.eremeeva.goodhealth.R
import java.util.Date
import java.util.Locale


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PressureItemDialog(
    pressureViewModel: PressureViewModel,
    onBackClicked: () -> Unit
) {
    Dialog(onDismissRequest = { onBackClicked() },
        DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ){
        var upValue by remember { mutableStateOf("") }
        var downValue by remember { mutableStateOf("") }
        var pulse by remember { mutableStateOf("") }

        val formatter = SimpleDateFormat(stringResource(R.string.datetimeformat), Locale.getDefault())

        Card(modifier = Modifier
            .fillMaxWidth()
            ,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            shape = RoundedCornerShape(16.dp)){

            Column(modifier = Modifier
                .fillMaxWidth()
                .selectableGroup ()
                .padding(10.dp)
                .border(width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))
            ){
                val focusRequester = remember { FocusRequester() }

                Text(text = stringResource(R.string.pressureitem_pressure),
                    modifier = Modifier .padding(10.dp), style = typography.labelMedium)
                Row(verticalAlignment = Alignment.CenterVertically){
                    TextField(
                        value = upValue,
                        onValueChange = { upValue = it },
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged {}
                            .width(80.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor =  MaterialTheme.colorScheme.tertiaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            focusedLabelColor =  MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = upValue.isNotEmpty() && !isValidText(upValue)
                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    Text(text = "/",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp),
                        style = typography.titleMedium)
                    TextField(
                        value = downValue,
                        onValueChange = { downValue = it },
                        singleLine = true,
                        modifier = Modifier
                            .width(80.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor =  MaterialTheme.colorScheme.tertiaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            focusedLabelColor =  MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = downValue.isNotEmpty() && !isValidText(downValue)
                    )
                }

                Text(text = stringResource(R.string.pressureitem_pulse),
                    modifier = Modifier .padding(10.dp), style = typography.labelMedium)
                Row{
                    TextField(
                        value = pulse,
                        onValueChange = { pulse = it },
                        modifier = Modifier
                            .padding(start = 15.dp, bottom = 10.dp)
                            .width(80.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor =  MaterialTheme.colorScheme.tertiaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            focusedLabelColor =  MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = pulse.isNotEmpty() && !isValidText(pulse)
                    )

            }
            }

            Row(modifier = Modifier
                .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly){
                ElevatedButton(
                    onClick =
                    {
                        if (upValue.isNotEmpty() && isValidText(upValue) &&
                            downValue.isNotEmpty() && isValidText(downValue) &&
                            pulse.isNotEmpty() && isValidText(pulse)){
                            val item = PressureData(id = null, creationDate = formatter.format(Date()),
                                upValue = upValue.toInt(),
                                downValue = downValue.toInt(),
                                pulse = pulse.toInt()
                            )

                            pressureViewModel.handlePressureIntent(PressureIntent.Add(item))
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
                    Text(
                        text = stringResource(R.string.pressureitem_button_add),
                        style = typography.labelMedium
                    )
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
                    Text(
                        text = stringResource(R.string.pressureitem_button_close),
                        style = typography.labelMedium
                    )
                }
            }
        }
    }
}

fun isValidText(text: String): Boolean {
    val result = false

    try{
        text.toInt()
        return true
    }
    catch(_: Exception){
    }

    return result
}