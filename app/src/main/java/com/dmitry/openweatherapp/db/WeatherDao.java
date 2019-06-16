package com.dmitry.openweatherapp.db;

import android.arch.persistence.room.*;
import com.dmitry.openweatherapp.db.models.WeatherDB;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherDB weather);

    @Update
    void update(WeatherDB weather);

    @Delete
    void delete(WeatherDB weather);

    @Query("SELECT * FROM Weather")
    List<WeatherDB> getAllData();


}
