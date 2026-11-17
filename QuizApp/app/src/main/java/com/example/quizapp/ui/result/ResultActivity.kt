package com.example.quizapp.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.databinding.ActivityResultBinding
import com.example.quizapp.local.QuizDatabase
import com.example.quizapp.local.QuizHistoryEntity
import com.example.quizapp.ui.home.HomeActivity
import com.example.quizapp.ui.history.HistoryActivity
import com.example.quizapp.ui.quiz.QuizActivity
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var b: ActivityResultBinding
    private val categoryId by lazy { intent.getIntExtra("categoryId", 9) }; private val categoryName by lazy { intent.getStringExtra("categoryName") ?: "Quiz" }; private val difficulty by lazy { intent.getStringExtra("difficulty") ?: "easy" }; private val score by lazy { intent.getIntExtra("score", 0) }
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); b = ActivityResultBinding.inflate(layoutInflater); setContentView(b.root); b.resultCategory.text = categoryName; b.resultDifficulty.text = difficulty.replaceFirstChar { it.uppercase() }; b.resultScore.text = "$score / 10"; b.correctCount.text = "Correct: $score"; b.incorrectCount.text = "Incorrect: ${10-score}"; b.performanceMessage.text = when { score >= 8 -> "Excellent work!"; score >= 5 -> "Good effort. Keep practising!"; else -> "Keep learning and try again!" }
        lifecycleScope.launch { QuizDatabase.get(this@ResultActivity).historyDao().insert(QuizHistoryEntity(timestamp = System.currentTimeMillis(), categoryName = categoryName, categoryId = categoryId, difficulty = difficulty, score = score, totalQuestions = 10)) }
        b.playAgainButton.setOnClickListener { startActivity(Intent(this, QuizActivity::class.java).putExtra("categoryId", categoryId).putExtra("categoryName", categoryName).putExtra("difficulty", difficulty)); finish() }; b.homeButton.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)); finish() }; b.historyButton.setOnClickListener { startActivity(Intent(this, HistoryActivity::class.java)) }
    }
}
