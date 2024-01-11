package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import java.io.Serializable

interface UiState: Serializable {

    fun apply(
        decrementButton: Button,
        incrementButton: Button,
        textView: TextView
    )

    data class Min(private val text: String) : UiState {
        override fun apply(decrementButton: Button, incrementButton: Button, textView: TextView) {
            textView.text = text
            decrementButton.isEnabled = false
            incrementButton.isEnabled = true
        }
    }

    data class Base(private val text: String) : UiState {
        override fun apply(decrementButton: Button, incrementButton: Button, textView: TextView) {
            textView.text = text
            decrementButton.isEnabled = true
            incrementButton.isEnabled = true
        }
    }

    data class Max(private val text: String) : UiState {
        override fun apply(decrementButton: Button, incrementButton: Button, textView: TextView) {
            textView.text = text
            decrementButton.isEnabled = true
            incrementButton.isEnabled = false
        }
    }
}