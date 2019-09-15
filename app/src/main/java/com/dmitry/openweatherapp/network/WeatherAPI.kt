package com.dmitry.openweatherapp.network

import com.dmitry.openweatherapp.models.Forecast
import com.dmitry.openweatherapp.models.Weather
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherAPI{

    val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    interface ApiInterface {
        @GET("weather")
        fun getWeather(
            @Query("q") q: String?,
            @Query("units") units: String,
            @Query("appid") appid: String
        ): Observable<Weather>

        @GET("forecast")
        fun getForecast(
            @Query("id") q: String?,
            @Query("units") units: String,
            @Query("appid") appid: String
        ): Observable<Forecast>
    }


}