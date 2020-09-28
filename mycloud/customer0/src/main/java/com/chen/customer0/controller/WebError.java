package com.chen.customer0.controller;

import com.chen.api.Person;
import feign.hystrix.FallbackFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class WebError  implements FallbackFactory<ConsumerApi> {



    @Resource(type=ConsumerApi.class)
    @Autowired
    ConsumerApi consumerApi;
    @Override
    public ConsumerApi create(Throwable cause) {

        ConsumerApi consumerApi = new ConsumerApi(){

            @Override
            public Object get() {
                return "null";
            }

            @Override
            public Object get(JSONObject o) {
                return "null";
            }

            @Override
            public JSONObject getJson(JSONObject o) {

                return o;
            }

            @Override
            public JSONObject getJsonByBody(JSONObject o) {
                return o;
            }

            @Override
            public Person getPerson(Person p) {
                p.setName("WebError");
                return p;
            }

            @Override
            public Map postMap(JSONObject o) {
                return o.toMap();
            }
        };
        System.out.println(cause);
        System.out.println(new JSONObject(cause));
        return consumerApi;
    }
}


