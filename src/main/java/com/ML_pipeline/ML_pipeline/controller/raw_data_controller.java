package com.ML_pipeline.ML_pipeline.controller;

import com.ML_pipeline.ML_pipeline.model.raw_data;
import com.ML_pipeline.ML_pipeline.service.raw_data_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/master")
public class raw_data_controller {

    @Autowired
    private raw_data_service rds;

    @PostMapping("/add")
    public String add_raw_data(@RequestBody ArrayList<raw_data> rd){
        rds.add_raw_data(rd);

        //System.out.println(weather);

        return "success add data";
    }

    /*@GetMapping
    public List<x_> getWeather(){
        return weatherService.getWeather();
    }

    @GetMapping("/get")
    public x_ getWeather(@RequestParam Integer id){
        return weatherService.getWeather(id);
    }

    //What is a Response Enitity?
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateWeather(@PathVariable Integer id, @RequestBody x_ weather){
        weatherService.updateWeather(id, weather);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWeather(@PathVariable Integer id){
        weatherService.deleteWeather(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-.../{id}")
    public ResponseEntity<Void> updateCityName(@PathVariable Integer id, @RequestBody x_DTO weatherDTO ){
        weatherService.updateCityName(id, weatherDTO);

        return ResponseEntity.noContent().build();
    }*/
}
