package com.example.people.theme

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.people.ui.viewmodel.SettingsViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFC490DE),
    secondary = Color(0xFFF1F2F6),
    tertiary = Color(0xFFC490DE),
    surface = Color(0xFF000000),
    onSurface = Color(0xFFFFFFFF),
    background = Color(0xFF1D1C1E)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFC490DE),
    secondary = Color(0xFFF1F2F6),
    tertiary = Color(0xFFC490DE),
    surface = Color(0xFFF1F2F6),
    onSurface = Color(0xFF000000),
    background = Color(0xFFFFFFFF)
)

@Composable
fun PeopleTheme(
    settings: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val colorScheme = when(settings.dark.value) {
        true -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = settings.dark.value
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(10.dp),
            large = RoundedCornerShape(30.dp),
        ),
        content = content
    )
}