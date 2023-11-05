package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        val button = findViewById<Button>(R.id.hideButton)

        if (savedInstanceState != null) {
            textView.isVisible = savedInstanceState.getBoolean("TextVisibility")
        }

        button.setOnClickListener {
            textView.isVisible = !textView.isVisible
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("TextVisibility", textView.isVisible)
    }
}