package com.example.goodhealth.ui.pressure

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.goodhealth.R
import com.example.goodhealth.data.database.model.MNOEntity
import com.example.goodhealth.data.database.model.PressureEntity
import com.example.goodhealth.ui.mno.MNODataList
import com.example.goodhealth.ui.mno.MNOViewModel
import com.example.goodhealth.ui.mno.ShowMNOCard

@Composable
fun PressureScreen(
    pressureViewModel: PressureViewModel,
    onAddClicked: () -> Unit
) {
    //Text("PressureScreen Not yet implemented")
    Scaffold(
        topBar = {
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClicked,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Outlined.Add, contentDescription = null)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        innerPadding ->
            PressureList(pressureViewModel, innerPadding)
    }
}

@Composable
fun PressureList(
    pressureViewModel: PressureViewModel,
    innerPadding: PaddingValues,
) {
    val pressureList by pressureViewModel.pressureList.observeAsState(listOf())

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(start = 5.dp, end = 5.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items( count = pressureList.count() ) {
                index -> ShowPressureCard(pressureViewModel, pressureList[index] )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ShowPressureCard(pressureViewModel: PressureViewModel,
                item : PressureEntity
)
{
    val showDialog = remember { mutableStateOf(false) }
    val cardContentColor: Color = MaterialTheme.colorScheme.onSecondary
    val cardContainerColor: Color = MaterialTheme.colorScheme.secondary
    val borderColor: Color = MaterialTheme.colorScheme.primary

    ElevatedCard(
        onClick = {
        },

        modifier = Modifier
            .fillMaxWidth()
            .border(width = 3.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
        ,
        colors = CardDefaults.cardColors(
            containerColor = cardContainerColor,
            contentColor = cardContentColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp ),

        ){
        Row (){
            Column(modifier = Modifier
                .padding(10.dp)
                .weight(5f),
            )
            {
                Text(item.creationDate, style = typography.bodyLarge)
                Spacer(modifier = Modifier.size(5.dp))
                HorizontalDivider(modifier = Modifier .padding(end = 10.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.size(5.dp))
                Row()
                {
                    Text(String.format("%d / %d", item.upValue, item.downValue), style = typography.bodyMedium,
                        modifier = Modifier .weight(1f))
                    Text(String.format("Пульс: %d", item.pulse), style = typography.bodyMedium,
                        modifier = Modifier .weight(1f))
                }
            }

            IconButton (
                onClick = { showDialog.value = true },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .size(50.dp)
                ,
            )
            {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    if (showDialog.value){
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Подтверждение действия") },
            text = { Text("Вы действительно хотите удалить выбранный элемент?") },
            confirmButton = {
                Button(
                    onClick = {
                        pressureViewModel.delete(item)
                        showDialog.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.button_delete),
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(
                        text = stringResource(R.string.button_cancel),
                        color = Color.White
                    )
                }
            }
        )
    }
}
