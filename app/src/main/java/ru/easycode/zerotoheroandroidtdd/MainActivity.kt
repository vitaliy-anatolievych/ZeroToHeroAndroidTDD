package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var viewStateManager: ViewStateManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            viewStateManager = ViewStateManager()
        }
    }

    override fun onResume() {
        super.onResume()
        val text = findViewById<TextView>(R.id.countTextView)
        val button = findViewById<Button>(R.id.incrementButton)

        viewStateManager?.attach(this)

        val count = Count.Base(step = 2, max = 4)

        button.setOnClickListener {
            val value = text.text.toString()
            when (val result = count.increment(value)) {
                is UiState.Base -> text.text = result.text
                is UiState.Max -> {
                    text.text = result.text
                    viewStateManager?.applyAction(ViewState.DISABLED(button), this)
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

    private var viewState: ViewState = ViewState.ATTACHED

    fun attach(activity: AppCompatActivity) {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                val root = activity.findViewById<LinearLayout>(R.id.rootLayout)
                when (viewState) {
                    is ViewState.ATTACHED -> {}
                    is ViewState.DISABLED -> {
                        val button = root.children.toList().find { it is Button }
                        button?.let {
                            it.isEnabled = false
                        }
                    }

                    is ViewState.REMOVED -> {}
                }
            }
        })
    }

    fun applyAction(action: ViewState, activity: AppCompatActivity) {
        val root = activity.findViewById<LinearLayout>(R.id.rootLayout)

        when (action) {
            is ViewState.ATTACHED -> {}
            is ViewState.DISABLED -> {
                viewState = action
                val button = root.children.toList().find { it is Button }
                button?.let {
                    it.isEnabled = false
                }
            }

            is ViewState.REMOVED -> {}
        }
    }
}

sealed class ViewState {
    data object ATTACHED : ViewState()
    data class REMOVED(val view: View) : ViewState()
    data class DISABLED(val view: View) : ViewState()
}