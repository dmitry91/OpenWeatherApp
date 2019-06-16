package com.dmitry.openweatherapp.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dmitry.openweatherapp.R
import com.dmitry.openweatherapp.models.Forecast
import com.dmitry.openweatherapp.presenter.PresenterForecast
import com.dmitry.openweatherapp.ui.adapters.ForecastItemRecyclerViewAdapter

const val ARG_PARAM1 = "param1"

class DetailsFragment : Fragment() {

    private var param1: String? = null
    private  var presenterForecast = PresenterForecast(this)
    var recyclerView : RecyclerView ?= null
    var nameCity : TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1,"0")
        }
        presenterForecast.getData(param1.toString())
    }

    fun setData(forecast: Forecast){
        nameCity!!.setText(forecast.city!!.name)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        val adapter = ForecastItemRecyclerViewAdapter(forecast.entities)
        recyclerView!!.setAdapter(adapter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        nameCity = view.findViewById(R.id.nameCity)
        recyclerView  = view!!.findViewById(R.id.list)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideFloatingActionButton()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showFloatingActionButton()
        presenterForecast.destroy()
    }

}
