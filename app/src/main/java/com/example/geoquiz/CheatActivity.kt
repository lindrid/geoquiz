package com.example.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
  private var answerIsTrue = false
  private lateinit var answerButton: Button
  private lateinit var answerTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cheat)

    answerIsTrue = intent.getBooleanExtra(ANSWER_IS_TRUE, false)
    answerButton    = findViewById(R.id.answer_button)
    answerTextView  = findViewById(R.id.answer_text_view)

    answerButton.setOnClickListener {
      answerTextView.setText(when(answerIsTrue) {
        true -> R.string.true_button
        else -> R.string.false_button
      })
    }
  }

  companion object {
    fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
      return Intent(packageContext, CheatActivity::class.java).apply {
        putExtra(ANSWER_IS_TRUE, answerIsTrue)
      }
    }
  }
}
