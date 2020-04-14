package com.yanpao.Controller;

import com.yanpao.model.Student;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 跨域配置
 */
@RestController
@RequestMapping("/cors")
@CrossOrigin("https://domain2.com")
public class CorsController {

    @CrossOrigin(value = "http://localhost",methods = RequestMethod.GET)
    @GetMapping("/get")
    public Student getStudent()
    {
        Student student=new Student();
        student.setAge(30);
        student.setId(3);
        student.setName("yanpao");
        return student;
    }

    /**
     * 使用ResponseEntity设置缓存
     * 在浏览器中有效，在postman中无效
     */
    @CrossOrigin(value = "http://localhost",methods = RequestMethod.GET)
    @GetMapping("/get2")
    public ResponseEntity<Student> getStudent2()
    {
        Student student=new Student();
        student.setAge(30);
        student.setId(3);
        student.setName("yanpao");
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .eTag("paopao") // lastModified is also available
                .body(student);
    }


}
