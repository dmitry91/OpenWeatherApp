package com.dmitry.openweatherapp.presenter

import com.dmitry.openweatherapp.data.RepositoryMain
import com.dmitry.openweatherapp.models.Weather
import com.dmitry.openweatherapp.presenter.interfaces.OnFinishedListener
import com.dmitry.openweatherapp.ui.MainFragment

class PresenterMain (mainFragment: MainFragment) : OnFinishedListener {

    private var viewMain: MainFragment ?= mainFragment
    private var repositoryMain: RepositoryMain = RepositoryMain();

    fun getData(name:String) {
        repositoryMain.getWeatherByName(name, this)
    }

    fun getData() {
        repositoryMain.getAllWeather(this)
    }

    fun saveWeatherToDB(weather: Weather){
        repositoryMain.saveWeatherToDB(weather)
    }

    fun deleteWeatherFromDB(weather: Weather){
        repositoryMain.deleteWeatherFromDB(weather)
    }

    override fun onFinished(any: Any) {
        viewMain!!.addCityToList(any as Weather)
    }

    override fun onFailure(t: Throwable) {
        t.printStackTrace()
    }

    fun destroy(){
        viewMain = null
    }

}
