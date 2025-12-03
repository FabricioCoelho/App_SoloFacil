package br.com.aranoua.solofacil.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.aranoua.solofacil.data.model.AnalysisResult
import br.com.aranoua.solofacil.data.model.Recommendation
import br.com.aranoua.solofacil.ui.components.RecommendationCard
import br.com.aranoua.solofacil.ui.components.ResultRow
import br.com.aranoua.solofacil.ui.components.formatReport
import br.com.aranoua.solofacil.ui.components.getStatusColor
import br.com.aranoua.solofacil.ui.components.getStatusColorForNutrient
import br.com.aranoua.solofacil.ui.theme.*
import br.com.aranoua.solofacil.ui.viewmodel.HistoryDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    viewModel: HistoryDetailViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Análise") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val report = formatReport(uiState.analysisResult, uiState.recommendations)
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, report)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        ContextCompat.startActivity(context, shareIntent, null)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartilhar")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.analysisResult != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item { ResultsSection(uiState.analysisResult!!) }
                item { RecommendationsSection(uiState.recommendations) }
            }
        } else {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Análise não encontrada.")
            }
        }
    }
}

@Composable
private fun ResultsSection(result: AnalysisResult) {
    Column {
        Text("Resultados", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
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
}

@Composable
private fun RecommendationsSection(recommendations: List<Recommendation>?) {
    Column {
        Text("Recomendações", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        if (!recommendations.isNullOrEmpty()) {
            recommendations.forEach {
                RecommendationCard(recomendacao = it)
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            Text("Nenhuma recomendação para esta análise.")
        }
    }
}
