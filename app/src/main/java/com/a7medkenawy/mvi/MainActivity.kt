package com.a7medkenawy.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: AddNumberViewModel
    lateinit var tvNumber: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(AddNumberViewModel::class.java)

        tvNumber = findViewById<TextView>(R.id.number_tv)
        val btnNumber = findViewById<Button>(R.id.add_number_button)
        render()

        btnNumber.setOnClickListener {
            lifecycleScope.launch {
                viewModel.internalChannel.send(MainIntent.AddNumber)
                render()
            }
        }
    }

    fun render() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainViewState.Idle -> tvNumber.text = "idle"
                    is MainViewState.Success -> tvNumber.text = it.number.toString()
                    is MainViewState.Error -> tvNumber.text = it.message
                }
            }
        }
    }
}