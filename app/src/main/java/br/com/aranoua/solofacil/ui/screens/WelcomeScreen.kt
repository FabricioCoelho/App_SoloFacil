package br.com.aranoua.solofacil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aranoua.solofacil.ui.components.BotaoPrincipal
import br.com.aranoua.solofacil.ui.theme.SoloFacilTheme
import br.com.aranoua.solofacil.ui.theme.VerdePrincipal

@Composable
fun WelcomeScreen(onStartAnalysis: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Filled.Eco, 
                contentDescription = "Logo do SoloFácil AM",
                modifier = Modifier.size(120.dp),
                colorFilter = ColorFilter.tint(VerdePrincipal)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "SoloFácil AM",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            BotaoPrincipal(text = "INICIAR ANÁLISE", onClick = onStartAnalysis)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    SoloFacilTheme {
        WelcomeScreen(onStartAnalysis = {})
    }
}
