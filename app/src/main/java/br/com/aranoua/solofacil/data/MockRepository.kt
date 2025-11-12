package br.com.aranoua.solofacil.data

import br.com.aranoua.solofacil.data.model.AnalysisResult
import br.com.aranoua.solofacil.data.model.Culture
import br.com.aranoua.solofacil.data.model.Recommendation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import java.util.concurrent.atomic.AtomicInteger

// Converted to a singleton object to ensure a single source of truth for the entire app.
object MockRepository {

    private val availableCultures = listOf(
        Culture("Mandioca", "🍠"),
        Culture("Milho", "🌽"),
        Culture("Soja", "🌱"),
        Culture("Café", "☕"),
        Culture("Cana", "🌿"),
        Culture("Laranja", "🍊")
    )

    private val lastId = AtomicInteger(0)
    
    // The list of saved analyses is now a StateFlow to be observed by ViewModels.
    private val _savedAnalyses = MutableStateFlow<List<AnalysisResult>>(emptyList())
    val savedAnalysesFlow: StateFlow<List<AnalysisResult>> = _savedAnalyses.asStateFlow()

    suspend fun getAvailableCultures(): List<Culture> {
        delay(500) // Simulate network delay
        return availableCultures
    }

    suspend fun getAnalysisResult(culture: Culture): AnalysisResult {
        delay(3000) // Simulate analysis time
        return when (culture.name) {
            "Mandioca" -> AnalysisResult(
                id = lastId.getAndIncrement(),
                date = Date(),
                culture = culture,
                ph = 4.5f,
                nitrogen = "Baixo",
                phosphorus = "Médio",
                potassium = "Baixo",
                moisture = 60
            )
            else -> AnalysisResult( // Default result for other cultures
                id = lastId.getAndIncrement(),
                date = Date(),
                culture = culture,
                ph = 6.2f,
                nitrogen = "Alto",
                phosphorus = "Alto",
                potassium = "Médio",
                moisture = 75
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

    // This function now returns the flow directly, no longer suspending.
    fun getHistoryFlow(): StateFlow<List<AnalysisResult>> {
        return savedAnalysesFlow
    }
    
    suspend fun getAnalysisById(id: Int): AnalysisResult? {
        delay(200)
        return _savedAnalyses.value.find { it.id == id }
    }

    // saveAnalysis now updates the flow, automatically notifying any collectors.
    fun saveAnalysis(result: AnalysisResult) {
        val currentList = _savedAnalyses.value
        _savedAnalyses.value = listOf(result) + currentList
    }
}
