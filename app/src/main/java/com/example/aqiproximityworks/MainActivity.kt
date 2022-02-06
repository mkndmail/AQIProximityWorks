package com.example.aqiproximityworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        startSocket(mainViewModel)
        val adapter = AQIListAdapter(ItemClickListener {

        })
        mainViewModel.aqiOfCities.observe(this) {
            adapter.submitList(it)
        }
        findViewById<RecyclerView>(R.id.rv_forecast).adapter = adapter
        findViewById<Button>(R.id.btn_try_agin).setOnClickListener {
            startSocket(mainViewModel)
        }

        mainViewModel.setNoInternet.observe(this) { noInternet ->
            if (noInternet) {
                findViewById<LinearLayout>(R.id.ll_no_internet).visibility = VISIBLE
                findViewById<LinearLayout>(R.id.llRv).visibility = GONE
            } else {
                findViewById<LinearLayout>(R.id.ll_no_internet).visibility = GONE
                findViewById<LinearLayout>(R.id.llRv).visibility = VISIBLE
            }
        }
    }

    private fun startSocket(mainViewModel: MainViewModel) {
        if (mainViewModel.isInternetAvailable(this)) {
            mainViewModel.setNoInternet(false)
            val client = OkHttpClient()
            mainViewModel.startSocket(client)
        } else {
            mainViewModel.setNoInternet(true)
        }
    }



}