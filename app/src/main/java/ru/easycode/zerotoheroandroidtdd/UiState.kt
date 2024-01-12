package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doBeforeTextChanged

interface UiState {

    fun apply(
        editText: EditText,
        textView: TextView,
        button: Button
    )

    data object Base: UiState {
        override fun apply(editText: EditText, textView: TextView, button: Button) {
            editText.setText("")
            button.isEnabled = false
        }
    }

    data class OnEdit(val text: String): UiState {
        override fun apply(editText: EditText, textView: TextView, button: Button) {
            editText.setOnClickListener {
                editText.setSelection(editText.length())
            }

            if (text.length >= 3) {
                button.isEnabled = true
            }
        }
    }

    data class OnSave(val text: String): UiState {
        override fun apply(editText: EditText, textView: TextView, button: Button) {
            editText.setText("")
            textView.text = text
            button.isEnabled = false
        }
    }
}