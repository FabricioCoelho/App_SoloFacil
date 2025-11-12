package br.com.aranoua.solofacil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.aranoua.solofacil.ui.navigation.Routes
import br.com.aranoua.solofacil.ui.theme.SoloFacilTheme
import br.com.aranoua.solofacil.ui.theme.VerdePrincipal
import br.com.aranoua.solofacil.ui.viewmodel.AnalysisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    analysisViewModel: AnalysisViewModel,
    navController: NavController
) {
    val uiState by analysisViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        analysisViewModel.onStartAnalysis()
    }

    LaunchedEffect(uiState.navigateToResults) {
        if (uiState.navigateToResults) {
            navController.navigate(Routes.RESULTS) {
                popUpTo(Routes.ANALYSIS) { inclusive = true }
            }
            analysisViewModel.onNavigatedToResults() // Reset the event
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Etapa 3 de 5") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isAnalyzing) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(150.dp),
                        strokeWidth = 8.dp,
                        color = VerdePrincipal
                    )
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ícone de Sonda",
                        modifier = Modifier.size(60.dp),
                        tint = VerdePrincipal
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Analisando amostra...",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mantenha o sensor estabilizado",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(text = "Análise concluída!", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnalysisScreenPreview() {
    SoloFacilTheme {
        val navController = rememberNavController()
        AnalysisScreen(viewModel(), navController)
    }
}
