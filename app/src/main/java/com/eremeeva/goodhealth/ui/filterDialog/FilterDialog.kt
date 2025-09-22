package com.eremeeva.goodhealth.ui.filterDialog

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import com.eremeeva.goodhealth.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FilterDialog(filterData: FilterData ) {

    private val inFilterData: FilterData = filterData
    private var outFilterData: FilterData = filterData

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    @Composable
    fun ShowFilter(
        onAcceptRequest: () -> Unit,
        onBackRequest: () -> Unit ) {

        Dialog(onDismissRequest = { onBackRequest() },
            DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false) )
        {
            val rbList = listOf(
                FilterPeriod.ALL.value,
                FilterPeriod.WEEK.value,
                FilterPeriod.MONTH.value,
                FilterPeriod.PERIOD.value)

            val periodType = remember { mutableStateOf(inFilterData.periodType) }
            val startDate = remember {mutableStateOf(inFilterData.startDate) }
            val endDate = remember {mutableStateOf(inFilterData.endDate)}
            val acceptClicked  = remember { mutableStateOf(false) }

            Card(modifier = Modifier
                .fillMaxWidth()
                ,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                shape = RoundedCornerShape(16.dp)
            ){
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.filter_title), style = typography.titleLarge) },
                            navigationIcon = {
                                IconButton(onClick = { onBackRequest() })
                                {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary),
                        )
                    },
                    bottomBar = {
                        TextButton(
                            onClick = { acceptClicked.value = true  },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                            ,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary)
                        ){
                            Text(
                                text = stringResource(R.string.filter_button_accept),
                                style = typography.labelMedium
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    innerPadding ->
                    var showPeriod  by  remember { mutableStateOf(inFilterData.periodType == FilterPeriod.PERIOD.value) }

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .selectableGroup()
                            .fillMaxWidth()
                            .padding(10.dp)
                            .verticalScroll(rememberScrollState())
                            .border(width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))
                    ) {
                        rbList.forEach { text ->
                            Row( modifier = Modifier
                                .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically )
                            {
                                RadioButton(
                                    selected = (text == periodType.value),
                                    onClick = {
                                        showPeriod = (text == FilterPeriod.PERIOD.value)
                                        periodType.value = text
                                        startDate.value = null
                                        endDate.value = null
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.onPrimary )
                                )
                                Text( text = text, modifier = Modifier .align(Alignment.CenterVertically),
                                    style = typography.labelMedium )
                            }
                        }

                        AnimatedVisibility(visible = showPeriod,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()){
                            Column(){
                                Row(modifier = Modifier
                                    .padding(top = 10.dp, bottom = 2.dp, start = 10.dp, end = 10.dp),
                                    horizontalArrangement = Arrangement.Center)
                                {
                                    Text("с", modifier = Modifier
                                        .padding(10.dp)
                                        .size(40.dp), style = typography.labelMedium )
                                    startDate.value = ShowDateField(startDate.value)
                                }

                                Row(modifier = Modifier
                                    .padding(top = 2.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
                                    horizontalArrangement = Arrangement.Center)
                                {
                                    Text("по", modifier = Modifier
                                        .padding(10.dp)
                                        .size(40.dp), style = typography.labelMedium )
                                    endDate.value = ShowDateField(endDate.value)
                                }
                            }
                        }

                        if (acceptClicked.value){
                            outFilterData = getDates( periodType.value, startDate.value, endDate.value)
                            onAcceptRequest()
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowDateField(date: Long?): Long? {
        val datePickerState = rememberDatePickerState(date)
        var showDialog by remember { mutableStateOf(false) }

        Box()
        {
            OutlinedTextField(
                value = if (datePickerState.selectedDateMillis != null)
                    convertMillisToDate(datePickerState.selectedDateMillis, "dd/MM/yyyy") else "",
                onValueChange = { },
                modifier = Modifier .padding(5.dp),
                readOnly = true,
                trailingIcon = {
                    IconButton( onClick = { showDialog = !showDialog } ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.filter_inputdate)
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
            )

            if (showDialog) {
                Popup(
                    onDismissRequest = { showDialog = false },
                    alignment = Alignment.TopStart
                ){
                    DatePickerModal( datePickerState, onDismiss = { showDialog = false } )
                }
            }
        }

        return datePickerState.selectedDateMillis
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePickerModal(
        datePickerState: DatePickerState,
        onDismiss: () -> Unit
    ) {
        val dateState = rememberDatePickerState()

        DatePickerDialog(
            modifier = Modifier .fillMaxWidth()
            ,
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    modifier = Modifier .width(140.dp),
                    onClick = {
                        datePickerState.selectedDateMillis = dateState.selectedDateMillis
                        onDismiss() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary)
                ){
                    Text("OK", style = typography.labelMedium)
                }
            },
            dismissButton = {
                TextButton(
                    modifier = Modifier .width(140.dp),
                    onClick = onDismiss,
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(10.dp)
                ){
                    Text("Отмена", style = typography.labelMedium)
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        ) {
            val df: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter(selectedDateSkeleton = "dd/MM/yyyy") }

            DatePicker(state = dateState,
                dateFormatter =  df,
                title = { Text("Введите дату:", modifier = Modifier .padding(10.dp), style = typography.labelMedium)},
                headline = {
                    Text(dateState.selectedDateMillis?.let {
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                } ?: "", modifier = Modifier .padding(10.dp)) },
                showModeToggle = true
            )
        }
    }

    private fun convertMillisToDate(millis: Long?, format: String): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(Date(millis ?: 0))
    }

    fun getFilterData(): FilterData{
        return outFilterData
    }

    private fun getDates(periodType: String, startDate: Long?, endDate: Long?): FilterData {

        val calendar: Calendar = Calendar.getInstance()

        var d1: Long = 0
        var d2: Long = 0

        when (periodType) {
            FilterPeriod.WEEK.value -> {
                calendar.add(Calendar.DAY_OF_MONTH, -7)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.timeInMillis.also { d1 = it }
                calendar.set(2100, 1,1,0, 0, 0)
                calendar.timeInMillis.also { d2 = it }
            }
            FilterPeriod.MONTH.value -> {
                calendar.add(Calendar.MONTH, -1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.timeInMillis.also { d1 = it }
                calendar.set(2100, 1,1,0, 0, 0)
                calendar.timeInMillis.also { d2 = it }
            }
            FilterPeriod.PERIOD.value -> {
                calendar.timeInMillis = startDate ?: 0
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.timeInMillis.also { d1 = it }

                calendar.timeInMillis = endDate  ?: 0
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.timeInMillis.also { d2 = it }
            }
        }

        /*
        val formatter = SimpleDateFormat(stringResource(R.string.datetimeformat), Locale.getDefault())
        val s1 = formatter.format(Date(d1 ?: 0))
        val s2 = formatter.format(Date(d2 ?: 0))
        */

        return FilterData(periodType, d1, d2)
    }
}




