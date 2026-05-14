package com.example.nallanudi.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(term: Term)

    @Update
    suspend fun update(term: Term)

    @Query("SELECT * FROM terms")
    fun getAllTerms(): Flow<List<Term>>

    @Query("SELECT * FROM terms WHERE subject = :subject")
    fun getTermsBySubject(subject: String): Flow<List<Term>>

    @Query("SELECT * FROM terms WHERE isSaved = 1")
    fun getSavedTerms(): Flow<List<Term>>
}