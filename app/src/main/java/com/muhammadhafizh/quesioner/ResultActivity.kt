package com.muhammadhafizh.quesioner


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class ResultActivity : AppCompatActivity() {
    private var home: MaterialCardView? = null
    private var correctt: TextView? = null
    private var resultinfo: TextView? = null
    private var resultscore: TextView? = null
    private var resultImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        home = findViewById(R.id.returnHome)
        resultscore = findViewById(R.id.resultScore)
        resultinfo = findViewById(R.id.resultInfo)
        resultImage = findViewById(R.id.resultImage)

        // Retrieve accumulated points from the intent
        val points = intent.getIntExtra("points", 0)

        resultscore?.text = points.toString() // Display accumulated points

        // Your logic for displaying result based on points goes here

        home?.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
            finish()
        }
    }
}