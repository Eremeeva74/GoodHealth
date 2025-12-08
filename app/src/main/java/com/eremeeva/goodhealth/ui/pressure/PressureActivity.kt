package com.eremeeva.goodhealth.ui.pressure

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.eremeeva.domain.models.PressureData
import com.eremeeva.goodhealth.R
import com.eremeeva.goodhealth.ui.filterDialog.FilterDialog
import com.eremeeva.goodhealth.ui.theme.GoodHealthTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import kotlin.math.min

@AndroidEntryPoint
class PressureActivity : ComponentActivity() {

    private val pressureViewModel: PressureViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            GoodHealthTheme {
                ShowScreen()
            }
        }
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowScreen() {
        val vmState = pressureViewModel.state.collectAsState().value
        val pressureList by vmState.pressureList.collectAsState(initial = emptyList())
        val expandedMenu = remember { mutableStateOf(false) }

        val createPdfLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.CreateDocument("application/pdf")) { uri: Uri? ->
            createPdf(uri, pressureList)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.button_pressure), style = typography.headlineLarge) },
                    navigationIcon = {
                        IconButton(onClick = { finish() },
                            modifier = Modifier .semantics{ role = Role.Button })
                        {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.pressure_activity_back),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { pressureViewModel.handlePressureIntent(PressureIntent.ShowFilterDialog(true))},
                                modifier = Modifier .semantics{ role = Role.Button })
                        {
                            Icon(
                                painter = painterResource(R.drawable.ic_filter_alt),
                                contentDescription = stringResource(R.string.pressure_activity_filter),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Box(modifier = Modifier .padding(5.dp)){
                            IconButton(onClick = { expandedMenu.value = !expandedMenu.value },
                                    modifier = Modifier .semantics{ role = Role.Button }) {
                                Icon(painterResource(R.drawable.ic_menu),
                                    contentDescription = stringResource(R.string.pressure_activity_menu),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                            DropdownMenu(
                                expanded = expandedMenu.value,
                                onDismissRequest = { expandedMenu.value = false },
                                modifier = Modifier .padding(5.dp),
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            ) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.pressure_activity_download), style = typography.titleSmall) },
                                    onClick = { createPdfLauncher.launch("pressure.pdf")
                                                expandedMenu.value = false },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_download),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                    },
                                    colors = MenuItemColors(
                                        textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                        leadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                        trailingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                        disabledTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                        disabledLeadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                        disabledTrailingIconColor = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                )
            },
            floatingActionButton = {
                LargeFloatingActionButton(
                    onClick = { pressureViewModel.handlePressureIntent( PressureIntent.ShowPressureItem(true)) },
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = stringResource(R.string.pressure_activity_add)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.EndOverlay,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            innerPadding ->
            if (vmState.loading){
                Box(modifier = Modifier .fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
            else{
                val listState = rememberLazyListState()
                LazyColumn (
                    modifier = Modifier .fillMaxSize() .padding(innerPadding),
                    contentPadding = PaddingValues(5.dp),
                    state = listState,
                    content = {
                        runBlocking {
                            items( count = pressureList.count() )
                            {
                                    index -> ShowCard( pressureList[index] ){
                                pressureViewModel.handlePressureIntent( PressureIntent.Delete(pressureList[index]) )
                            }
                            }
                        }
                    },
                )
            }

            AnimatedVisibility(visible = vmState.showFilterDialog){
                val filterDialog = FilterDialog(pressureViewModel.state.value.filterData)
                filterDialog.ShowFilter(
                    onAcceptRequest =
                    {
                        pressureViewModel.handlePressureIntent(PressureIntent.SetFilterData(filterDialog.getFilterData()))
                        pressureViewModel.handlePressureIntent(PressureIntent.ShowFilterDialog(false))
                    },
                    onBackRequest = { pressureViewModel.handlePressureIntent(PressureIntent.ShowFilterDialog(false)) }
                )
            }

            AnimatedVisibility(visible = vmState.showPressureItemDialog){
                PressureItemDialog(pressureViewModel, onBackClicked = { pressureViewModel.handlePressureIntent( PressureIntent.ShowPressureItem(false)) })
            }
        }
    }

    @Composable
    fun ShowCard(
        item: PressureData,
        onDeleteItem: () -> Unit )
    {
        val focusRequester = remember { FocusRequester() }
        val showDeleteDialog = remember { mutableStateOf(false) }

        val focusedContainerColor = MaterialTheme.colorScheme.secondary
        val unFocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
        val focusedContentColor = MaterialTheme.colorScheme.onSecondary
        val unFocusedContentColor = MaterialTheme.colorScheme.onSecondaryContainer

        val containerColor = remember { mutableStateOf(unFocusedContainerColor)}
        val contentColor = remember { mutableStateOf(unFocusedContentColor)}

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .onFocusChanged {
                    if (it.isFocused ){
                        containerColor.value  = focusedContainerColor
                        contentColor.value = focusedContentColor
                    }
                    else{
                        containerColor.value = unFocusedContainerColor
                        contentColor.value = unFocusedContentColor
                    }
                }
                .focusable()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusRequester.requestFocus()
                    containerColor.value  = focusedContainerColor
                    contentColor.value = focusedContentColor
                }
                .focusRequester(focusRequester)
                .focusable()
            ,
            colors = CardDefaults.cardColors(
                containerColor = containerColor.value,
                contentColor = contentColor.value,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ){
            Row(modifier = Modifier .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(modifier = Modifier .weight(5f),
                )
                {
                    Text(item.creationDate, style = typography.bodyLarge)
                    Spacer(modifier = Modifier.size(5.dp))
                    HorizontalDivider(modifier = Modifier .padding(end = 10.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(modifier = Modifier.size(5.dp))
                    Row()
                    {
                        Text("${item.upValue} / ${item.downValue}", style = typography.bodyLarge,
                            modifier = Modifier .weight(1f))
                        Text(String.format("Пульс: ${item.pulse}"), style = typography.bodyLarge,
                            modifier = Modifier .weight(1f))
                    }
                }

                IconButton (
                    onClick = { showDeleteDialog.value = true },
                    modifier = Modifier .weight(1f) .size(50.dp),
                )
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = stringResource(R.string.pressure_activity_delete),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        AnimatedVisibility(visible = showDeleteDialog.value){
            val btnColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

            AlertDialog(
                onDismissRequest = { showDeleteDialog.value = false },
                modifier = Modifier .fillMaxWidth(),
                title = { Text(text = stringResource(R.string.deletedialog_caption), style = typography.titleSmall) },
                text = { Text(text = stringResource(R.string.deletedialog_text), style = typography.bodyLarge) },
                confirmButton = {
                    Button(
                        onClick = {
                            onDeleteItem()
                            showDeleteDialog.value = false
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = btnColors
                    ) {
                        Text( text = stringResource(R.string.deletedialog_btn_delete), style = typography.titleSmall )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDeleteDialog.value = false },
                        shape = RoundedCornerShape(16.dp),
                        colors = btnColors
                    ){
                        Text(text = stringResource(R.string.deletedialog_btn_cancel), style = typography.titleSmall)
                    }
                }
            )
        }
    }

    private fun createPdf(uri: Uri?, list: List<PressureData>?){
        if (uri != null &&  list != null) {
            val document = PdfDocument()

            val width = 612
            val height = 792
            val pageMargin = 20

            val numberOfColumns = 3
            val numberOfRows = list.count()
            val cellWidth = (width - pageMargin * 2) / numberOfColumns
            val cellHeight = 15
            val linePaint = Paint().apply { style = Paint.Style.STROKE; strokeWidth = 1f }
            val textPaint = Paint().apply { style = Paint.Style.FILL; textSize = 12f }
            val textCaptionPaint = Paint().apply { style = Paint.Style.FILL; textSize = 12f }
            textCaptionPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

            val linePerPage = (height - pageMargin * 2) / (cellHeight) - 1
            var pageNumber = 1
            var index = 0
            while (index < numberOfRows){

                val pageInfo = PdfDocument.PageInfo.Builder(width, height, pageNumber).create()
                val page = document.startPage(pageInfo)
                val canvas = page.canvas

                // caption
                canvas.drawLine(pageMargin.toFloat(),
                    pageMargin.toFloat(),
                    width.toFloat() - pageMargin,
                    pageMargin.toFloat(),
                    linePaint)

                for (j in 0..< numberOfColumns) {
                    val y = pageMargin + cellHeight.toFloat() - 3f
                    when (j){
                        0 -> canvas.drawText("Дата", pageMargin + 5 + j * cellWidth.toFloat(),
                            y, textCaptionPaint)
                        1 -> canvas.drawText("Давление", pageMargin + 5 + j * cellWidth.toFloat(),
                            y, textCaptionPaint)
                        2 -> canvas.drawText("Пульс", pageMargin + 5 + j * cellWidth.toFloat(),
                            y, textCaptionPaint)
                    }
                }

                canvas.drawLine(pageMargin.toFloat(),
                    pageMargin.toFloat() + cellHeight,
                    width.toFloat() - pageMargin,
                    pageMargin.toFloat() + cellHeight,
                    linePaint)

                // text and horizontal lines
                for(i in 0..< min(linePerPage - 1, numberOfRows - index)){
                    val y = pageMargin + i * cellHeight.toFloat() - 3f + 2 * cellHeight.toFloat()
                    for (j in 0..< numberOfColumns) {
                        when (j){
                            0 -> canvas.drawText(list[index + i].creationDate, pageMargin + 5 + j * cellWidth.toFloat(),
                                y, textPaint)
                            1 -> canvas.drawText("${list[index + i].upValue} / ${list[index + i].downValue}", pageMargin + 5 + j * cellWidth.toFloat(),
                                y, textPaint)
                            2 -> canvas.drawText(list[index + i].pulse.toString(), pageMargin + 5 + j * cellWidth.toFloat(),
                                y, textPaint)
                        }
                    }

                    canvas.drawLine(
                        pageMargin.toFloat() ,
                        pageMargin.toFloat() + i * cellHeight.toFloat() + 2 * cellHeight.toFloat(),
                        width.toFloat() - pageMargin,
                        pageMargin.toFloat() + i * cellHeight.toFloat() + 2 * cellHeight.toFloat(),
                        linePaint)
                }

                // vertical lines
                for (j in 0..numberOfColumns){
                    canvas.drawLine(pageMargin.toFloat() + j * cellWidth.toFloat(),
                        pageMargin.toFloat(),
                        pageMargin.toFloat() + j * cellWidth.toFloat(),
                        min(linePerPage, numberOfRows - index + 1) * cellHeight + pageMargin.toFloat(),
                        linePaint)
                }

                document.finishPage(page)
                pageNumber++
                index += min(linePerPage - 1, numberOfRows - index)
            }

            contentResolver.openOutputStream(uri)?.use {
                document.writeTo(it)
            }

            document.close()
        }
    }
}



