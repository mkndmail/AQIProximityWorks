package com.example.aqiproximityworks

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * Created by Mukund Gururani, mkndmail@gmail.com on 2/6/2022
 */

@BindingAdapter("setAqi")
fun TextView.setAqiStatus(aqi: Double) {
    this.text = getFormattedString(aqi)
    when (aqi.toInt()) {
        in 0..50 -> {
            this.setTextColor(getColor(this.context, R.color.colorAqiGood))
        }
        in 51..100 -> {
            this.setTextColor(getColor(this.context, R.color.colorAqiSatisfactory))
        }
        in 101..200 -> {
            this.setTextColor(getColor(this.context, R.color.colorAqiModerate))
        }
        in 201..300 -> {
            this.setTextColor(getColor(this.context, R.color.colorAqiPoor))
        }
        in 301..400 -> {
            this.setTextColor(getColor(this.context, R.color.colorAqiVeryPoor))
        }
        in 401..500 -> {
            this.setTextColor(getColor(this.context, R.color.colorAqiSevere))
        }
    }
}

private fun getColor(context: Context, id: Int) =
    ContextCompat.getColor(context, id)

fun getFormattedString(aqi: Double): String {
    return String.format("%.2f", aqi)
}
