package br.com.aranoua.solofacil.data.repository

import br.com.aranoua.solofacil.data.database.AnalysisDao
import br.com.aranoua.solofacil.data.model.AnalysisResult
import br.com.aranoua.solofacil.data.model.Culture
import br.com.aranoua.solofacil.data.model.Recommendation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.util.Date

class AnalysisRepository(private val analysisDao: AnalysisDao) {

    // BANCO DE DADOS (ROOM)
    val savedAnalysesFlow: Flow<List<AnalysisResult>> = analysisDao.getAllAnalyses()

    suspend fun saveAnalysis(result: AnalysisResult) {
        analysisDao.insertAnalysis(result)
    }

    suspend fun getAnalysisById(id: Int): AnalysisResult? {
        return analysisDao.getAnalysisById(id)
    }

    suspend fun getAvailableCultures(): List<Culture> {
        delay(500)
        return listOf(
            Culture("Mandioca", "🍠"),
            Culture("Milho", "🌽"),
            Culture("Soja", "🌱"),
            Culture("Café", "☕"),
            Culture("Cana", "🌿"),
            Culture("Laranja", "🍊")
        )
    }

    suspend fun getAnalysisResult(culture: Culture): AnalysisResult {
        delay(3000)
        return when (culture.name) {
            "Mandioca" -> AnalysisResult(
                id = 0,
                date = Date(),
                culture = culture,
                ph = 4.5f,
                nitrogen = "Baixo",
                phosphorus = "Médio",
                potassium = "Baixo",
                moisture = 60f
            )
            else -> AnalysisResult(
                id = 0,
                date = Date(),
                culture = culture,
                ph = 6.2f,
                nitrogen = "Alto",
                phosphorus = "Alto",
                potassium = "Médio",
                moisture = 75f
            )
        }
    }

    fun getRecommendations(result: AnalysisResult): List<Recommendation> {
        val recommendations = mutableListOf<Recommendation>()

        if (result.ph < 5.0f) {
            recommendations.add(Recommendation("Correção de pH", "Aplicar 2,5 t/ha de calcário dolomítico para elevar o pH para 6.0."))
        }
        if (result.nitrogen == "Baixo") {
            recommendations.add(Recommendation("Adubação Nitrogenada", "Aplicar 90 kg/ha de Nitrogênio na forma de ureia, parcelado em duas vezes."))
        }
        if (result.phosphorus == "Baixo") {
            recommendations.add(Recommendation("Adubação Fosfatada", "Aplicar 120 kg/ha de P2O5 na forma de superfosfato simples no sulco de plantio."))
        }
        if (result.potassium == "Baixo") {
            recommendations.add(Recommendation("Adubação Potássica", "Aplicar 80 kg/ha de K2O na forma de cloreto de potássio."))
        }

        if(recommendations.isEmpty()){
            recommendations.add(Recommendation("Solo Fértil", "Nenhuma correção é necessária no momento. O solo apresenta boas condições para a cultura selecionada."))
        }
        return recommendations
    }
}