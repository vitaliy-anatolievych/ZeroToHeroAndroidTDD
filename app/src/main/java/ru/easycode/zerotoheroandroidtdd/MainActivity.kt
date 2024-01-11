package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var viewStateManager: ViewStateManager? = null
    private var count: Count? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        count = Count.Base(step = 2, max = 4, min = 0)
        viewStateManager = ViewStateManager()
    }

    override fun onResume() {
        super.onResume()
        val textView = findViewById<TextView>(R.id.countTextView)
        val increment = findViewById<Button>(R.id.incrementButton)
        val decrement = findViewById<Button>(R.id.decrementButton)

        if (count?.initial(textView.text.toString()) is UiState.Min) {
            viewStateManager?.applyAction(ViewState.DISABLED(decrement))
        }

        if (count?.initial(textView.text.toString()) is UiState.Max) {
            viewStateManager?.applyAction(ViewState.DISABLED(increment))
        }

        increment.setOnClickListener {
            val value = textView.text.toString()
            when (val result = count?.increment(value)) {
                is UiState.Base -> {
                    textView.text = result.text
                    viewStateManager?.applyAction(ViewState.ENABLED(decrement))
                }

                is UiState.Max -> {
                    textView.text = result.text
                    viewStateManager?.applyAction(ViewState.DISABLED(increment))
                }

                else -> {}
            }
        }

        decrement.setOnClickListener {
            val value = textView.text.toString()
            when (val result = count?.decrement(value)) {
                is UiState.Base -> {
                    textView.text = result.text
                    viewStateManager?.applyAction(ViewState.ENABLED(increment))
                }

                is UiState.Min -> {
                    textView.text = result.text
                    viewStateManager?.applyAction(ViewState.DISABLED(decrement))
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("KEY", viewStateManager)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewStateManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(
                "KEY",
                ViewStateManager::class.java
            ) as ViewStateManager
        } else {
            savedInstanceState.getSerializable("KEY") as ViewStateManager
        }
    }
}

class ViewStateManager : Serializable {

    fun applyAction(action: ViewState) {
        when (action) {
            is ViewState.DISABLED ->
                action.view.isEnabled = false

            is ViewState.ENABLED ->
                action.view.isEnabled = true
        }
    }
}

sealed class ViewState {
    data class DISABLED(val view: View) : ViewState()
    data class ENABLED(val view: View) : ViewState()
}