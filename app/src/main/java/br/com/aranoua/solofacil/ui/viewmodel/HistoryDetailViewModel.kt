package br.com.aranoua.solofacil.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aranoua.solofacil.data.MockRepository
import br.com.aranoua.solofacil.data.model.AnalysisResult
import br.com.aranoua.solofacil.data.model.Recommendation
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val analysisId: Int = savedStateHandle.get<String>("analysisId")?.toIntOrNull() ?: -1

    private val _uiState = MutableStateFlow(HistoryDetailUiState())
    val uiState: StateFlow<HistoryDetailUiState> = _uiState.asStateFlow()

    init {
        if (analysisId != -1) {
            loadAnalysisDetails()
        }
    }

    private fun loadAnalysisDetails() {
        viewModelScope.launch {
            val result = MockRepository.getAnalysisById(analysisId)
            if (result != null) {
                val recommendations = MockRepository.getRecommendations(result)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        analysisResult = result,
                        recommendations = recommendations
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) } // Handle error case
            }
        }
    }
}
