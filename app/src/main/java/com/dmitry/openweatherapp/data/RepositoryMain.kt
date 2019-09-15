package com.dmitry.openweatherapp.data

import android.annotation.SuppressLint
import com.dmitry.openweatherapp.db.App
import com.dmitry.openweatherapp.db.models.WeatherDB
import com.dmitry.openweatherapp.models.Weather
import com.dmitry.openweatherapp.network.WeatherAPI
import com.dmitry.openweatherapp.presenter.interfaces.OnFinishedListener
import com.dmitry.openweatherapp.utils.DefaultCities
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class RepositoryMain {

    var api: WeatherAPI.ApiInterface? = null
    var units = "metric"
    var key = "e8493aabaa9798679024a324aa54643e"
    lateinit var list: ArrayList<Weather>
    val db = App.getInstance().getDatabase()

    fun loadDataFromDB() {
        list = ArrayList()
        var weatherDB: ArrayList<WeatherDB> = ArrayList()
        runBlocking {
            weatherDB = getWeathers()
        }
        weatherDB.forEach {
            val weather = Weather()
            weather.id = it.id.toString()
            weather.name = it.name
            weather.main = Weather.Main()
            weather.main!!.temp = it.temp
            list.add(weather)
        }

    }

    suspend fun getWeathers() =  withContext(Dispatchers.IO) {
        db.weatherDao().allData as ArrayList<WeatherDB>
    }

    fun getAllWeather(presenterMain: OnFinishedListener){
        getWeatherByName(DefaultCities.CITY_1, presenterMain)
        getWeatherByName(DefaultCities.CITY_2, presenterMain)
        getWeatherByName(DefaultCities.CITY_3, presenterMain)
        loadDataFromDB()
        for (w in list){
            w.name?.let { getWeatherByName(it, presenterMain) }
        }
    }

    @SuppressLint("CheckResult")
    fun getWeatherByName(name: String, presenterMain: OnFinishedListener) {
        api = WeatherAPI.getClient()!!.create(WeatherAPI.ApiInterface::class.java)
        api!!.getWeather(name, units, key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ s ->
                if (s != null) {
                    presenterMain.onFinished(s)
                }
            },
                { e ->
                    println(e)
                    GlobalScope.launch {
                        val itDb = db.weatherDao().loadWeather(name)
                        val weather = Weather()
                        weather.id = itDb.id.toString()
                        weather.name = itDb.name
                        weather.main = Weather.Main()
                        weather.main!!.temp = itDb.temp
                        presenterMain.onFinished(weather)
                    }
                },
                { println("onComplete") })
    }



    @SuppressLint("CheckResult")
    fun getForecast(id: String, presenterForecast: OnFinishedListener) {
        api = WeatherAPI.getClient()!!.create(WeatherAPI.ApiInterface::class.java)
        api!!.getForecast(id, units, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ s ->
                if (s != null) {
                    presenterForecast.onFinished(s)
                }
            },{e -> presenterForecast.onFailure(e)})
    }

    fun saveWeatherToDB(weather: Weather){
        GlobalScope.launch {
            db.weatherDao().insert(WeatherDB(weather))
        }
    }

    fun deleteWeatherFromDB(weather: Weather){
        GlobalScope.launch {
            db.weatherDao().delete(WeatherDB(weather))
        }
    }

}