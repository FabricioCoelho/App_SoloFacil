package br.com.aranoua.solofacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import br.com.aranoua.solofacil.ui.navigation.BottomBarScreen
import br.com.aranoua.solofacil.ui.navigation.Routes
import br.com.aranoua.solofacil.ui.navigation.bottomBarItems
import br.com.aranoua.solofacil.ui.screens.*
import br.com.aranoua.solofacil.ui.theme.SoloFacilTheme
import br.com.aranoua.solofacil.ui.viewmodel.AnalysisViewModel
import br.com.aranoua.solofacil.ui.viewmodel.HistoryDetailViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoloFacilTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            
            if (bottomBarItems.any { it.route == currentRoute }) {
                NavigationBar {
                    bottomBarItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            WelcomeScreen(onStartAnalysis = {
                navController.navigate(Routes.ANALYSIS_FLOW) {
                    popUpTo(Routes.HOME) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            })
        }
        composable(Routes.HISTORY) { 
            HistoryScreen(onAnalysisClick = {
                navController.navigate("${Routes.HISTORY_DETAIL}/$it")
            })
        }
        composable(Routes.PROFILE) { ProfileScreen() }
        composable(Routes.HELP) { HelpScreen() }

        navigation(startDestination = Routes.SENSOR_CONNECTION, route = Routes.ANALYSIS_FLOW) {
            composable(Routes.SENSOR_CONNECTION) {
                val analysisViewModel = it.sharedViewModel<AnalysisViewModel>(navController, Routes.ANALYSIS_FLOW)
                SensorConnectionScreen(analysisViewModel, navController)
            }
            composable(Routes.CULTURE_SELECTION) {
                val analysisViewModel = it.sharedViewModel<AnalysisViewModel>(navController, Routes.ANALYSIS_FLOW)
                CultureSelectionScreen(analysisViewModel, navController)
            }
            composable(Routes.ANALYSIS) {
                val analysisViewModel = it.sharedViewModel<AnalysisViewModel>(navController, Routes.ANALYSIS_FLOW)
                AnalysisScreen(analysisViewModel, navController)
            }
            composable(Routes.RESULTS) {
                val analysisViewModel = it.sharedViewModel<AnalysisViewModel>(navController, Routes.ANALYSIS_FLOW)
                ResultsScreen(analysisViewModel, navController)
            }
            composable(Routes.RECOMMENDATIONS) {
                val analysisViewModel = it.sharedViewModel<AnalysisViewModel>(navController, Routes.ANALYSIS_FLOW)
                RecommendationsScreen(
                    analysisViewModel = analysisViewModel,
                    onSaveAndGoToHistory = {
                        navController.navigate(Routes.HISTORY) {
                            // Limpe a pilha de navegação ATÉ a tela inicial (HOME)
                            popUpTo(Routes.ANALYSIS_FLOW) {
                                saveState = false
                            }
                            // Garanta que o Histórico não seja empilhado se já estiver lá
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        
        composable(route = "${Routes.HISTORY_DETAIL}/{analysisId}") { backStackEntry ->
            val analysisId = backStackEntry.arguments?.getString("analysisId")?.toIntOrNull()
            if (analysisId != null) {
                 val viewModel: HistoryDetailViewModel = viewModel()
                HistoryDetailScreen(viewModel, navController)
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    graphRoute: String
): T {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(graphRoute)
    }
    return viewModel(parentEntry)
}
