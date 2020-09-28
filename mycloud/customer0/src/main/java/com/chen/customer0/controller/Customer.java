package com.chen.customer0.controller;


import com.chen.api.Person;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping
@RestController
@RefreshScope
public class Customer {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    //@Autowired
    //private EurekaDiscoveryClient eurekaDiscoveryClient;
    @Autowired
    private DiscoveryClient discoveryClient;

    /*@Autowired
    private RestTemplate template;*/


    @Resource(type = ConsumerApi.class)
    @Autowired
    ConsumerApi consumerApi;
    /*@Autowired
    private UserApi userApi;*/
    @Value("${p}")
    private String str;
    @Value("${aa}")
    private String aa;
    @Value("${spring.cloud.config.profile}")
    private String configProfile;


    @RequestMapping("a")
    public Object a() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
               // System.out.println("service:" + new JSONObject(instance));
            }

        }

        /*ServiceInstance serviceInstance = loadBalancerClient.choose("EurekaProvide");
        System.out.println(serviceInstance.getUri() + "/a");
        Object forObject = restTemplate.getForObject(serviceInstance.getUri() +"/a" , Object.class);
        */

        //Object forObject = template.getForObject("http://EUREKAPROVIDE/a", Object.class);
        //Object o = consumerApi.get();
        JSONObject jsonObject = new JSONObject();
        //Object o1 = consumerApi.get();
        jsonObject.put("customer","customer");
        //JSONObject o2 = consumerApi.getJson(jsonObject);
        Person p=new Person();
        p.setName("customer");
        p.setId(345);
        //Map o3 = consumerApi.postMap(jsonObject);

        Person o4 = consumerApi.getPerson(p);
        System.out.println("customer");

        return o4;
    }

    @RequestMapping("queryConfig")
    public Object queryConfig(){
        Map<String,String> map=new HashMap<>();
        map.put("p",str);
        map.put("aa",aa);
        map.put("configProfile",configProfile);

        return map;
    }
}
