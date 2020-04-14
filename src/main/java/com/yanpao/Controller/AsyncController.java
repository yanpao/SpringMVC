package com.yanpao.Controller;

import com.yanpao.Service.RunnableDemo;
import com.yanpao.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;


@RestController
@RequestMapping("/async")
public class AsyncController {

    //region 多线程测试
    private RunnableDemo runnableDemo1;
    private RunnableDemo runnableDemo2;

    @Autowired
    AsyncController(RunnableDemo runnableDemo1,RunnableDemo runnableDemo2)
    {
        this.runnableDemo1=runnableDemo1;
        this.runnableDemo2=runnableDemo2;
    }

    @GetMapping("/get")
    public void test()
    {
        runnableDemo1.start();
        runnableDemo2.start();
    }
    //endregion

    @GetMapping("/getasync")
    public DeferredResult<String> getStudent(){
        DeferredResult<String> deferredResult = new DeferredResult<String>(10000L);

        System.out.println("调用之前："+System.currentTimeMillis());
        CompletableFuture.supplyAsync(new TestService()::exec).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
        System.out.println("调用之后："+System.currentTimeMillis());

        return deferredResult;
    }

    @GetMapping("/getsync")
    public String getStudent2()
    {
        System.out.println("调用之前："+System.currentTimeMillis());
        TestService testService =   new TestService();
        String result = testService.exec();
        System.out.println("调用之后："+System.currentTimeMillis());
        return result;
    }

    @GetMapping("/getasync2")
    public Callable<String> hello() throws Exception {
        System.out.println("调用之前："+System.currentTimeMillis());
        Callable<String> helloworld = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("调用时："+System.currentTimeMillis());
                return "Hello World!";
            }
        };
        System.out.println("调用之后："+System.currentTimeMillis());
        return helloworld;
    }

    //region 没什么用的手动设置result的办法
    /**
     * 手动调用DeferredResult的setResult，实际没什么用处
     * 先请求"/testDeferredResult"，再打开新标签页访问"/setDeferredResult"
     * /testDeferredResult"会返回值
     */
    private DeferredResult<String> deferredResult = new DeferredResult<>();

    @RequestMapping("/testDeferredResult")
    public DeferredResult<String> testDeferredResult() {
        return deferredResult;
    }

    @RequestMapping("/setDeferredResult")
    public void setDeferredResult() {
        deferredResult.setResult("Test result!");
    }
    //endregion


}
