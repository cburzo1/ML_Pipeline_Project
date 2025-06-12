package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.raw_data;
import com.ML_pipeline.ML_pipeline.repository.raw_data_repo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class raw_data_service_impl implements raw_data_service {
    @Autowired
    private raw_data_repo rdr;

    ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(raw_data_service.class);

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
    public void add_raw_data(ArrayList<raw_data> rd){

        try {
            String json = mapper.writeValueAsString(rd);

            logger.debug("See json for raw data {}",json);

            for(int i = 0; i < rd.size(); i++){
                rdr.save(rd.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
