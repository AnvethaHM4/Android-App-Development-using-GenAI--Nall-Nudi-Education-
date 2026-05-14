package com.example.nallanudi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terms")
data class Term(

    @PrimaryKey
    val word: String,

    val meaning: String,

    val subject: String,

    val example: String,

    val isSaved: Boolean = false
)