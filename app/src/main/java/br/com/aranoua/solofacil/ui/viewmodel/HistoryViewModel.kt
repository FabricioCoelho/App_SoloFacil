package br.com.aranoua.solofacil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aranoua.solofacil.data.MockRepository
import br.com.aranoua.solofacil.data.model.AnalysisResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HistoryUiState(
    val isLoading: Boolean = false, // O carregamento é agora implícito na emissão inicial do fluxo
    val historyList: List<AnalysisResult> = emptyList()
)

class HistoryViewModel : ViewModel() {

    // O uiState agora é um mapeamento direto do fluxo de dados do repositório.
    // Ele reage automaticamente a qualquer mudança na lista de análises salvas.
    val uiState: StateFlow<HistoryUiState> = MockRepository.savedAnalysesFlow
        .map { historyList ->
            // Para cada nova lista emitida pelo repositório, nós a colocamos no nosso UiState.
            HistoryUiState(historyList = historyList)
        }
        .stateIn(
            scope = viewModelScope,
            // Inicia o fluxo quando a UI o observa e o mantém ativo por 5s após a UI parar de observar.
            // Isso otimiza recursos.
            started = SharingStarted.WhileSubscribed(5000),
            // Valor inicial enquanto o primeiro item do fluxo não é carregado.
            initialValue = HistoryUiState(isLoading = true)
        )
}
