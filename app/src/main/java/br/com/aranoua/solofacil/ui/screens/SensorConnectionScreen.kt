package br.com.aranoua.solofacil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.aranoua.solofacil.ui.components.BotaoPrincipal
import br.com.aranoua.solofacil.ui.navigation.Routes
import br.com.aranoua.solofacil.ui.theme.SoloFacilTheme
import br.com.aranoua.solofacil.ui.theme.VerdePrincipal
import br.com.aranoua.solofacil.ui.viewmodel.AnalysisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorConnectionScreen(
    analysisViewModel: AnalysisViewModel,
    navController: NavController
) {
    val uiState by analysisViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToCultureSelection) {
        if (uiState.navigateToCultureSelection) {
            navController.navigate(Routes.CULTURE_SELECTION)
            analysisViewModel.onNavigatedToCultureSelection() // Reset the event
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Etapa 1 de 5") },
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Default.Usb,
                contentDescription = "Ícone de USB",
                modifier = Modifier.size(80.dp),
                colorFilter = ColorFilter.tint(VerdePrincipal)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Conecte o sensor ao smartphone",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                imageVector = Icons.Default.Nature, // Placeholder for the diagram
                contentDescription = "Diagrama da sonda no solo",
                modifier = Modifier.size(150.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.height(32.dp))
            BotaoPrincipal(
                text = "SENSOR CONECTADO →", 
                onClick = { analysisViewModel.onSensorConnected() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SensorConnectionScreenPreview() {
    SoloFacilTheme {
        val navController = rememberNavController()
        SensorConnectionScreen(viewModel(), navController)
    }
}
