package br.com.aranoua.solofacil.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aranoua.solofacil.ui.theme.SoloFacilTheme

private data class FaqItem(val question: String, val answer: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen() {
    val faqItems = listOf(
        FaqItem(
            question = "Como conectar o sensor?", 
            answer = "É muito simples! Siga estes 3 passos:\n1. Conecte o sensor na porta USB do seu celular.\n2. Insira a ponta metálica da sonda no solo, a uma profundidade de 15 a 20 centímetros.\n3. Aguarde a confirmação no aplicativo ou a luz do sensor indicar que a conexão foi bem-sucedida para iniciar a leitura."
        ),
        FaqItem(
            question = "Qual a profundidade ideal?",
            answer = "A profundidade ideal é de 15 a 20 cm. Esta é a região onde a maioria das raízes das plantas se concentra e busca por nutrientes. Fazer a medição nesta profundidade garante que a análise reflita o solo que sua cultura realmente está utilizando. Evite medir em solo superficial muito seco ou encharcado."
        ),
        FaqItem(
            question = "Com que frequência devo analisar?",
            answer = "A frequência ideal depende do seu objetivo. Recomendamos analisar:\n- Antes de um novo plantio\n- Em fases críticas da cultura (floração, início da produção)\n- Sempre que a planta apresentar algum sinal estranho (folhas amareladas, baixo crescimento)\n- Pelo menos uma vez por ciclo produtivo"
        ),
        FaqItem(
            question = "Como interpretar os resultados?",
            answer = "O SoloFácil AM usa um sistema de cores para facilitar o entendimento:\n\n🟢 VERDE: O nível está ótimo.\n🟡 AMARELO: O nível está em um ponto de atenção.\n🔴 VERMELHO: O nível está fora do recomendado e precisa de correção.\n\nPara todos os resultados, a tela de \"Recomendações\" irá te dizer exatamente o que fazer."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Central de Ajuda") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(faqItems) { faq ->
                ExpandableFaqCard(faqItem = faq)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandableFaqCard(faqItem: FaqItem) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { isExpanded = !isExpanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faqItem.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Recolher" else "Expandir"
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = faqItem.answer, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpScreenPreview() {
    SoloFacilTheme {
        HelpScreen()
    }
}
