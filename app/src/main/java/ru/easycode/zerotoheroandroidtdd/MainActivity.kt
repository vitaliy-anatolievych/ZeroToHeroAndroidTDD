package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState != null) {
            list = savedInstanceState.getStringArrayList(TAG) as ArrayList<String>
        }

        with(binding) {
            if (list.isNotEmpty()) {
                list.forEach { text ->
                    val view = TextView(this@MainActivity)
                    view.text = text

                    contentLayout.addView(view)
                }
            }

            actionButton.setOnClickListener {
                val view = TextView(this@MainActivity)
                val text = inputEditText.text.toString()
                view.text = text
                inputEditText.setText("")
                list.add(text)

                contentLayout.addView(view)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(TAG, list)
    }

    companion object {
        const val TAG = "tag"
    }
}