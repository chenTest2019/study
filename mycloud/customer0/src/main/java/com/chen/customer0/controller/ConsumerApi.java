package com.chen.customer0.controller;

import com.chen.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 *
 *
 * fallbackFactory = WebError.class,fallback = FallBack.class 加上这2个 可以正常工作
 *
 * 只加上这个 fallback = FallBack.class  可以正常工作
 *
 * 只加上这个  fallbackFactory = WebError.class  出现 java.net.UnknownHostException: eureka-provide?
 */
@FeignClient(name = "eureka-provide",fallback = FallBack.class )
public interface ConsumerApi extends UserApi {

}
