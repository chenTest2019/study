package com.chen.configcenter.controller;


import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;


@RequestMapping
@RestController
@RefreshScope
public class Customer {

    @RequestMapping("getGitLabPush")
    public Object queryConfig(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String,Object> p){

        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<String> iterator = parameterMap.keySet().iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next+"AAA:"+ Arrays.toString(parameterMap.get(next)));
        }

        Iterator<String> it = p.keySet().iterator();
        while (it.hasNext()){
            String next = it.next();
            System.out.println(next+":"+p.get(next));
        }
        return parameterMap;
    }
}
