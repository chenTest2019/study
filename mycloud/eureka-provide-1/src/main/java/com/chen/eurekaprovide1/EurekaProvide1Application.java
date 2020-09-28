package com.chen.eurekaprovide1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaProvide1Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaProvide1Application.class, args);
    }

}
