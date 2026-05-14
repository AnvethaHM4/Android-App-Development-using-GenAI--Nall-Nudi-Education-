package com.example.nallanudi.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Term::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun termDao(): TermDao
}