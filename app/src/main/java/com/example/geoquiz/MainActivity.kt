package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Build
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
private const val KEY_CHEAT_COUNT = "cheat_count"
private const val REQUEST_CODE_CHEAT = 0
private const val MAX_CHEAT_COUNT = 3

class MainActivity : AppCompatActivity() {
  private lateinit var trueButton: Button
  private lateinit var falseButton: Button
  private lateinit var nextButton: Button
  private lateinit var questionTextView: TextView
  private lateinit var cheatButton: Button
  private lateinit var apiTextView: TextView

  private val quizViewModel: QuizViewModel by lazy {
    ViewModelProvider(this).get(QuizViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    d(TAG, "onCreate()")
    setContentView(R.layout.activity_main)

    d(TAG, "Got a QuizViewModel: $quizViewModel")

    quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
    var cheatCount = savedInstanceState?.getInt(KEY_CHEAT_COUNT, 0) ?: 0

    trueButton        = findViewById(R.id.true_button)
    falseButton       = findViewById(R.id.false_button)
    nextButton        = findViewById(R.id.next_button)
    questionTextView  = findViewById(R.id.question_text_view)
    cheatButton       = findViewById(R.id.cheat_button)
    apiTextView       = findViewById(R.id.api_version_text_view)

    apiTextView.text = getString(R.string.api_version, Build.VERSION.SDK_INT)

    trueButton.setOnClickListener {
      checkAnswer(true)
      if (quizViewModel.isItLastQuestion) {
        Toast.makeText(this, getString(R.string.cheat_count, quizViewModel.getCheatCount()),
          Toast.LENGTH_SHORT).show()
      }
    }

    falseButton.setOnClickListener {
      checkAnswer(false)
      if (quizViewModel.isItLastQuestion) {
        Toast.makeText(this, getString(R.string.cheat_count, quizViewModel.getCheatCount()),
          Toast.LENGTH_SHORT).show()
      }
    }

    nextButton.setOnClickListener {
      if (quizViewModel.isItLastQuestion) {
        quizViewModel.clearCheatStat()
      }
      quizViewModel.moveToNext()
      updateQuestion()
    }

    cheatButton.setOnClickListener {
      val intent = CheatActivity.newIntent(this, quizViewModel.currentQuestionAnswer)
      startActivityForResult(intent, REQUEST_CODE_CHEAT)
    }

    if (cheatCount == MAX_CHEAT_COUNT) {
      cheatButton.isEnabled = false
      cheatButton.isClickable = false
    }

    updateQuestion()
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode != Activity.RESULT_OK) {
      d(TAG,"result is not ok")
      return
    }

    // ???????????????? ???? ?????????? ???????????????? ???????????????? ???????????? ???????????????? ????????????
    // ?? ???????????? ???????????? ???? CheatActivity
    if (requestCode == REQUEST_CODE_CHEAT) {
      if (data?.getBooleanExtra(ANSWER_SHOWN, false) == true) {
        quizViewModel.userIsCheatingOnThisQuestion()
        val cheatCount = quizViewModel.getCheatCount()
        if (cheatCount == MAX_CHEAT_COUNT) {
          cheatButton.isEnabled = false
          cheatButton.isClickable = false
        }
      }
    }
  }


  private fun updateQuestion () {
    questionTextView.setText(quizViewModel.currentQuestionResId)
  }

  private fun checkAnswer (answer: Boolean) {
    val correctAnswer = quizViewModel.currentQuestionAnswer

    val resId = when {
      quizViewModel.userIsCheater() -> R.string.judgement_toast
      answer == correctAnswer       -> R.string.correct_toast
      else                          -> R.string.incorrect_toast
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
    savedInstanceState.putInt(KEY_CHEAT_COUNT, quizViewModel.getCheatCount())
  }
}
