package com.yanpao.Service;


import java.awt.*;

public class TestService {
    public String exec() {
        try {
            System.out.println("调用时："+System.currentTimeMillis());
            Robot r   =   new   Robot();
            r.delay(5000);
            System.out.println("调用结束："+System.currentTimeMillis());
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return "HelloWorld";
    }

}
