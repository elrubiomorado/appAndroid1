package avila.gonzalez.com.example.quiz

import androidx.annotation.StringRes

data class Question (@StringRes val textResId: Int, val answer: Boolean, var enabled: Int)