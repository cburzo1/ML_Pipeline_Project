package com.ML_pipeline.ML_pipeline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/...")
public class simple_linear_regression_controller {

    @Autowired
    private x_Service weatherService;

    @PostMapping("/add")
    public String add_linear_regression_data(@RequestBody linear_regression_data){
        weatherService.add_linear_regression_data(linear_regression_data);

        //System.out.println(weather);

        return "success add weather";
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
