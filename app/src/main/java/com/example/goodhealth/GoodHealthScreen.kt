package com.example.goodhealth

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodhealth.ui.mno.MNOItem
import com.example.goodhealth.ui.mno.MNOScreen
import com.example.goodhealth.ui.mno.MNOViewModel
import com.example.goodhealth.ui.pressure.PressureItem
import com.example.goodhealth.ui.pressure.PressureScreen
import com.example.goodhealth.ui.pressure.PressureViewModel

object GoodHealthScreens {
    const val START_ROUTE = "start"
    const val MNO_ROUTE = "mno"
    const val MNO_ITEM_ROUTE = "mnoItem"
    const val PRESSURE_ROUTE = "pressure"
    const val PRESSURE_ITEM_ROUTE = "pressureItem"
    const val TEMPERATURE_ROUTE = "temperature"
    const val TEMPERATURE_ITEM_ROUTE = "temperatureItem"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodHealthApp(
    application: Application,
    appContext: Context,
    navController: NavHostController = rememberNavController()
) {
    var title = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title.value, style = typography.titleLarge, ) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary),
            )
        }
    ) { innerPadding ->
        val mnoViewModel: MNOViewModel = viewModel(
            factory = MNOViewModel.provideFactory(application, appContext)
        )

        val pressureViewModel: PressureViewModel = viewModel(
            factory = PressureViewModel.provideFactory(appContext)
        )

        NavHost(
            navController = navController,
            startDestination = GoodHealthScreens.START_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = GoodHealthScreens.START_ROUTE) {
                title.value = stringResource(id = R.string.app_name)
                MainScreen(
                    onMNOButtonClicked = { navController.navigate(GoodHealthScreens.MNO_ROUTE) },
                    onPressureButtonClicked = { navController.navigate(GoodHealthScreens.PRESSURE_ROUTE) },
                )
            }

            composable(route = GoodHealthScreens.MNO_ROUTE) {
                title.value = "МНО"
                MNOScreen(
                    mnoViewModel,
                    onMNOAddClicked = { navController.navigate(GoodHealthScreens.MNO_ITEM_ROUTE) },
                )
            }

            composable(route = GoodHealthScreens.MNO_ITEM_ROUTE) {
                title.value = "МНО"
                MNOItem(
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
            }

            composable(route = GoodHealthScreens.PRESSURE_ROUTE) {
                title.value = "Давление"
                PressureScreen(
                    pressureViewModel,
                    onAddClicked = { navController.navigate(GoodHealthScreens.PRESSURE_ITEM_ROUTE) },
                )
            }

            composable(route = GoodHealthScreens.PRESSURE_ITEM_ROUTE) {
                title.value = "Давление"
                PressureItem(
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


        }
    }
}



