package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import kotlin.math.abs

class MainActivity : AppCompatActivity() {

  private lateinit var trueButton: Button
  private lateinit var falseButton: Button
  private lateinit var nextButton: ImageButton
  private lateinit var prevButton: ImageButton
  private lateinit var questionTextView: TextView

  private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans,    true),
    Question(R.string.question_mideast,   false),
    Question(R.string.question_africa,    false),
    Question(R.string.question_americas,  true),
    Question(R.string.question_asia,      true)
  )
  private var currentIndex = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    trueButton        = findViewById(R.id.true_button)
    falseButton       = findViewById(R.id.false_button)
    nextButton        = findViewById(R.id.next_button)
    prevButton        = findViewById(R.id.prev_button)
    questionTextView  = findViewById(R.id.text_view)

    trueButton.setOnClickListener {
      checkAnswer(true)
    }

    falseButton.setOnClickListener {
      checkAnswer(false)
    }

    prevButton.setOnClickListener {
      currentIndex = if (currentIndex == 0) {
        questionBank.size - 1
      } else {
        ((currentIndex - 1)) % questionBank.size
      }
      updateQuestion()
    }

    nextButton.setOnClickListener {
      currentIndex = (currentIndex + 1) % questionBank.size
      updateQuestion()
    }

    questionTextView.setOnClickListener {
      currentIndex = (currentIndex + 1) % questionBank.size
      updateQuestion()
    }

    updateQuestion()
  }

  private fun updateQuestion () {
    questionTextView.setText(questionBank[currentIndex].textResId)
  }

  private fun checkAnswer (answer: Boolean) {
    val correctAnswer = questionBank[currentIndex].answer

    val resId = if (answer == correctAnswer) {
      R.string.correct_toast
    } else {
      R.string.incorrect_toast
    }

    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
  }
}
