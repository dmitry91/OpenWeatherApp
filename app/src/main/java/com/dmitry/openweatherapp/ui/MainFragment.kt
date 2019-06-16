package com.dmitry.openweatherapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmitry.openweatherapp.db.App
import com.dmitry.openweatherapp.db.models.WeatherDB
import com.dmitry.openweatherapp.models.Weather
import com.dmitry.openweatherapp.presenter.PresenterMain
import com.dmitry.openweatherapp.ui.adapters.MainItemRecyclerViewAdapter
import com.dmitry.openweatherapp.utils.DefaultCities
import kotlinx.coroutines.*
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.dmitry.openweatherapp.R

class MainFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private var presenterMain = PresenterMain(this)
    private var list = ArrayList<Weather>()
    private var adapter: MainItemRecyclerViewAdapter?= null
    val db = App.getInstance().getDatabase()

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_city_list, container, false)
        val recyclerView: RecyclerView = view!!.findViewById(com.dmitry.openweatherapp.R.id.list)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MainItemRecyclerViewAdapter(list, listener,this)
        recyclerView.setAdapter(adapter)
        if (isInternetAvailable()){
            refresh()
        }else{
            loadDataFromDB()
        }
        return view
    }

    fun addCityToList(weather: Weather) {
        if (list.contains(weather)){
            list.remove(weather)
            list.add(weather)//update list
        }
        else{
            list.add(weather)
        }
        adapter!!.notifyDataSetChanged()

        if (isInternetAvailable()){
            GlobalScope.launch {
                db.weatherDao().insert(WeatherDB(weather))
            }
        }
    }

    fun loadCity(name:String){
        presenterMain.getData(name)
    }

    fun refresh(){
        loadCity(DefaultCities.CITY_1)
        loadCity(DefaultCities.CITY_2)
        loadCity(DefaultCities.CITY_3)
    }

    fun delete(weather: Weather) {
        list.remove(weather)
        adapter!!.notifyDataSetChanged()
        GlobalScope.launch {
            db.weatherDao().delete(WeatherDB(weather))
        }
    }

    fun loadDataFromDB(){
        GlobalScope.launch {
            val weatherDB: ArrayList<WeatherDB> = db.weatherDao().allData as ArrayList<WeatherDB>
            weatherDB.forEach {
                val weather = Weather()
                weather.id = it.id.toString()
                weather.name = it.name
                weather.main = Weather.Main()
                weather.main!!.temp = it.temp
                addCityToList(weather);
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        presenterMain.destroy()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Weather?)
    }

    fun isInternetAvailable(): Boolean {

        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


}
