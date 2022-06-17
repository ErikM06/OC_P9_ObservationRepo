package com.mediscreen.observationrepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ObservationRepoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservationRepoApplication.class, args);
    }

}
