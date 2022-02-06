package com.example.aqiproximityworks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aqiproximityworks.databinding.LayoutAqiBinding

/**
 * Created by Mukund Gururani, mkndmail@gmail.com on 2/5/2022
 */
class AQIListAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<AQIListAdapter.ViewHolder>() {

    var aqiForecastModel = mutableListOf<AQIForecastModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = aqiForecastModel[position]
        holder.bind(clickListener, result)
    }

    override fun getItemCount(): Int {
        return aqiForecastModel.size
    }

    class ViewHolder private constructor(private val binding: LayoutAqiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ItemClickListener, aqiForecastModel: AQIForecastModel) {
            binding.aqiModel = aqiForecastModel
            binding.executePendingBindings()
            binding.click = clickListener
            binding.llItem.setOnClickListener {
                clickListener.onClick(aqiForecastModel)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutAqiBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    fun submitList(list: List<AQIForecastModel>) {
        aqiForecastModel.clear()
        aqiForecastModel.addAll(list)
        notifyDataSetChanged()
    }


}

class ItemClickListener(val clickListener: (result: AQIForecastModel) -> Unit) {
    fun onClick(result: AQIForecastModel) = clickListener(result)
}