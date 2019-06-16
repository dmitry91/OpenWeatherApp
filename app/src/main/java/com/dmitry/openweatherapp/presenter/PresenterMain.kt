package com.dmitry.openweatherapp.presenter

import com.dmitry.openweatherapp.data.RepositoryMain
import com.dmitry.openweatherapp.models.Weather
import com.dmitry.openweatherapp.presenter.interfaces.OnFinishedListener
import com.dmitry.openweatherapp.ui.MainFragment

class PresenterMain (mainFragment: MainFragment) : OnFinishedListener {

    private var viewMain: MainFragment ?= mainFragment
    private var repositoryMain: RepositoryMain = RepositoryMain();

    fun getData(name:String) {
        repositoryMain.getWeather(name, this)
    }

    override fun onFinished(any: Any) {
        viewMain!!.addCityToList(any as Weather)
    }

    override fun onFailure(t: Throwable) {
        viewMain!!.loadDataFromDB()
        t.printStackTrace()
    }

    fun destroy(){
        viewMain = null
    }

}
