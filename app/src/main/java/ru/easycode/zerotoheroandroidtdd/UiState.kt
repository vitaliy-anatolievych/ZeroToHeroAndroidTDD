package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.io.Serializable

interface UiState: Serializable {

    fun apply(
        button: Button,
        progressBar: ProgressBar,
        textView: TextView
    )

    data object Default: UiState {
        override fun apply(button: Button, progressBar: ProgressBar, textView: TextView) {
            button.isEnabled = true
            progressBar.visibility = View.GONE
            textView.visibility = View.GONE
        }
    }

    data object ShowProgress: UiState {
        override fun apply(button: Button, progressBar: ProgressBar, textView: TextView) {
            button.isEnabled = false
            progressBar.visibility = View.VISIBLE
        }
    }

    data class ShowData(val text: String): UiState {
        override fun apply(button: Button, progressBar: ProgressBar, textView: TextView) {
            button.isEnabled = true
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            textView.text = text
        }
    }
}