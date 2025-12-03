package br.com.aranoua.solofacil

import android.app.Application
import br.com.aranoua.solofacil.data.database.AppDatabase
import br.com.aranoua.solofacil.data.repository.AnalysisRepository

class SoloFacilApplication : Application() {
    // Criação do banco de dados (Lazy para otimizar a inicialização)
    private val database by lazy { AppDatabase.getDatabase(this) }

    // Criação do repositório usando o DAO do banco
    val repository by lazy { AnalysisRepository(database.analysisDao()) }
}