package com.example.quizapp.ui.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.model.TriviaQuestion
import com.example.quizapp.network.TriviaApi
import com.example.quizapp.ui.result.ResultActivity
import com.example.quizapp.utils.decodeHtml
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {
    private lateinit var b: ActivityQuizBinding
    private var questions: List<TriviaQuestion> = emptyList(); private var index = 0; private var score = 0; private var selected = false; private var options = emptyList<String>()
    private val buttons by lazy { listOf(b.option1, b.option2, b.option3, b.option4) }
    private val categoryId by lazy { intent.getIntExtra("categoryId", 9) }; private val categoryName by lazy { intent.getStringExtra("categoryName") ?: "Quiz" }; private val difficulty by lazy { intent.getStringExtra("difficulty") ?: "easy" }
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); b = ActivityQuizBinding.inflate(layoutInflater); setContentView(b.root); b.quizLoading.visibility = View.VISIBLE; b.quizContent.visibility = View.GONE
        lifecycleScope.launch { try { val response = TriviaApi.service.questions(category = categoryId, difficulty = difficulty); if (response.response_code != 0 || response.results.orEmpty().size != 10) { Toast.makeText(this@QuizActivity, "Could not get 10 questions for this category and difficulty. Try another selection.", Toast.LENGTH_LONG).show(); finish(); return@launch }; questions = response.results!!; b.quizLoading.visibility = View.GONE; b.quizContent.visibility = View.VISIBLE; render() } catch (e: Exception) { Toast.makeText(this@QuizActivity, "Network error while loading questions.", Toast.LENGTH_LONG).show(); finish() } }
    }
    private fun render() { selected = false; val q = questions[index]; options = q.shuffledOptions(); b.questionCounter.text = "Question ${index + 1} of 10"; b.quizProgress.progress = index + 1; b.questionText.text = decodeHtml(q.question); b.nextButton.visibility = View.GONE; buttons.forEachIndexed { i, button -> button.isEnabled = true; button.text = decodeHtml(options[i]); button.setBackgroundColor(Color.LTGRAY); button.setOnClickListener { choose(button, options[i], q.correct_answer) } }; b.nextButton.text = if (index == 9) "View Result" else "Next"; b.nextButton.setOnClickListener { if (index < 9) { index++; render() } else openResult() } }
    private fun choose(chosen: Button, answer: String, correct: String) { if (selected) return; selected = true; buttons.forEach { it.isEnabled = false; if (it.text.toString() == decodeHtml(correct)) it.setBackgroundColor(Color.rgb(46,157,98)) }; if (answer == correct) score++ else chosen.setBackgroundColor(Color.rgb(217,67,67)); b.nextButton.visibility = View.VISIBLE }
    private fun openResult() { startActivity(Intent(this, ResultActivity::class.java).putExtra("categoryId", categoryId).putExtra("categoryName", categoryName).putExtra("difficulty", difficulty).putExtra("score", score)); finish() }
}
