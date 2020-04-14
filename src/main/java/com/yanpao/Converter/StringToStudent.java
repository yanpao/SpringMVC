package com.yanpao.Converter;

import com.yanpao.model.Student;
import org.springframework.core.convert.converter.Converter;


public class StringToStudent implements Converter<String, Student> {

    public Student convert(String source) {
        String[] resutls = source.split(":");
        Student student = new Student();

        student.setId(Integer.parseInt(resutls[0]));
        student.setName(resutls[1]);
        student.setAge(Integer.parseInt(resutls[2]));
        return student;
    }

}
