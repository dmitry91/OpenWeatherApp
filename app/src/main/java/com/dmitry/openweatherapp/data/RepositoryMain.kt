package com.dmitry.openweatherapp.data

import com.dmitry.openweatherapp.models.Forecast
import com.dmitry.openweatherapp.models.Weather
import com.dmitry.openweatherapp.network.WeatherAPI
import com.dmitry.openweatherapp.presenter.interfaces.OnFinishedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryMain {

    var api: WeatherAPI.ApiInterface? = null
    var units = "metric"
    var key = "e8493aabaa9798679024a324aa54643e"

    fun getWeather(name: String, presenterMain: OnFinishedListener) {
        api = WeatherAPI.getClient()!!.create(WeatherAPI.ApiInterface::class.java)
        val callToday = api!!.getWeather(name, units, key)
        callToday.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.body() != null){
                    presenterMain.onFinished(response.body())
                }
            }
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getForecast(id: String, presenterForecast: OnFinishedListener) {
        api = WeatherAPI.getClient()!!.create(WeatherAPI.ApiInterface::class.java)
        val callToday = api!!.getForecast(id, units, key)
        callToday.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                presenterForecast.onFinished(response.body())
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                presenterForecast.onFailure(t)
            }
        })
    }

}