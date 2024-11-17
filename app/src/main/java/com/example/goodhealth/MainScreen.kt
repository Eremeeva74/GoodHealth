package com.example.goodhealth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onMNOButtonClicked: () -> Unit,
    onPressureButtonClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        innerPadding ->
            ShowButtons(innerPadding,
                onMNOButtonClicked,
                onPressureButtonClicked)
    }
}

@Composable
fun ShowButtons(innerPadding: PaddingValues,
                onMNOButtonClicked: () -> Unit,
                onPressureButtonClicked: () -> Unit
) {
    val buttons = listOf(stringResource(R.string.button_pressure),
        stringResource(R.string.button_mno),
        //stringResource(R.string.button_temperature)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        for(btn in  buttons) {
           ElevatedButton(
                onClick = when (btn){
                    stringResource(R.string.button_mno) -> onMNOButtonClicked
                    stringResource(R.string.button_pressure) -> onPressureButtonClicked
                    stringResource(R.string.button_temperature) -> onMNOButtonClicked
                    else -> onMNOButtonClicked
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp)
                    .shadow(
                        15.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(5.dp)
                    ,
                enabled = true,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ),

            ) {
                Text(
                    text = btn,
                    style = typography.titleMedium

                )
            }
        }
    }


}



