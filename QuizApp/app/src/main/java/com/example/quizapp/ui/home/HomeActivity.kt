package com.example.quizapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.databinding.ActivityHomeBinding
import com.example.quizapp.model.QuizCategory
import com.example.quizapp.network.TriviaApi
import com.example.quizapp.ui.login.LoginActivity
import com.example.quizapp.ui.quiz.QuizActivity
import com.example.quizapp.ui.history.HistoryActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var b: ActivityHomeBinding
    private var categories: List<QuizCategory> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); b = ActivityHomeBinding.inflate(layoutInflater); setContentView(b.root)
        b.welcomeText.text = "Welcome, ${FirebaseAuth.getInstance().currentUser?.displayName ?: "Quiz Player"}!"
        b.loadingCategories.visibility = android.view.View.VISIBLE
        lifecycleScope.launch { try { categories = TriviaApi.service.categories().trivia_categories.orEmpty(); b.categorySpinner.adapter = ArrayAdapter(this@HomeActivity, android.R.layout.simple_spinner_dropdown_item, categories.map { it.name }); if (categories.isEmpty()) Toast.makeText(this@HomeActivity, "No categories returned. Try again later.", Toast.LENGTH_LONG).show() } catch (e: Exception) { Toast.makeText(this@HomeActivity, "Could not load categories. Check your internet connection.", Toast.LENGTH_LONG).show() } finally { b.loadingCategories.visibility = android.view.View.GONE } }
        b.difficultySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOf("easy", "medium", "hard"))
        b.startQuizButton.setOnClickListener { if (categories.isEmpty()) Toast.makeText(this, "Categories are not available yet", Toast.LENGTH_SHORT).show() else startQuiz(categories[b.categorySpinner.selectedItemPosition].id, b.difficultySpinner.selectedItem.toString()) }
        b.scanButton.setOnClickListener { GmsBarcodeScanning.getClient(this).startScan().addOnSuccessListener { barcode -> parseQr(barcode.rawValue.orEmpty()) }.addOnCanceledListener { }.addOnFailureListener { Toast.makeText(this, "Scanner failed. Please try manual selection.", Toast.LENGTH_LONG).show() } }
        b.historyButton.setOnClickListener { startActivity(Intent(this, HistoryActivity::class.java)) }
        b.logoutButton.setOnClickListener { FirebaseAuth.getInstance().signOut(); GoogleSignIn.getClient(this, com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN).signOut().addOnCompleteListener { startActivity(Intent(this, LoginActivity::class.java)); finish() } }
    }
    private fun parseQr(raw: String) { val match = Regex("""^category=(\d+);difficulty=(easy|medium|hard)$""", RegexOption.IGNORE_CASE).matchEntire(raw.trim()); if (match == null) { Toast.makeText(this, "Invalid QR. Expected category=9;difficulty=easy", Toast.LENGTH_LONG).show(); return }; val id = match.groupValues[1].toIntOrNull(); val diff = match.groupValues[2].lowercase(); if (id == null || id !in 9..32 || categories.none { it.id == id }) { Toast.makeText(this, "Unknown quiz category in QR code", Toast.LENGTH_LONG).show(); return }; startQuiz(id, diff) }
    private fun startQuiz(id: Int, difficulty: String) { startActivity(Intent(this, QuizActivity::class.java).putExtra("categoryId", id).putExtra("categoryName", categories.firstOrNull { it.id == id }?.name ?: "Quiz").putExtra("difficulty", difficulty)) }
}
