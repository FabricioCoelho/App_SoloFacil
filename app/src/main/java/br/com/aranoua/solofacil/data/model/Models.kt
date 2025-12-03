package br.com.aranoua.solofacil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


//armazenar a informação da cultura agricola escolhida
data class Culture(
    val name: String,
    val icon: String
)
//armazenar os resultados do teste de solo
@Entity(tableName = "analysis_history")
data class AnalysisResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Date,
    val culture: Culture,
    val ph: Float,
    val nitrogen: String,
    val phosphorus: String,
    val potassium: String,
    val moisture: Float
)

//armazenar as recomendacoes para melhorar o solo se necessário
data class Recommendation(
    val title: String,
    val description: String
)
