package com.yanpao.Controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用于测试自定义的HandleMapping，参考WebConfig的setHandlerMapping()
 */
@Component
public class UserHandler {

    @ResponseBody
    public String getHandler(Long i){return "test Mapping Handler";}

}