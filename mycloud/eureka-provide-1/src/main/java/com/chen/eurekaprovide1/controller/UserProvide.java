package com.chen.eurekaprovide1.controller;


import com.chen.api.Person;
import com.chen.api.UserApi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping
@RestController
public class UserProvide implements UserApi {

    private AtomicInteger atomicInteger=new AtomicInteger();
    //@Autowired
    //private LoadBalancerClient loadBalancerClient;
    @Autowired
    private HealthStatusService healthStatusService;

    /*@RequestMapping("a")
    public Object a() {
        //loadBalancerClient.
        Map<String, String> map = new HashMap<>();
        map.put("aa", "eureka-provide-1");
        return map;
    }*/

    @GetMapping("/health")
    public String health(@RequestParam("status") Boolean status) {

        healthStatusService.setStatus(status);
        return healthStatusService.getStatus();
    }

    @Override
    public Object get() {
        Map<String, String> map = new HashMap<>();
        map.put("aa", "eureka-provide-1");
        return map;
    }

    @Override
    public Object get(@RequestParam JSONObject o) {
        System.out.println("eureka-provide-1:"+o);
        o.put("eureka-provide-1","eureka-provide-1");
        return o;
    }

    @Override
    public JSONObject getJson(@RequestParam JSONObject o) {
        System.out.println("eureka-provide-1:"+o);
        o.put("eureka-provide-1","eureka-provide-1");
        return o;
    }

    @Override
    public JSONObject getJsonByBody(JSONObject o) {
        System.out.println("eureka-provide-1:"+o);
        o.put("eureka-provide-1","eureka-provide-1");
        return o;
    }

    @Override
    public Person getPerson(Person p) {
        int i = atomicInteger.incrementAndGet();
        System.out.println("eureka-provide-1第"+i+"次调用");
        p.setName("eureka-provide-1");
        p.setId(i);
        return p;
    }

    @Override
    public Map postMap(JSONObject o) {

        System.out.println("eureka-provide-1:"+o);
        o.put("eureka-provide-1","eureka-provide-1");
        return o.toMap();
    }


}
