package br.com.aranoua.solofacil.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SoloFacilColorScheme = lightColorScheme(
    primary = VerdePrincipal,
    secondary = AzulInfo,
    background = CinzaFundo,
    surface = CinzaFundo,
    error = VermelhoErro,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

@Composable
fun SoloFacilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SoloFacilColorScheme,
        typography = Typography,
        content = content
    )
}