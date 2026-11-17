package com.example.quizapp.model

data class QuizCategory(val id: Int, val name: String)
data class TriviaQuestion(val category: String, val type: String, val difficulty: String, val question: String, val correct_answer: String, val incorrect_answers: List<String>) {
    fun shuffledOptions(): List<String> = (incorrect_answers + correct_answer).shuffled()
}
data class CategoryResponse(val trivia_categories: List<QuizCategory>?)
data class QuestionResponse(val response_code: Int, val results: List<TriviaQuestion>?)
