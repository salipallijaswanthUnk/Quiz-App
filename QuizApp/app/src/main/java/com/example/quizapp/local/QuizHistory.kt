package com.example.quizapp.local

import android.content.Context
import androidx.room.*

@Entity(tableName = "quiz_history")
data class QuizHistoryEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val timestamp: Long, val categoryName: String, val categoryId: Int, val difficulty: String, val score: Int, val totalQuestions: Int)

@Dao interface QuizHistoryDao {
    @Insert suspend fun insert(item: QuizHistoryEntity)
    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC") suspend fun getAll(): List<QuizHistoryEntity>
    @Query("DELETE FROM quiz_history") suspend fun clear()
}

@Database(entities = [QuizHistoryEntity::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun historyDao(): QuizHistoryDao
    companion object {
        @Volatile private var INSTANCE: QuizDatabase? = null
        fun get(context: Context): QuizDatabase = INSTANCE ?: synchronized(this) { INSTANCE ?: Room.databaseBuilder(context.applicationContext, QuizDatabase::class.java, "quiz_history.db").build().also { INSTANCE = it } }
    }
}
