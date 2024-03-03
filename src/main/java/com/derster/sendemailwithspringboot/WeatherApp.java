package com.derster.sendemailwithspringboot;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherApp {
    private static final String API_KEY = "02c08b0fff5d456a1826de3b4548bb0b";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid="+API_KEY;

    public static void main(String[] args) {
        List<String> cities = new ArrayList<>();
        // Ajoutez des noms de villes à la liste
        cities.add("Paris");
        cities.add("London");
        cities.add("New York");
        cities.add("Tokyo");

        // Créez et démarrez un thread pour chaque ville
        List<Thread> threadArrayList = new ArrayList<>();
        for (String city : cities) {
            Thread thread = new Thread(() -> {
                try {
                    JSONObject weatherData = getWeatherData(city);
                    System.out.println("Weather in " + city + ": " + weatherData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            threadArrayList.add(thread);
        }

        // Attendre la fin de tous les threads
        for (Thread thread : threadArrayList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All threads have finished fetching weather data.");

    }

    private static JSONObject getWeatherData(String city) throws IOException {
        URL url = new URL(String.format(API_URL, city, API_KEY));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }
}


