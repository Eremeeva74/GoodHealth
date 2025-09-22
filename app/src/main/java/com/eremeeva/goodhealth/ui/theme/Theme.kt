package com.eremeeva.goodhealth.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CambrigeBlue,
    onPrimary = CharCoal,
    secondary = TeaGreen,
    onSecondary = CharCoal,
    //surface = Bone,
    surface = TeaGreen,
    onSurface = CharCoal,
    tertiaryContainer = White,
    errorContainer = Red_50,
    onErrorContainer = Red_900
)

private val LightColorScheme = lightColorScheme(
    primary = CambrigeBlue,
    onPrimary = CharCoal,
    secondary = TeaGreen,
    onSecondary = CharCoal,
    //surface = Bone,
    surface = TeaGreen,
    onSurface = CharCoal,
    tertiaryContainer = White,
    errorContainer = Red_50,
    onErrorContainer = Red_900,
    onError = Red_500,
)

@Composable
fun GoodHealthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when { darkTheme -> DarkColorScheme
                else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}





