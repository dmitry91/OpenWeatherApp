package com.dmitry.openweatherapp.presenter

import com.dmitry.openweatherapp.data.RepositoryMain
import com.dmitry.openweatherapp.models.Forecast
import com.dmitry.openweatherapp.presenter.interfaces.OnFinishedListener
import com.dmitry.openweatherapp.ui.DetailsFragment

class PresenterForecast (fragment: DetailsFragment) : OnFinishedListener {

    private var viewMain: DetailsFragment? = fragment
    private var repositoryMain: RepositoryMain = RepositoryMain();

    fun getData(id: String) {
        repositoryMain.getForecast(id, this)
    }

    override fun onFinished(any: Any) {
        viewMain!!.setData(any as Forecast)
    }

    override fun onFailure(t: Throwable) {
        t.printStackTrace()
    }

    fun destroy() {
        viewMain = null
    }
}