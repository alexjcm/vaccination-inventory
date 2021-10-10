package com.superapp.firstdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static com.superapp.firstdemo.util.AppConstants.CUSTOM_TIMEZONE;

@SpringBootApplication
public class SuperappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperappApplication.class, args);
    }

    /**
     * While run any application in JVM, JVM will take system default time zone.
     * For example production server is running under PST timezone and spring boot
     * application will start then application will take PST timezone by default.
     */
    @PostConstruct
    public void init() {
        //We want to start application with another timezone then need to set timezone:
        TimeZone.setDefault(TimeZone.getTimeZone(CUSTOM_TIMEZONE));
    }

}
