package com.astha.nasa_apod.service;

import com.astha.nasa_apod.model.ApodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApodService {

    @Value("${nasa.api.key}")
    private String apiKey;

    @Value("${nasa.api.apod.url}")
    private String apodUrl;

    @Autowired
    private RestTemplate restTemplate;

    // Today’s APOD (cached)
    @Cacheable("todayApod")
    public ApodResponse getTodayApod() {
        String url = apodUrl + "?api_key=" + apiKey;
        return restTemplate.getForObject(url, ApodResponse.class);
    }

    // APOD by date (cached)
    @Cacheable(value = "apodByDate", key = "#date")
    public ApodResponse getApodByDate(String date) {
        LocalDate inputDate = LocalDate.parse(date);
        if (inputDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Future date not allowed.");
        }

        String url = apodUrl + "?api_key=" + apiKey + "&date=" + date;
        return restTemplate.getForObject(url, ApodResponse.class);
    }


    public boolean isFutureDate(String date) {
        LocalDate input = LocalDate.parse(date);
        return input.isAfter(LocalDate.now());
    }

    public List<ApodResponse> getRecentApods(int count) {

        List<ApodResponse> results = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int daysChecked = 0;
        int maxLookBack = count * 3; // Look up to 3x more days to ensure we get enough images

        while (results.size() < count && daysChecked < maxLookBack) {

            LocalDate date = today.minusDays(daysChecked);
            String url = apodUrl + "?api_key=" + apiKey + "&date=" + date;

            try {
                ApodResponse apod = restTemplate.getForObject(url, ApodResponse.class);

                if (apod != null &&
                        apod.getUrl() != null &&
                        apod.getMedia_type().equals("image")) {

                    results.add(apod);
                }

            } catch (Exception ignored) {}

            daysChecked++;
        }

        return results;
    }

}
