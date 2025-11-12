package br.com.aranoua.solofacil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.aranoua.solofacil.data.model.AnalysisResult
import br.com.aranoua.solofacil.data.model.Recommendation
import br.com.aranoua.solofacil.ui.theme.AmareloAtencao
import br.com.aranoua.solofacil.ui.theme.VerdeSucesso
import br.com.aranoua.solofacil.ui.theme.VermelhoErro

@Composable
fun ResultRow(parametro: String, valor: String, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$parametro:", style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = valor, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription = "Status",
                tint = statusColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun RecommendationCard(recomendacao: Recommendation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recomendacao.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = recomendacao.description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

fun getStatusColorForNutrient(level: String): Color {
    return when (level) {
        "Baixo" -> VermelhoErro
        "Médio" -> AmareloAtencao
        "Alto" -> VerdeSucesso
        else -> Color.Gray
    }
}

fun getStatusColor(value: Float, optimalMin: Float, optimalMax: Float): Color {
    return when {
        value < optimalMin -> VermelhoErro
        value > optimalMax -> AmareloAtencao
        else -> VerdeSucesso
    }
}

fun formatReport(result: AnalysisResult?, recommendations: List<Recommendation>?): String {
    if (result == null) return "Nenhuma análise para compartilhar."
    
    val recommendationsText = recommendations?.joinToString("\n") { "- ${it.title}: ${it.description}" } 
        ?: "Nenhuma recomendação disponível."

    return """
    *Relatório de Análise - SoloFácil AM*
    -------------------------------------
    *Cultura:* ${result.culture.name}
    *pH:* ${result.ph}
    *Nitrogênio:* ${result.nitrogen}
    *Fósforo:* ${result.phosphorus}
    *Potássio:* ${result.potassium}
    *Umidade:* ${result.moisture}%

    *Recomendações:*
    $recommendationsText
    """.trimIndent()
}
