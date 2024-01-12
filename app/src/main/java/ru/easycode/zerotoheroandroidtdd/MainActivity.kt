package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }


        with(binding) {
            UiState.Base.apply(inputEditText, titleTextView)

            actionButton.setOnClickListener {
                UiState.Change(inputEditText.text.toString()).also { state ->
                    state.apply(inputEditText, titleTextView)
                }
            }
        }
    }
}