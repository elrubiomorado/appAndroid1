package avila.gonzalez.com.example.quiz

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import avila.gonzalez.com.example.quiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->

    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener{ view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener{view: View->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener{view: View ->
            quizViewModel.moveToNext()
            updateNextQuestion()
            enabled()
        }

        binding.prevButton.setOnClickListener{view: View ->
            quizViewModel.moveToPrev()
            updatePrevQuestion()
            enabled()
        }

        binding.cheatButton.setOnClickListener{view: View ->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }


    }

    private fun updateNextQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }
    private fun updatePrevQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) R.string.correct_text else R.string.incorrect_text
        val colorRes = if (userAnswer == correctAnswer) R.color.green else R.color.red
        quizViewModel.currentStatus = 0
        enabled()
        Snackbar.make(findViewById(R.id.true_button),messageResId,Snackbar.LENGTH_SHORT).
                setBackgroundTint(resources.getColor(colorRes)).show()

    }


     private fun enabled(){
        if(quizViewModel.currentStatus == 1) {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            binding.cheatButton.isEnabled = true

        }else{
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
            binding.cheatButton.isEnabled = false

        }

        if(quizViewModel.currentIndex == 0)
            binding.prevButton.isEnabled = false else binding.prevButton.isEnabled = true


        if(quizViewModel.currentIndex == quizViewModel.getCurrentSize - 1)
            binding.nextButton.isEnabled = false else binding.nextButton.isEnabled = true

    }

}

