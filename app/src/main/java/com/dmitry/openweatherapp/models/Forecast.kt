package com.dmitry.openweatherapp.models

import com.google.gson.annotations.SerializedName

class Forecast {

    @SerializedName("city")
    val city: City? = null
    @SerializedName("list")
    var entities:List<Entities>?=null

    class Entities {
        @SerializedName("dt")
        val dt:String?= null
        @SerializedName("main")
        val main:Main?= null

        class Main{
            @SerializedName("temp")
            var temp:String?=null
            @SerializedName("pressure")
            var pressure:String?=null
            @SerializedName("humidity")
            var humidity:String?=null
        }
    }

    class City{
        @SerializedName("name")
        val name: String? = null
    }
}