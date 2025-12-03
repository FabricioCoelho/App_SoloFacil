package br.com.aranoua.solofacil.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.aranoua.solofacil.SoloFacilApplication
import br.com.aranoua.solofacil.data.model.AnalysisResult
import br.com.aranoua.solofacil.data.model.Recommendation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HistoryDetailUiState(
    val isLoading: Boolean = true,
    val analysisResult: AnalysisResult? = null,
    val recommendations: List<Recommendation>? = null
)

class HistoryDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = (application as SoloFacilApplication).repository
    private val analysisId: Int = savedStateHandle.get<String>("analysisId")?.toIntOrNull() ?: -1

    private val _uiState = MutableStateFlow(HistoryDetailUiState())
    val uiState: StateFlow<HistoryDetailUiState> = _uiState.asStateFlow()

    init {
        if (analysisId != -1) {
            loadAnalysisDetails()
        }
    }

    private fun loadAnalysisDetails() {
        // Dispatchers.IO garante que o acesso ao banco ocorra em background
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAnalysisById(analysisId)
            if (result != null) {
                val recommendations = repository.getRecommendations(result)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        analysisResult = result,
                        recommendations = recommendations
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}