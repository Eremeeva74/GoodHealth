package com.eremeeva.goodhealth.ui.mno

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eremeeva.domain.models.MNOData
import com.eremeeva.goodhealth.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MNOItemDialog(
    mnoViewModel: MNOViewModel,
    onBackClicked: () -> Unit
){
    Dialog(
        onDismissRequest = { onBackClicked() },
        DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ) {
        var value1 by remember { mutableStateOf("") }
        var value2 by remember { mutableStateOf("") }
        var value3 by remember { mutableStateOf("") }

        val formatter = DateTimeFormatter.ofPattern(stringResource(R.string.datetimeformat))

        Card(
            modifier = Modifier .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup()
                    .semantics { isTraversalGroup = true }
                    .padding(5.dp)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                val focusRequester = remember { FocusRequester() }

                Text(
                    text = stringResource(R.string.mnoitem_title),
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                    style = typography.titleSmall
                )

                TextField(
                    value = value1,
                    onValueChange = { value1 = it },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                        .focusRequester(focusRequester)
                        .semantics { traversalIndex = 1f },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.mnoitem_textfield_1), style = typography.bodySmall) },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = value1.isNotEmpty() && !isValidText(value1),
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
                        .semantics { traversalIndex = 2f },
                    label = { Text(text = stringResource(R.string.mnoitem_textfield_2), style = typography.bodySmall) },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = value2.isNotEmpty() && !isValidText(value2),
                )

                TextField(
                    value = value3,
                    onValueChange = { value3 = it },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                        .semantics { traversalIndex = 3f },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.mnoitem_textfield_3), style = typography.bodySmall) },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = value3.isNotEmpty() && !isValidText(value3),
                )
            }

            val buttons = listOf(
                stringResource(R.string.mnoitem_button_cancel),
                stringResource(R.string.mnoitem_button_add),
            )

            Row(
                modifier = Modifier .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                for (btn in buttons) {
                    val isButtonFocused = remember { mutableStateOf(false) }

                    ElevatedButton(
                        onClick = {
                            when (btn) {
                               "Добавить" -> {
                                    if (value1.isNotEmpty() && isValidText(value1) &&
                                        value2.isNotEmpty() && isValidText(value2) &&
                                        value3.isNotEmpty() && isValidText(value3)
                                    ) {
                                        val item = MNOData(
                                            id = null,
                                            creationDate = LocalDateTime.now().format(formatter),
                                            value1 = value1.toDouble(),
                                            value2 = value2.toDouble(),
                                            value3 = value3.toDouble()
                                        )
                                        mnoViewModel.handleMNOIntent(MNOIntent.Add(item))
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
                            .focusable()
                            .semantics{ role = Role.Button
                                        contentDescription = btn }
                        ,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = if (isButtonFocused.value) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primaryContainer,
                            contentColor = if (isButtonFocused.value)  MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onPrimaryContainer)
                    ){
                        Text( text = btn, style = typography.bodyMedium)
                    }
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



