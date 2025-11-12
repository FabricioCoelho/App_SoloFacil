package br.com.aranoua.solofacil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aranoua.solofacil.data.MockRepository
import br.com.aranoua.solofacil.data.model.AnalysisResult
import br.com.aranoua.solofacil.data.model.Culture
import br.com.aranoua.solofacil.data.model.Recommendation
import br.com.aranoua.solofacil.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AnalysisUiState(
    val isSensorConnected: Boolean = false,
    val navigateToCultureSelection: Boolean = false,
    val isLoadingCultures: Boolean = true,
    val availableCultures: List<Culture> = emptyList(),
    val selectedCulture: Culture? = null,
    val isAnalyzing: Boolean = false,
    val analysisResult: AnalysisResult? = null,
    val navigateToResults: Boolean = false, 
    val recommendations: List<Recommendation>? = null
)

class AnalysisViewModel : ViewModel() {

    private val repository = MockRepository

    private val _uiState = MutableStateFlow(AnalysisUiState())
    val uiState: StateFlow<AnalysisUiState> = _uiState.asStateFlow()
    
    private val _navigationEvents = MutableSharedFlow<String>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        loadCultures()
    }

    private fun loadCultures() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCultures = true) }
            val cultures = repository.getAvailableCultures()
            _uiState.update { it.copy(availableCultures = cultures, isLoadingCultures = false) }
        }
    }

    fun onSensorConnected() {
        _uiState.update { it.copy(isSensorConnected = true, navigateToCultureSelection = true) }
    }

    fun onNavigatedToCultureSelection() {
        _uiState.update { it.copy(navigateToCultureSelection = false) }
    }

    fun onCultureSelected(culture: Culture) {
        _uiState.update { it.copy(selectedCulture = culture) }
    }

    fun onStartAnalysis() {
        _uiState.value.selectedCulture?.let { culture ->
            viewModelScope.launch {
                _uiState.update { it.copy(isAnalyzing = true, analysisResult = null) }
                val result = repository.getAnalysisResult(culture)
                _uiState.update { it.copy(analysisResult = result, isAnalyzing = false, navigateToResults = true) }
            }
        }
    }

    fun onNavigatedToResults() {
        _uiState.update { it.copy(navigateToResults = false) }
    }

    fun onViewRecommendations() {
        _uiState.value.analysisResult?.let { result ->
            val recommendations = repository.getRecommendations(result)
            _uiState.update { it.copy(recommendations = recommendations) }
        }
    }
    
    fun onSaveReport() {
        viewModelScope.launch {
            _uiState.value.analysisResult?.let {
                repository.saveAnalysis(it)
                _navigationEvents.emit(Routes.HISTORY)
            }
        }
    }
}
