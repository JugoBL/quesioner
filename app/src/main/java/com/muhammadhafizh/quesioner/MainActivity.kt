package com.muhammadhafizh.quesioner
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    var easycard: MaterialCardView? = null
    var difficultcard: MaterialCardView? = null
    var aboutcard: MaterialCardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        easycard = findViewById<MaterialCardView>(R.id.easyCard)
        difficultcard = findViewById<MaterialCardView>(R.id.difficultCard)
        aboutcard = findViewById<MaterialCardView>(R.id.aboutCard)

        easycard?.setOnClickListener {
            startActivity(Intent(this@MainActivity, BasicQuiz::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        materialAlertDialogBuilder.setTitle(R.string.app_name)
        materialAlertDialogBuilder.setMessage("Are you sure want to exit the app?")
        materialAlertDialogBuilder.setNegativeButton(
            android.R.string.no
        ) { dialogInterface, _ -> dialogInterface.dismiss() }
        materialAlertDialogBuilder.setPositiveButton(
            android.R.string.yes
        ) { _, _ -> finish() }
        materialAlertDialogBuilder.show()
    }
}
