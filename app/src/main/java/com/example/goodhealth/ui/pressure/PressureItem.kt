package com.example.goodhealth.ui.pressure

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.goodhealth.R
import com.example.goodhealth.data.database.model.MNOEntity
import com.example.goodhealth.data.database.model.PressureEntity
import com.example.goodhealth.ui.mno.MNOViewModel
import java.util.Date
import java.util.Locale

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PressureItem(
    pressureViewModel: PressureViewModel,
    onBackClicked: () -> Unit
) {
    var upValue by remember { mutableStateOf("") }
    var downValue by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }

    var isValidUp by remember { mutableStateOf(false) }
    var isValidDown by remember { mutableStateOf(false) }
    var isValidPulse by remember { mutableStateOf(false) }

    val formatter = SimpleDateFormat(stringResource(R.string.dateformat), Locale.getDefault())

    Scaffold(
        topBar = {
            /*TODO*/
        },
        bottomBar = {
            Column(){
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                )
                {
                    IconButton(
                        onClick = {
                            isValidUp = !upValue.isEmpty() && isValidText(upValue)
                            isValidDown = !downValue.isEmpty() && isValidText(downValue)
                            isValidPulse = !pulse.isEmpty() && isValidText(pulse)

                            if (isValidUp && isValidDown && isValidPulse){
                                pressureViewModel.add(
                                    PressureEntity(id = null, creationDate = formatter.format(Date()),
                                        upValue = upValue.toInt(),
                                        downValue = downValue.toInt(),
                                        pulse = pulse.toInt()
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
                            upValue = ""
                            downValue = ""
                            pulse = ""
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
                    modifier = Modifier .fillMaxWidth()
                        .padding(bottom = 5.dp),
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

            Text(text = "Давление",
                modifier = Modifier
                    .padding(10.dp)
                ,style = typography.labelMedium)
            Row(verticalAlignment = Alignment.CenterVertically){
                TextField(
                    value = upValue,
                    onValueChange = { upValue = it },
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                        }
                        .width(80.dp)
                    ,
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
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
                        .width(80.dp)
                    ,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = downValue.isNotEmpty() && !isValidText(downValue)
                )
            }

            Text(text = "Пульс",
                modifier = Modifier
                    .padding(10.dp)
                ,style = typography.labelMedium)
            Row(){
                TextField(
                    value = pulse,
                    onValueChange = { pulse = it },
                    modifier = Modifier
                        .padding(start = 15.dp, bottom = 10.dp)
                        .width(80.dp)
                    ,
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = pulse.isNotEmpty() && !isValidText(pulse)
                )
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