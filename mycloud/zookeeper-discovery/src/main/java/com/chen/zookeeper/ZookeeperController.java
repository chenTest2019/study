package com.chen.zookeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供Rest Api，根据实例名称获取注册服务列表
 *
 * @author sam.liu
 * @create 2017-11-21 20:47
 * @contact 563750241
 * @email lsc19890723@163.com
 */
@RestController
@RequestMapping("/zookeeper")
public class ZookeeperController {


    @Value("${p}")
    private String str;
    @Value("${aa}")
    private String aa;
    @Value("${spring.cloud.config.profile}")
    private String configProfile;

    @Value("${spring.application.name}")
    private String instanceName;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public ZookeeperController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping
    public String hello() {
        return "Hello,Zookeeper.";
    }

    @GetMapping("/services")
    public List<String> serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances(instanceName);
        List<String> services = new ArrayList<>();
        if (list != null && list.size() > 0 ) {
            list.forEach(serviceInstance -> {
                services.add(serviceInstance.getUri().toString());
            });
        }
        return services;
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