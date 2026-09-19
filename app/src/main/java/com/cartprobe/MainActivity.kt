package com.cartprobe

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val settingsButton = Button(this).apply {
            text = "Enable Screen Reading"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        val readButton = Button(this).apply {
            text = "READ CURRENT SCREEN"
            setOnClickListener {
                val service = CartAccessibilityService.instance

                resultText.text = service?.readCurrentScreen()
                    ?: "CartProbe service is not enabled."
            }
        }

        resultText = TextView(this).apply {
            text = "CartProbe ready.\n\nEnable Screen Reading, then return to Amazon/Flipkart."
            textSize = 16f
        }

        layout.addView(settingsButton)
        layout.addView(readButton)
        layout.addView(resultText)

        setContentView(layout)
    }
}
