package com.chen.api;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface UserApi {
    @RequestMapping("/ab")
    Object get();
     @RequestMapping("test")
    Object get(@RequestParam JSONObject o);

    /**
     * JSONObject返回值  返回客户端接收不到值
     */
    @RequestMapping(value = "gg",method = RequestMethod.POST)
    JSONObject getJson(@RequestParam JSONObject o);
    /**
     * JSONObject返回值  返回客户端接收不到值
     */
    @RequestMapping(value = "getJsonByBody",method = RequestMethod.POST)
    JSONObject getJsonByBody(@RequestBody JSONObject o);

    @RequestMapping(value = "getPerson",method = RequestMethod.POST)
    Person getPerson(@RequestBody Person p);

    @RequestMapping(value = "postMap",method = RequestMethod.POST)
    Map postMap(@RequestParam JSONObject o);
}
