package br.com.aranoua.solofacil.data.model

import java.util.Date

//armazenar a informação da cultura agricola escolhida
data class Culture(
    val name: String,
    val icon: String
)
//armazenar os resultados do teste de solo
data class AnalysisResult(
    val id: Int,
    val date: Date,
    val culture: Culture,
    val ph: Float,
    val nitrogen: String,
    val phosphorus: String,
    val potassium: String,
    val moisture: Int
)
//armazenar as recomendacoes para melhorar o solo se necessário
data class Recommendation(
    val title: String,
    val description: String
)
