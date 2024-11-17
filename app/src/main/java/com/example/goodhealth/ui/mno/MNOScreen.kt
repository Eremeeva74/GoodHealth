package com.example.goodhealth.ui.mno

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
import androidx.compose.ui.unit.dp
import com.example.goodhealth.R
import com.example.goodhealth.data.database.model.MNOEntity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MNOScreen(
    mnoViewModel: MNOViewModel,
    onMNOAddClicked: () -> Unit
) {
    Scaffold(
        topBar = {

        },
        floatingActionButton = {
            FloatingActionButton(onClick = onMNOAddClicked,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Outlined.Add, contentDescription = null)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        innerPadding ->
            MNODataList(mnoViewModel, innerPadding)
    }
}

@Composable
fun MNODataList(
    mnoViewModel: MNOViewModel,
    innerPadding: PaddingValues,
) {
    val mnoList by mnoViewModel.mnoList.observeAsState(listOf())

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(start = 5.dp, end = 5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items( count = mnoList.count() ) {
            index ->
                ShowMNOCard(mnoViewModel,
                mnoList[index] )
        }
    }
}

@Composable
fun ShowMNOCard(mnoViewModel: MNOViewModel,
                item : MNOEntity,
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
                    Text(item.value1.toString(), style = typography.bodyMedium,
                        modifier = Modifier .weight(1f))
                    Text(item.value2.toString(), style = typography.bodyMedium,
                        modifier = Modifier .weight(1f))
                    Text(item.value3.toString(), style = typography.bodyMedium,
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
                        mnoViewModel.delete(item)
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
