package com.example.goodhealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.goodhealth.ui.theme.GoodHealthTheme

class MainActivity : ComponentActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
           GoodHealthTheme {
               GoodHealthApp(application, getApplicationContext())
            }
        }
    }
}


