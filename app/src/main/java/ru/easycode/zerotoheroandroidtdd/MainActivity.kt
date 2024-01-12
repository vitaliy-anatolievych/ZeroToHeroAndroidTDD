package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(
        liveDataWrapper = LiveDataWrapper.Base(),
        repository = Repository.Base()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val actionButton = findViewById<Button>(R.id.actionButton)

        actionButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.livedata().observe(this) { uiState ->
            uiState.apply(actionButton, progressBar, titleTextView)
        }
    }
}