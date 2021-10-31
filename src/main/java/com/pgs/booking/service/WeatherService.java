package com.pgs.booking.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pgs.booking.model.dto.WeatherDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final String hostName = "x-rapidapi-host";
    private final String hostValue = "community-open-weather-map.p.rapidapi.com";
    private final String keyName = "x-rapidapi-key";
    private final String keyValue = "c72dd541f1msh23890d6e014e604p10d3c9jsn3a67da9a0d84";

    public WeatherDto getWeatherByCity(String city) {
        OkHttpClient client = new OkHttpClient();
        String urlWeather = "https://community-open-weather-map.p.rapidapi.com/weather?q=" + city + "&units=imperial&mode=JSON";

        Request request = new Request.Builder()
                .url(urlWeather)
                .get()
                .addHeader(hostName, hostValue)
                .addHeader(keyName, keyValue)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonData = Objects.requireNonNull(response.body()).string();

            Map<String, Object> respMap = jsonToMap(jsonData);
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

            var temperature = mainMap.get("temp");
            var humidity = mainMap.get("humidity");
            var pressure = mainMap.get("pressure");
            var windSpeed = windMap.get("speed");

            return WeatherDto.builder()
                    .temperature(temperature.toString())
                    .humidity(humidity.toString())
                    .pressure(pressure.toString())
                    .windSpeed(windSpeed.toString()).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Can not load weather from server.");
    }

    private Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
    }
}
