package com.dmitry.openweatherapp.db.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.dmitry.openweatherapp.models.Weather;
import org.jetbrains.annotations.NotNull;

@Entity(tableName = "weather")
public class WeatherDB {
    @PrimaryKey

    private int id;
    private String name;
    private String temp;

    public WeatherDB() {
    }

    public WeatherDB(@NotNull Weather weather) {
        id = Integer.parseInt(weather.getId());
        name = weather.getName();
        temp = weather.getMain().getTemp();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
