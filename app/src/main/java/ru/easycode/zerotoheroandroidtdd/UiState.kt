package ru.easycode.zerotoheroandroidtdd

import android.widget.EditText
import android.widget.TextView

interface UiState {

    fun apply(
        editText: EditText,
        textView: TextView
    )

    data object Base: UiState {
        override fun apply(editText: EditText, textView: TextView) {
            editText.setText("")
        }
    }

    data class Change(val text: String): UiState {
        override fun apply(editText: EditText, textView: TextView) {
            editText.setText("")
            textView.text = text
        }
    }
}