package com.dmitry.openweatherapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dmitry.openweatherapp.R
import com.dmitry.openweatherapp.models.Forecast
import kotlinx.android.synthetic.main.forecast_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastItemRecyclerViewAdapter(private var mValues: List<Forecast.Entities>?, var context: Context) : RecyclerView.Adapter<ForecastItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues!!.get(position)
        holder.mDate.text = getDate( (item.dt + "000").toLong())
        holder.mTemp.text = item.main!!.temp
        holder.mPressure.text = item.main.pressure
        holder.mDescription.text = item.detWeather?.get(0)!!.description

        Glide
            .with(context)
            .load("http://openweathermap.org/img/w/"+item.detWeather.get(0).icon+".png")
            .into(holder.mImg);
    }

    override fun getItemCount(): Int = mValues!!.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mDate: TextView = mView.date
        val mTemp: TextView = mView.temp
        val mPressure: TextView = mView.pressure
        val mDescription: TextView = mView.description
        val mImg: ImageView = mView.imageView

    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }
}
