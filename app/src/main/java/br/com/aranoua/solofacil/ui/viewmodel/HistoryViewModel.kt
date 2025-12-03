package br.com.aranoua.solofacil.ui.viewmodel


import androidx.lifecycle.viewModelScope
import br.com.aranoua.solofacil.data.model.AnalysisResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.aranoua.solofacil.SoloFacilApplication

data class HistoryUiState(
    val isLoading: Boolean = false,
    val historyList: List<AnalysisResult> = emptyList()
)

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as SoloFacilApplication).repository

    // Converte o Flow do Room diretamente para o UI State
    val uiState: StateFlow<HistoryUiState> = repository.savedAnalysesFlow
        .map { historyList ->
            HistoryUiState(historyList = historyList, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HistoryUiState(isLoading = true)
        )
}