package br.com.aranoua.solofacil.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.aranoua.solofacil.data.model.AnalysisResult
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AnalysisResult)

    @Query("SELECT * FROM analysis_history WHERE id = :id")
    suspend fun getAnalysisById(id: Int): AnalysisResult?

    @Query("SELECT * FROM analysis_history ORDER BY date DESC")
    fun getAllAnalyses(): Flow<List<AnalysisResult>>
}