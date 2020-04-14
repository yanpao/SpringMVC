package com.yanpao.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yanpao.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jsonview")
public class JsonViewController {

    @GetMapping("/test")
    public Person Test()
    {
        Person person =new Person(1,"lihanzhang","male","3895","8575");
        return person;
    }

    @GetMapping("/test2")
    @JsonView(Person.WithBaseInfo.class)
    public Person Test2()
    {
        Person person =new Person(1,"lihanzhang","male","3895","8575");
        return person;
    }

    @GetMapping("/test3")
    @JsonView(Person.WithSensitiveInfo.class)
    public Person Test3()
    {
        Person person =new Person(1,"lihanzhang","male","3895","8575");
        return person;
    }

    @GetMapping("/test4")
    @JsonView(Person.WithAllInfo.class)
    public Person Test4()
    {
        Person person =new Person(1,"lihanzhang","male","3895","8575");
        return person;
    }
}
