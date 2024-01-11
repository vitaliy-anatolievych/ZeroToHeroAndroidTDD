package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var actionButton: Button
    private lateinit var titleTextView: TextView
    private lateinit var progressBar: ProgressBar

    private var uiState: UiState = UiState.DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionButton = findViewById(R.id.actionButton)
        titleTextView = findViewById(R.id.titleTextView)
        progressBar = findViewById(R.id.progressBar)

        uiState.apply(actionButton, titleTextView, progressBar)


        actionButton.setOnClickListener {
            uiState = UiState.LOAD
            uiState.apply(actionButton, titleTextView, progressBar)

            actionButton.postDelayed({
                uiState = UiState.SUCCESS
                uiState.apply(actionButton, titleTextView, progressBar)
            }, 3000)
        }
    }
}