package com.tma.deviceeverycountry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeviceEveryCountryApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DeviceEveryCountryApplication.class, args);
    }

    @Autowired
    private ApplicationRunner runner;

    @Override
    public void run(String... args) throws Exception {
        runner.run();
    }
}
