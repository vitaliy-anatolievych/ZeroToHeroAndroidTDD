package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.io.Serializable

interface UiState: Serializable {

    fun apply(
        actionButton: Button,
        titleTextView: TextView,
        progressBar: ProgressBar
    )

    data object LOAD: UiState {
        override fun apply(
            actionButton: Button,
            titleTextView: TextView,
            progressBar: ProgressBar
        ) {
            actionButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
        }
    }

    data object DEFAULT: UiState {
        override fun apply(
            actionButton: Button,
            titleTextView: TextView,
            progressBar: ProgressBar
        ) {
            actionButton.isEnabled = true
            titleTextView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    data object SUCCESS: UiState {
        override fun apply(
            actionButton: Button,
            titleTextView: TextView,
            progressBar: ProgressBar
        ) {
            actionButton.isEnabled = true
            titleTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}