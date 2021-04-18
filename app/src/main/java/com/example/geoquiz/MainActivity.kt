package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
  private lateinit var trueButton: Button
  private lateinit var falseButton: Button
  private lateinit var nextButton: Button
  private lateinit var questionTextView: TextView
  private lateinit var cheatButton: Button

  private val quizViewModel: QuizViewModel by lazy {
    ViewModelProvider(this).get(QuizViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    d(TAG, "onCreate()")
    setContentView(R.layout.activity_main)

    d(TAG, "Got a QuizViewModel: $quizViewModel")

    quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

    trueButton        = findViewById(R.id.true_button)
    falseButton       = findViewById(R.id.false_button)
    nextButton        = findViewById(R.id.next_button)
    questionTextView  = findViewById(R.id.question_text_view)
    cheatButton       = findViewById(R.id.cheat_button)

    trueButton.setOnClickListener {
      checkAnswer(true)
    }

    falseButton.setOnClickListener {
      checkAnswer(false)
    }

    nextButton.setOnClickListener {
      quizViewModel.moveToNext()
      updateQuestion()
    }

    cheatButton.setOnClickListener {
      val intent = CheatActivity.newIntent(this, quizViewModel.currentQuestionAnswer)
      startActivity(intent)
    }

    updateQuestion()
  }

  private fun updateQuestion () {
    questionTextView.setText(quizViewModel.currentQuestionResId)
  }

  private fun checkAnswer (answer: Boolean) {
    val correctAnswer = quizViewModel.currentQuestionAnswer

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

  override fun onSaveInstanceState(savedInstanceState: Bundle) {
    super.onSaveInstanceState(savedInstanceState)
    Log.i(TAG, "onSaveInstanceState")
    savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
  }
}
