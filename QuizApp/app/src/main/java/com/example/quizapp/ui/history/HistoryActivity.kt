package com.example.quizapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ActivityHistoryBinding
import com.example.quizapp.local.QuizDatabase
import com.example.quizapp.local.QuizHistoryEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var b: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); b = ActivityHistoryBinding.inflate(layoutInflater); setContentView(b.root); b.historyRecycler.layoutManager = LinearLayoutManager(this); b.backButton.setOnClickListener { finish() }; b.clearHistoryButton.setOnClickListener { AlertDialog.Builder(this).setTitle("Clear history?").setMessage("This permanently deletes all saved quiz attempts on this device.").setNegativeButton("Cancel", null).setPositiveButton("Clear") { _, _ -> lifecycleScope.launch { QuizDatabase.get(this@HistoryActivity).historyDao().clear(); load() } }.show() }; load() }
    override fun onResume() { super.onResume(); if (::b.isInitialized) load() }
    private fun load() { lifecycleScope.launch { val items = QuizDatabase.get(this@HistoryActivity).historyDao().getAll(); b.emptyHistory.text = if (items.isEmpty()) "No quiz attempts yet." else ""; b.historyRecycler.adapter = HistoryAdapter(items) } }
    private class HistoryAdapter(private val items: List<QuizHistoryEntity>) : RecyclerView.Adapter<HistoryAdapter.Holder>() {
        class Holder(val text: TextView) : RecyclerView.ViewHolder(text)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { val t = TextView(parent.context); t.setPadding(24, 22, 24, 22); t.textSize = 16f; return Holder(t) }
        override fun getItemCount() = items.size
        override fun onBindViewHolder(holder: Holder, position: Int) { val item = items[position]; val date = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(item.timestamp)); holder.text.text = "$date\n${item.categoryName} • ${item.difficulty.replaceFirstChar { it.uppercase() }}\nScore: ${item.score}/${item.totalQuestions}" }
    }
}
