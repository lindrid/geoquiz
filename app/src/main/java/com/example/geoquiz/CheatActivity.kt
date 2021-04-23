package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
private const val CORRECT_ANSWER = "com.example.geoquiz.correct_answer"

class CheatActivity : AppCompatActivity() {
  private var correctAnswer = false
  private lateinit var answerButton: Button
  private lateinit var answerTextView: TextView
  private var answerWasShown = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cheat)

    correctAnswer   = intent.getBooleanExtra(CORRECT_ANSWER, false)
    answerButton    = findViewById(R.id.answer_button)
    answerTextView  = findViewById(R.id.answer_text_view)

    answerWasShown = savedInstanceState?.getBoolean(ANSWER_SHOWN, false) ?: false
    if (answerWasShown) {
      setAnswerShownToTrue()
    }

    answerButton.setOnClickListener {
      answerTextView.setText(when(correctAnswer) {
        true -> R.string.true_button
        else -> R.string.false_button
      })
      setAnswerShownToTrue()
    }
  }

  private fun setAnswerShownToTrue () {
    val data = Intent().apply {
      putExtra(ANSWER_SHOWN, true)
    }
    setResult(Activity.RESULT_OK, data)
    answerWasShown = true
  }

  companion object {
    fun newIntent(packageContext: Context, correctAnswer: Boolean): Intent {
      return Intent(packageContext, CheatActivity::class.java).apply {
        putExtra(CORRECT_ANSWER, correctAnswer)
      }
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putBoolean(ANSWER_SHOWN, answerWasShown)
  }
}
