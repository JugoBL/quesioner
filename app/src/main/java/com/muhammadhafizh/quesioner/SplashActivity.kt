package com.muhammadhafizh.quesioner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



    class SplashActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash)

            val mSplashThread = object : Thread() {
                override fun run() {
                    try {
                        sleep(2000)
                    } catch (ignored: InterruptedException) {
                    } finally {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                }
            }

            mSplashThread.start()
        }
    }
