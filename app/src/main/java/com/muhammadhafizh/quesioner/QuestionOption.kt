package com.muhammadhafizh.quesioner

data class QuestionOption(val text: String, val points: Int)

data class Question(
    val question: String,
    val options: Map<String, QuestionOption>
)
