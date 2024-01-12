package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            actionButton.setOnClickListener {
                val text = inputEditText.text.toString()
                addTextView(text)

                inputEditText.setText("")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) = with(binding) {
        super.onSaveInstanceState(outState)
        val textList =
            contentLayout.children.map { (it as TextView).text.toString() }.toList()
        outState.putStringArrayList(TAG, ArrayList(textList))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val list = savedInstanceState.getStringArrayList(TAG) ?: ArrayList()

        list.forEach { inputText ->
            addTextView(inputText)
        }
    }

    private fun addTextView(inputText: String) {
        val textView = TextView(this).apply {
            text = inputText
        }
        binding.contentLayout.addView(textView)
    }

    companion object {
        const val TAG = "tag"
    }
}