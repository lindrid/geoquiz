package com.example.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
  private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans,    true),
    Question(R.string.question_mideast,   false),
    Question(R.string.question_africa,    false),
    Question(R.string.question_americas,  true),
    Question(R.string.question_asia,      true)
  )
  private val cheatArray = arrayOfNulls<Boolean>(6)

  var currentIndex = 0

  var isItLastQuestion: Boolean = false
    get() = (currentIndex == questionBank.size-1)

  val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer

  val currentQuestionResId: Int
    get() = questionBank[currentIndex].textResId

  fun moveToNext() {
    currentIndex = (currentIndex + 1) % questionBank.size
  }

  fun userIsCheatingOnThisQuestion() {
    cheatArray[currentIndex] = true
  }

  fun userIsCheater(): Boolean {
    return cheatArray[currentIndex] ?: false
  }

  fun clearCheatStat() {
    cheatArray.forEach { _ -> false }
  }

  fun getCheatCount(): Int {
    var count = 0
    cheatArray.forEach {
      if (it == true) {
        count++
      }
    }
    return count
  }
}