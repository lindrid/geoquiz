package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

  private lateinit var trueButton: Button
  private lateinit var falseButton: Button
  private lateinit var nextButton: Button
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
    d(TAG, "onCreate()")
    setContentView(R.layout.activity_main)

    trueButton        = findViewById(R.id.true_button)
    falseButton       = findViewById(R.id.false_button)
    nextButton        = findViewById(R.id.next_button)
    questionTextView  = findViewById(R.id.question_text_view)

    trueButton.setOnClickListener {
      checkAnswer(true)
    }

    falseButton.setOnClickListener {
      checkAnswer(false)
    }

    nextButton.setOnClickListener{
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
    }
    else {
      R.string.incorrect_toast
    }

    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
  }

  override fun onDestroy() {
    super.onDestroy()
    d(TAG, "onDestroy()")
  }

  override fun onStart() {
    super.onStart()
    d(TAG, "onStart()")
  }

  override fun onStop() {
    super.onStop()
    d(TAG, "onStop()")
  }

  override fun onResume() {
    super.onResume()
    d(TAG, "onResume()")
  }

  override fun onPause() {
    super.onPause()
    d(TAG, "onPause()")
  }
}
