package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val count: Count = Count.Base(step = 2, max = 4, min = 0)
    private lateinit var uiState: UiState

    private lateinit var textView: TextView
    private lateinit var increment: Button
    private lateinit var decrement: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.countTextView)
        increment = findViewById(R.id.incrementButton)
        decrement = findViewById(R.id.decrementButton)

        increment.setOnClickListener {
            val number = textView.text.toString()
            uiState = count.increment(number)
            uiState.apply(decrement, increment, textView)
        }

        decrement.setOnClickListener {
            val number = textView.text.toString()
            uiState = count.decrement(number)
            uiState.apply(decrement, increment, textView)
        }

        if (savedInstanceState == null) {
            uiState = count.initial(textView.text.toString())
            uiState.apply(decrement, increment, textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("KEY", uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        uiState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(
                "KEY",
                UiState::class.java
            ) as UiState
        } else {
            savedInstanceState.getSerializable("KEY") as UiState
        }

        uiState.apply(decrement, increment, textView)
    }
}

