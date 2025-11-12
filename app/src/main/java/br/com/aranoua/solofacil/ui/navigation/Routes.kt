package br.com.aranoua.solofacil.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val HOME = "home"
    const val HISTORY = "history"
    const val PROFILE = "profile"
    const val HELP = "help"
    
    const val ANALYSIS_FLOW = "analysis_flow"
    const val SENSOR_CONNECTION = "sensor_connection"
    const val CULTURE_SELECTION = "culture_selection"
    const val ANALYSIS = "analysis"
    const val RESULTS = "results"
    const val RECOMMENDATIONS = "recommendations"
    
    const val HISTORY_DETAIL = "history_detail"
}

sealed class BottomBarScreen(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomBarScreen(Routes.HOME, "Início", Icons.Default.Home)
    object History : BottomBarScreen(Routes.HISTORY, "Histórico", Icons.Default.History)
    object Profile : BottomBarScreen(Routes.PROFILE, "Perfil", Icons.Default.AccountCircle)
    object Help : BottomBarScreen(Routes.HELP, "Ajuda", Icons.Default.Help)
}

val bottomBarItems = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.History,
    BottomBarScreen.Profile,
    BottomBarScreen.Help
)
