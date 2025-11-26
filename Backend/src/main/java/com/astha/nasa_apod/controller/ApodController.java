package com.astha.nasa_apod.controller;

import com.astha.nasa_apod.model.ApodResponse;
import com.astha.nasa_apod.service.ApodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/apod")
public class ApodController {

    @Autowired
    private ApodService apodService;

    @GetMapping("/today")
    public ApodResponse getTodayApod(){
        return apodService.getTodayApod();
    }

    @GetMapping("/date")
    public ApodResponse getApodByDate(@RequestParam String date){
        return apodService.getApodByDate(date);
    }

    @GetMapping("/recent")
    public List<ApodResponse> getRecentApods(@RequestParam(defaultValue = "10") int count) {
        return apodService.getRecentApods(count);
    }


}
