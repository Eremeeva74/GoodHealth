package com.eremeeva.goodhealth.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.eremeeva.goodhealth.R
import com.eremeeva.goodhealth.ui.mno.MNOActivity
import com.eremeeva.goodhealth.ui.pressure.PressureActivity
import com.eremeeva.goodhealth.ui.theme.GoodHealthTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            GoodHealthTheme {
                GoodHealthApp()
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun GoodHealthApp() {
        MainScreen(
            stringResource(id = R.string.app_name),
            onMNOButtonClicked =
                {
                    val intent = Intent(applicationContext, MNOActivity::class.java)
                    startActivity(intent)
                },
            onPressureButtonClicked =
                {
                    val intent = Intent(applicationContext, PressureActivity::class.java)
                    startActivity(intent)
                },
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        title: String,
        onMNOButtonClicked: () -> Unit,
        onPressureButtonClicked: () -> Unit,
    ) {
        GoodHealthTheme {
            Scaffold(
                modifier = Modifier .fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(title, style = typography.headlineLarge) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                    )
                },
                containerColor = MaterialTheme.colorScheme.background
            ) {
                innerPadding ->
                ShowButtons(innerPadding,
                    onMNOButtonClicked,
                    onPressureButtonClicked)
            }
        }
    }

    @Composable
    fun ShowButtons(innerPadding: PaddingValues,
                    onMNOButtonClicked: () -> Unit,
                    onPressureButtonClicked: () -> Unit
    ) {
        val buttons = listOf(
            stringResource(R.string.button_mno),
            stringResource(R.string.button_pressure)
        )

        val brush = Brush.verticalGradient(listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.inversePrimary))

        Box(modifier = Modifier
            .fillMaxSize()
            .background(brush))
        {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                for (btn in buttons) {
                    ElevatedButton(
                        onClick =
                                when (btn) {
                                    stringResource(R.string.button_mno) -> onMNOButtonClicked
                                    stringResource(R.string.button_pressure) -> onPressureButtonClicked
                                    else -> onMNOButtonClicked
                                },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                            .dropShadow(
                                shape = RoundedCornerShape(16.dp),
                                shadow = Shadow(
                                    radius = 10.dp,
                                    spread = 6.dp,
                                    color = Color(0x40000000),
                                    offset = DpOffset(x = 6.dp, 6.dp)
                                )
                            )
                            .semantics{ role = Role.Button },
                        enabled = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        contentPadding = PaddingValues()
                    )
                    {
                        Text(text = btn, style = typography.titleLarge)
                    }
                }
            }
        }
    }
}

