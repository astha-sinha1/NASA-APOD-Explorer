package com.astha.nasa_apod.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictScheduler {

    @CacheEvict(value={"todayApod","apodByDate"},allEntries = true)
    @Scheduled(fixedRate=86400000)
    public void clearCacheDaily(){
        System.out.println("Cache cleared automatically after 24 hours");
    }
}
