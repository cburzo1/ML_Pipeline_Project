package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.simple_linear_regression;
import com.ML_pipeline.ML_pipeline.repository.simple_linear_regression_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class simple_linear_regression_service_impl implements simple_linear_regression_service {
    @Autowired
    private simple_linear_regression_Repo slrs;

    //private final RestTemplate restTemplate;

    /*public simple_linear_regression_service_impl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }*/

    /*public void getWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=f22099277e7c5b092f838a7218ea4c6e";

        //System.out.println(restTemplate.getForObject(url, x_Service.class));

        //return restTemplate.getForObject(url, WeatherResponse.class);
    }*/

    @Override
    public void add_linear_regression_data(simple_linear_regression slrs_data){
        System.out.println("HELLO?");
        slrs.save(slrs_data);
    }

    /*@Override
    public List<x_> getWeather(){
        return weatherRepository.findAll();
    }

    @Override
    public x_ getWeather(Integer id){
        x_ weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));
        return weather;
    }

    @Override
    public void updateWeather(Integer id, x_ weather){
        //check if the object of the given id is in the database
        weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weather.setId(id);
        weatherRepository.save(weather);
    }

    @Override
    public void deleteWeather(Integer id){
        x_ weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weatherRepository.delete(weather);
    }

    @Override
    public void updateCityName(Integer id, x_DTO weatherDTO){
        x_ weather = weatherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid weather id" + id));

        weather.setCity_name(weatherDTO.getCity_name());

        weatherRepository.save(weather);
    } */
}
