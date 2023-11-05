package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        val text = findViewById<TextView>(R.id.countTextView)
        val button = findViewById<Button>(R.id.incrementButton)


        button.setOnClickListener {
            val value = text.text.toString().toInt()
            text.text = "${value + 2}"
        }
    }
}
