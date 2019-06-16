package com.dmitry.openweatherapp.presenter.interfaces

interface OnFinishedListener {
    fun onFinished(any: Any)
    fun onFailure(t: Throwable)
}