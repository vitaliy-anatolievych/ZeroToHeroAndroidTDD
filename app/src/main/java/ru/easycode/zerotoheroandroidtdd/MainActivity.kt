package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }


        with(binding) {
            UiState.Base.apply(inputEditText, titleTextView, actionButton)

            inputEditText.doOnTextChanged { text, _, _, _ ->
                val state = UiState.OnEdit(text.toString())
                state.apply(inputEditText, titleTextView, actionButton)
            }

            actionButton.setOnClickListener {
                val state = UiState.OnSave(inputEditText.text.toString())
                state.apply(inputEditText, titleTextView, actionButton)
            }
        }
    }
}