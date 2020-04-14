package com.yanpao.Converter;

import com.yanpao.model.Teacher;
import org.springframework.core.convert.converter.Converter;

public class StringToTeacher implements Converter<String, Teacher> {

    public Teacher convert(String source) {
        String[] resutls = source.split(":");
        Teacher teacher = new Teacher();

        teacher.setId(Integer.parseInt(resutls[0]));
        teacher.setName(resutls[1]);
        teacher.setAge(Integer.parseInt(resutls[2]));
        teacher.setSubject(resutls[3]);
        return teacher;
    }
}
