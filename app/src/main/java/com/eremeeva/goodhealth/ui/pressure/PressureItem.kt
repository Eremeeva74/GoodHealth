package com.eremeeva.goodhealth.ui.pressure

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
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
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eremeeva.domain.models.PressureData
import com.eremeeva.goodhealth.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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

        val formatter = DateTimeFormatter.ofPattern(stringResource(R.string.datetimeformat))

        Card(modifier = Modifier .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .semantics { isTraversalGroup = true }
                .padding(5.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
            ){
                val focusRequester = remember { FocusRequester() }

                Text(text = stringResource(R.string.pressureitem_pressure),
                    modifier = Modifier .padding(10.dp), style = typography.bodyLarge)
                Row(modifier = Modifier .padding(10.dp), verticalAlignment = Alignment.CenterVertically){
                    TextField(
                        value = upValue,
                        onValueChange = { upValue = it },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .width(80.dp)
                            .semantics { traversalIndex = 1f },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = upValue.isNotEmpty() && !isValidText(upValue)
                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    Text(text = "/",
                        modifier = Modifier .padding(start = 10.dp, end = 10.dp),
                        style = typography.bodyLarge)
                    TextField(
                        value = downValue,
                        onValueChange = { downValue = it },
                        modifier = Modifier
                            .width(80.dp)
                            .semantics { traversalIndex = 2f },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = downValue.isNotEmpty() && !isValidText(downValue)
                    )
                }

                Text(text = stringResource(R.string.pressureitem_pulse),
                    modifier = Modifier .padding(10.dp), style = typography.bodyLarge)
                Row(modifier = Modifier .padding(10.dp)){
                    TextField(
                        value = pulse,
                        onValueChange = { pulse = it },
                        modifier = Modifier
                            .width(80.dp)
                            .semantics { traversalIndex = 3f },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = pulse.isNotEmpty() && !isValidText(pulse)
                    )
                }
            }

            val btnTextAdd = stringResource(R.string.mnoitem_button_add)
            val btnTextCancel = stringResource(R.string.mnoitem_button_cancel)

            val buttons = listOf( btnTextCancel, btnTextAdd )

            Row(modifier = Modifier .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                for (btn in buttons) {
                    val isButtonFocused = remember { mutableStateOf(false) }

                    ElevatedButton(
                        onClick =
                            {
                               when (btn){
                                   btnTextAdd -> {
                                       if (upValue.isNotEmpty() && isValidText(upValue) &&
                                           downValue.isNotEmpty() && isValidText(downValue) &&
                                           pulse.isNotEmpty() && isValidText(pulse)){
                                           val item = PressureData(id = null, creationDate = LocalDateTime.now().format(formatter),
                                               upValue = upValue.toInt(),
                                               downValue = downValue.toInt(),
                                               pulse = pulse.toInt()
                                           )

                                           pressureViewModel.handlePressureIntent(PressureIntent.Add(item))
                                           onBackClicked()
                                       }
                                   }
                                   else -> onBackClicked()
                               }
                            },
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f)
                            .onFocusChanged { focusState ->
                                isButtonFocused.value = focusState.isFocused
                            }
                            .focusable(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = if (isButtonFocused.value) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primaryContainer,
                            contentColor = if (isButtonFocused.value)  MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Text( text = btn, style = typography.bodyMedium)
                    }
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