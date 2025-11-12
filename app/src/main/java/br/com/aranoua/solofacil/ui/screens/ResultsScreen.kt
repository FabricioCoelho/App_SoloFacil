package br.com.aranoua.solofacil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.aranoua.solofacil.ui.components.BotaoPrincipal
import br.com.aranoua.solofacil.ui.components.ResultRow
import br.com.aranoua.solofacil.ui.components.getStatusColor
import br.com.aranoua.solofacil.ui.components.getStatusColorForNutrient
import br.com.aranoua.solofacil.ui.navigation.Routes
import br.com.aranoua.solofacil.ui.theme.*
import br.com.aranoua.solofacil.ui.viewmodel.AnalysisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    analysisViewModel: AnalysisViewModel,
    navController: NavController
) {
    val uiState by analysisViewModel.uiState.collectAsStateWithLifecycle()
    val result = uiState.analysisResult

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Etapa 4 de 5") },
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
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Resultados da Análise", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))
            if (result != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ResultRow("Cultura", result.culture.name, VerdeSucesso)
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ResultRow("pH", result.ph.toString(), getStatusColor(result.ph, 5.5f, 6.5f))
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ResultRow("Nitrogênio", result.nitrogen, getStatusColorForNutrient(result.nitrogen))
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ResultRow("Fósforo", result.phosphorus, getStatusColorForNutrient(result.phosphorus))
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ResultRow("Potássio", result.potassium, getStatusColorForNutrient(result.potassium))
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ResultRow("Umidade", "${result.moisture}%", getStatusColor(result.moisture.toFloat(), 60f, 80f))
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            BotaoPrincipal(text = "VER RECOMENDAÇÕES", onClick = {
                analysisViewModel.onViewRecommendations()
                navController.navigate(Routes.RECOMMENDATIONS)
            })
             Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    SoloFacilTheme {
        val navController = rememberNavController()
        ResultsScreen(viewModel(), navController)
    }
}
