package br.com.aranoua.solofacil.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.aranoua.solofacil.ui.components.BotaoPrincipal
import br.com.aranoua.solofacil.ui.components.RecommendationCard
import br.com.aranoua.solofacil.ui.components.formatReport
import br.com.aranoua.solofacil.ui.navigation.Routes
import br.com.aranoua.solofacil.ui.theme.SoloFacilTheme
import br.com.aranoua.solofacil.ui.viewmodel.AnalysisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(
    analysisViewModel: AnalysisViewModel,
    onSaveAndGoToHistory: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by analysisViewModel.uiState.collectAsStateWithLifecycle()
    val recommendations = uiState.recommendations
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Etapa 5 de 5") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
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
                .padding(16.dp)
        ) {
            Text(
                text = "Recomendações Detalhadas",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (recommendations != null) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(recommendations) { recomendacao ->
                        RecommendationCard(recomendacao = recomendacao)
                    }
                }
            } else {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("Nenhuma recomendação gerada.")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { 
                        val report = formatReport(uiState.analysisResult, uiState.recommendations)
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, report)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        ContextCompat.startActivity(context, shareIntent, null)
                    },
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Text("COMPARTILHAR")
                }
                Spacer(modifier = Modifier.width(16.dp))
                BotaoPrincipal(
                    text = "SALVAR E IR PARA O HISTÓRICO",
                    onClick = {
                        analysisViewModel.onSaveReport()
                        Toast.makeText(context, "Relatório Salvo!", Toast.LENGTH_SHORT).show()
                        onSaveAndGoToHistory()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationsScreenPreview() {
    SoloFacilTheme {
        RecommendationsScreen(
            analysisViewModel = viewModel(),
            onSaveAndGoToHistory = {},
            onNavigateBack = {}
        )
    }
}
