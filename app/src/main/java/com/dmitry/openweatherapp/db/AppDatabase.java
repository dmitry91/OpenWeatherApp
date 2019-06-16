package com.dmitry.openweatherapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.dmitry.openweatherapp.db.models.WeatherDB;

@Database(entities = {WeatherDB.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}