package com.chen.customer0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 *  @EnableFeignClients  要加上 否则
 * 用    @FeignClient 修饰的类 无法被加载为Bean
 */

@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
public class Customer0Application  {

    public static void main(String[] args) {
        SpringApplication.run(Customer0Application.class, args);
    }

}
