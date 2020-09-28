package com.chen.customer0.controller;

import com.chen.api.Person;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("cnm")
public class FallBack implements  ConsumerApi{


    @Override
    public Object get() {
        return "null";
    }

    @Override
    public Object get(JSONObject o) {
        return o;
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
        p.setName("lalalla");
        return p;
    }

    @Override
    public Map postMap(JSONObject o) {
        return o.toMap();
    }
}
