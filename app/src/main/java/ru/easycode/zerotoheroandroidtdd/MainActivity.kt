package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        val text = findViewById<TextView>(R.id.titleTextView)
        val button = findViewById<Button>(R.id.removeButton)

        viewStateManager?.attach(this, text)

        button.setOnClickListener {
            viewStateManager?.removeView(text, this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("KEY", viewStateManager)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewStateManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable("KEY", ViewStateManager::class.java) as ViewStateManager
        } else {
            savedInstanceState.getSerializable("KEY") as ViewStateManager
        }
    }
}

class ViewStateManager: Serializable {

    var viewState: ViewState = ViewState.ATTACHED

    fun attach(activity: AppCompatActivity, view: View) {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                val root = activity.findViewById<LinearLayout>(R.id.rootLayout)
                if (viewState is ViewState.REMOVED) {
                    root.removeView(view)
                }
            }
        })
    }

    fun removeView(view: View, activity: AppCompatActivity) {
        val root = activity.findViewById<LinearLayout>(R.id.rootLayout)
        viewState = ViewState.REMOVED
        root.removeView(view)
    }
}

sealed class ViewState {
    data object ATTACHED : ViewState()
    data object REMOVED : ViewState()
}