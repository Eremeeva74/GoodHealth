package com.eremeeva.goodhealth.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eremeeva.goodhealth.GoodHealthScreens
import com.eremeeva.goodhealth.R
import com.eremeeva.goodhealth.ui.mno.MNOActivity
import com.eremeeva.goodhealth.ui.pressure.PressureActivity
import com.eremeeva.goodhealth.ui.pressure.PressureViewModel
import com.eremeeva.goodhealth.ui.theme.GoodHealthTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
           GoodHealthTheme {
               GoodHealthApp()
            }
        }
    }

    @SuppressLint("ContextCastToActivity")
    @Composable
    fun SetBarColor(){
        val statusBarLight = MaterialTheme.colorScheme.primary
        val statusBarDark = MaterialTheme.colorScheme.primary
        val navigationBarLight = MaterialTheme.colorScheme.primary
        val navigationBarDark = MaterialTheme.colorScheme.primary
        val isDarkMode = isSystemInDarkTheme()
        val context = LocalContext.current as ComponentActivity

        DisposableEffect(isDarkMode) {
            context.enableEdgeToEdge(
                statusBarStyle = if (!isDarkMode) {
                    SystemBarStyle.light(
                        statusBarLight.toArgb(),
                        statusBarDark.toArgb()
                    )
                } else SystemBarStyle.dark( statusBarDark.toArgb() ),
                navigationBarStyle = if(!isDarkMode){
                    SystemBarStyle.light(
                        navigationBarLight.toArgb(),
                        navigationBarDark.toArgb()
                    )
                }
                else { SystemBarStyle.dark(navigationBarDark.toArgb()) }
            )

            onDispose { }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun GoodHealthApp(
        navController: NavHostController = rememberNavController()
    ) {
        SetBarColor()

        val title = remember { mutableStateOf("") }

        //val dataStoreManager = DataStoreManager.getInstance(applicationContext)
        /*val coroutine = rememberCoroutineScope()
        coroutine.launch {
            dataStoreManager.writeFilterData(FilterData("все", 0, 0))
        }*/

        //val pressureViewModel: PressureViewModel = viewModel(
        //    factory = PressureViewModel.provideFactory(applicationContext)
        //)

        //val pressureScreen = PressureIndicatorScreen(pressureViewModel, dataStoreManager)

        NavHost(
            navController = navController,
            startDestination = GoodHealthScreens.START_ROUTE
        ) {
            composable(route = GoodHealthScreens.START_ROUTE) {
                title.value = stringResource(id = R.string.app_name)
                MainScreen(
                    title.value,
                    onMNOButtonClicked =
                    {
                            val intent = Intent(applicationContext, MNOActivity::class.java)
                            startActivity(intent)

                        //navController.navigate(GoodHealthScreens.MNO_ROUTE)
                    },
                    onPressureButtonClicked =
                    {
                        val intent = Intent(applicationContext, PressureActivity::class.java)
                        startActivity(intent)

                        //navController.navigate(GoodHealthScreens.PRESSURE_ROUTE)
                    },
                )
            }
/*
            composable(route = GoodHealthScreens.MNO_ROUTE) {
                //val intent = Intent(applicationContext, MNOActivity::class.java)
                //startActivity(intent)

                    /*title.value = stringResource(R.string.button_mno)
                mnoScreen.ShowScreen(
                    title.value,
                    { navController.navigate(GoodHealthScreens.MNO_ITEM_ROUTE) },
                    { navController.navigate(GoodHealthScreens.START_ROUTE) } )*/
            }

            composable(route = GoodHealthScreens.MNO_ITEM_ROUTE) {
                title.value = stringResource(R.string.button_mno)
                MNOItem(
                    title.value,
                    mnoViewModel,
                    onBackClicked = {
                        navController.navigate(GoodHealthScreens.MNO_ROUTE)
                        { popUpTo(GoodHealthScreens.MNO_ROUTE)
                        {
                            inclusive = true
                        }
                        }
                    }
                )
            }*/
/*
            composable(route = GoodHealthScreens.PRESSURE_ROUTE) {
                title.value = stringResource(R.string.button_pressure)
                pressureScreen.ShowScreen(
                    title.value,
                    { navController.navigate(GoodHealthScreens.PRESSURE_ITEM_ROUTE) },
                    { navController.navigate(GoodHealthScreens.START_ROUTE) } )
            }

            composable(route = GoodHealthScreens.PRESSURE_ITEM_ROUTE) {
                title.value = stringResource(R.string.button_pressure)
                PressureItem(
                    title.value,
                    pressureViewModel,
                    onBackClicked = {
                        navController.navigate(GoodHealthScreens.PRESSURE_ROUTE)
                        { popUpTo(GoodHealthScreens.PRESSURE_ROUTE)
                        {
                            inclusive = true
                        }
                        }
                    }
                )
            }

             */
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        title: String,
        onMNOButtonClicked: () -> Unit,
        onPressureButtonClicked: () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(title, style = typography.titleLarge) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary),
                )
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
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
            ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            for(btn in  buttons) {
                ElevatedButton(
                    onClick = when (btn){
                        stringResource(R.string.button_mno) -> onMNOButtonClicked
                        stringResource(R.string.button_pressure) -> onPressureButtonClicked
                        else -> onMNOButtonClicked
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                        .shadow(15.dp, shape = RoundedCornerShape(10.dp) ),
                    enabled = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),

                    ) { Text(text = btn, style = typography.titleMedium) }
            }
        }
    }
}

