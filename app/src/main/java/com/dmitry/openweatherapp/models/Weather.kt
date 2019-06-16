package com.dmitry.openweatherapp.models

import com.google.gson.annotations.SerializedName
class Weather{

    @SerializedName("id")
    var id:String?=null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("main")
    var main:Main?= null

    class Main{
        @SerializedName("temp")
        var temp:String?=null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Weather
        if (id != other.id) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }


}
