package com.muhammadhafizh.quesioner
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class StressQuiz : AppCompatActivity() {
    private lateinit var quiztext: TextView
    private lateinit var aans: TextView
    private lateinit var bans: TextView
    private lateinit var cans: TextView
    private lateinit var dans: TextView
    private lateinit var questionsItems: List<Question>
    private var currentQuestions = 0
    private var totalPoints = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        quiztext = findViewById(R.id.quizText)
        aans = findViewById(R.id.aanswer)
        bans = findViewById(R.id.banswer)
        cans = findViewById(R.id.canswer)
        dans = findViewById(R.id.danswer)

        loadAllQuestions()
        questionsItems.shuffled()
        setQuestionScreen(currentQuestions)

        aans.setOnClickListener {
            processAnswer(aans, "0")
        }

        bans.setOnClickListener {
            processAnswer(bans, "1")
        }

        cans.setOnClickListener {
            processAnswer(cans, "2")
        }

        dans.setOnClickListener {
            processAnswer(dans, "3")
        }
    }

    private fun processAnswer(answerView: TextView, selectedAnswer: String) {
        val points = questionsItems[currentQuestions].options[selectedAnswer]?.points ?: 0
        totalPoints += points
        setAnswerStatus(answerView, R.color.green)

        if (currentQuestions < questionsItems.size - 1) {
            Handler().postDelayed({
                currentQuestions++
                setQuestionScreen(currentQuestions)
                resetAnswerStatus(answerView)
            }, 500)
        } else {
            val intent = Intent(this@StressQuiz, ResultActivity::class.java)
            intent.putExtra("points", totalPoints)
            startActivity(intent)
            finish()
        }
    }

    private fun setQuestionScreen(currentQuestions: Int) {
        quiztext.text = questionsItems[currentQuestions].question
        aans.text = questionsItems[currentQuestions].options["0"]?.text
        bans.text = questionsItems[currentQuestions].options["1"]?.text
        cans.text = questionsItems[currentQuestions].options["2"]?.text
        dans.text = questionsItems[currentQuestions].options["3"]?.text
    }

    private fun loadAllQuestions() {
        questionsItems = ArrayList()
        val jsonquiz = loadJsonFromAsset("stressquestions.json")
        try {
            val jsonObject = JSONObject(jsonquiz)
            val questions = jsonObject.getJSONArray("stressquestions")
            for (i in 0 until questions.length()) {
                val question = questions.getJSONObject(i)

                val questionText = question.getString("question")
                val optionsObject = question.getJSONObject("options")
                val options = mutableMapOf<String, QuestionOption>()

                for (key in optionsObject.keys()) {
                    val optionObject = optionsObject.getJSONObject(key)
                    val optionText = optionObject.getString("text")
                    val optionPoints = optionObject.getInt("points")
                    options[key] = QuestionOption(optionText, optionPoints)
                }

                (questionsItems as ArrayList<Question>).add(Question(questionText, options))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJsonFromAsset(fileName: String): String {
        var json = ""
        try {
            val inputStream: InputStream = assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    private fun setAnswerStatus(answerView: TextView, colorResId: Int) {
        answerView.setBackgroundResource(colorResId)
        answerView.setTextColor(resources.getColor(R.color.text_color))
    }

    private fun resetAnswerStatus(answerView: TextView) {
        answerView.setBackgroundResource(R.color.blue_40)
        answerView.setTextColor(resources.getColor(R.color.text_color))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MaterialAlertDialogBuilder(this@StressQuiz)
            .setTitle(R.string.app_name)
            .setMessage("Are you sure want to exit the quiz?")
            .setNegativeButton(android.R.string.no) { dialogInterface, _ -> dialogInterface.dismiss() }
            .setPositiveButton(android.R.string.yes) { _, _ ->
                startActivity(Intent(this@StressQuiz, MainActivity::class.java))
                finish()
            }
            .show()
    }
}
