package com.yanpao.Controller;

import com.yanpao.model.Student;
import com.yanpao.model.Teacher;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/init")
public class InitBinderController {

    /**
     *@InitBinder 初始化数据绑定
     * 可以将"yyyy-MM-dd"格式的字符串转换成Date类型
     * 可以去除字符串前后的空格
     * @InitBinder("date") 参数的意思是只有这个参数包含的参数才会进行转换
     * 参数为“date”时，只有date会转换，txt不会去除空格
     * Spring 还自带了许多默认的Editor，参见org.springframework.beans.propertyeditors.
     */
    @InitBinder("date")
    public void InitBinder(WebDataBinder binder){

        //注册Editor
        binder.registerCustomEditor(String.class,new StringTrimmerEditor(true));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        //注册Editor
        binder.registerCustomEditor(Date.class,dateEditor);
    }

    /**
     *请求时，会自动使用注册过的DataBinder将"yyyy-MM-dd HH-mm-ss"的字符串转换成Date类型，如果不符合要求会报400错误
     * 会自动去除字符串前后的空格
     */
    @GetMapping(value="/param")
    @ResponseBody
    public Map<String,Object> getInitData(String txt,Date date) throws ParseException {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("txt",txt);
        map.put("date",date);
        return map;
    }

    //region 一次传入两个对象

    /**
     *绑定url参数前缀是studentbind.的参数到Student的实例
     * Student的实例通过@InitBinder的参数必须设置为"student"
     * 并会加入model，key为student
     */
    @InitBinder("student")
    public void initBinderStudent(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("studentbind.");
    }

    /**
     *同上
     */
    @InitBinder("teacher")
    public void initBinderTeacher(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("teacherbind.");
    }

    /**
     * 一次传入两个对象
     * /init/bindstudent2?studentbind.id=1&studentbind.name=www&studentbind.age=20&teacherbind.id=10&teacherbind.name=asdasd&teacherbind.age=40&teacherbind.subject=math
     * @param studentss 不加@ModelAttribute也可以，加的话会严格指定是model中的哪一个key，不加默认是"student"
     * @param teacherss
     * @return
     */
    @GetMapping(value="/bindstudent2")
    @ResponseBody
    public Map<String,Object> BindStudent2(@ModelAttribute("student") Student studentss,
                                           @ModelAttribute("teacher") Teacher teacherss) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("student",studentss);
        map.put("teacher",teacherss);
        return map;
    }

    /**
     *一次传入两个对象有两种方法
     * 1.使用Convert+@ModelAttribute方法，详细见ModelAttributeController.getTwoInstances()
     * 2.使用@InitBinder+@ModelAttribute方法，详见上面的BindStudent2()方法
     * 方法2代码量较少，但是会强制将实例绑定的model中，不能修改key值，会导致一些情况下混乱
     */

    //region 错误方法
    /**
     * 400错误
     * @requestbody 的含义是在当前对象获取整个http请求的body里面的所有数据
     */
    @GetMapping(value="/wrong1")
    @ResponseBody
    public Map<String,Object> getFormatData(@RequestBody Student student, @RequestBody Teacher teacher){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("student",student);
        map.put("teacher",teacher);
        return map;
    }

    /**
     * 可以获取两个对象
     * {
     *     "student": {
     *         "id": 1,
     *         "name": "sunshazi",
     *         "age": 18
     *     },
     *     "teacher": {
     *         "id": 1,
     *         "name": "lihanzhang",
     *         "age": 30,
     *         "subject": "Math"
     *     }
     * }
     * 但是还是需要手动将Object转化成需要的对象
     */
    @GetMapping(value="/wrong2")
    @ResponseBody
    public Map<String,Object> getFormatData(@RequestBody Map<String,Object> all){
        Map<String,Object> map = new HashMap<String, Object>();
        return map;
    }
    //endregion

    //endregion


}
