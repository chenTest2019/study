package com.chen.eurekaprovide0.controller;

import com.chen.api.Person;
import com.chen.api.UserApi;
import org.json.JSONObject;
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

    /*@RequestMapping("a")
    public Object a(){
        //loadBalancerClient.
        Map<String,String> map=new HashMap<>();
        map.put("aa","eureka-provide-0");
        return map;
    }*/

    @Override
    public Object get() {
        Map<String,String> map=new HashMap<>();
        map.put("aa","eureka-provide-0");
        return map;
    }

    @Override
    public Object get(@RequestParam JSONObject o) {
        System.out.println("eureka-provide-0:"+o);
        o.put("eureka-provide-0","eureka-provide-0");
        return o;
    }

    @Override
    public JSONObject getJson(@RequestParam JSONObject o) {
        System.out.println("eureka-provide-0:"+o);
        o.put("eureka-provide-0","eureka-provide-0");
        return o;
    }

    @Override
    public JSONObject getJsonByBody(JSONObject o) {
        System.out.println("eureka-provide-0:"+o);
        o.put("eureka-provide-0","eureka-provide-0");
        return o;
    }

    @Override
    public Person getPerson(Person p) {
        int i = atomicInteger.incrementAndGet();
        System.out.println("eureka-provide-0第"+i+"次调用");
        p.setName("eureka-provide-0");
        p.setId(i);
        //int a=1/0;
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public Map postMap(JSONObject o) {
        System.out.println("eureka-provide-0:"+o);
        o.put("eureka-provide-0","eureka-provide-0");
        return o.toMap();
    }


}
