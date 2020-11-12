/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.restfulapi.api.jsonapi.get.internal;

import de.linzn.localWeather.LocalWeatherPlugin;
import de.linzn.localWeather.engine.WeatherContainer;
import de.linzn.restfulapi.api.jsonapi.get.IGetJSON;
import org.json.JSONObject;

import java.util.List;

public class GET_Weather implements IGetJSON {
    @Override
    public Object getRequestData(List<String> inputList) {

        int weatherID = -1;
        String description = "N.A";
        double current = -1;
        double minTemp = -1;
        double maxTemp = -1;
        String location = "None";
        double humidity = 0;
        double pressure = 0;


        WeatherContainer weatherContainer = LocalWeatherPlugin.localWeatherPlugin.getWeatherData();

        if (weatherContainer != null) {
            weatherID = weatherContainer.getICON();
            description = weatherContainer.getWeatherDescription();
            current = weatherContainer.getTemp();
            minTemp = weatherContainer.getTemp_min();
            maxTemp = weatherContainer.getTemp_max();
            location = weatherContainer.getLocation();
            pressure = weatherContainer.getPressure();
            humidity = weatherContainer.getHumidity();
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("weatherID", weatherID);
        jsonObject.put("description", description);
        jsonObject.put("currentTemp", current);
        jsonObject.put("minTemp", minTemp);
        jsonObject.put("maxTemp", maxTemp);
        jsonObject.put("location", location);
        jsonObject.put("pressure", pressure);
        jsonObject.put("humidity", humidity);

        return jsonObject;
    }

    @Override
    public Object getGenericData() {
        return getRequestData(null);
    }

    @Override
    public String name() {
        return "weather";
    }
}
