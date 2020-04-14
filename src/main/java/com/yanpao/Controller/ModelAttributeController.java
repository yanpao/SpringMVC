package com.yanpao.Controller;

import com.yanpao.model.Student;
import com.yanpao.model.Teacher;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试ModelAttribute注释
 */
@RestController
public class ModelAttributeController {

    @ModelAttribute("countries")
    public List<String> getCountries() {
        List <String> countries = new ArrayList< >();
        countries.add("UK");
        countries.add("USA");
        countries.add("Japan");
        return countries;
    }

    @ModelAttribute
    public void  getStates(final Model model) {
        List <String> states = new ArrayList < > ();
        states.add("California");
        states.add("New York");
        states.add("Alaska");
        states.add("Colorado");

        model.addAttribute("states", states);
    }

    @GetMapping("/home")
    public String home(@ModelAttribute("countries") List < String > countries,Model model) {
        countries.add("Australia");
        countries.add("Canada");
        return model.toString();
    }

    /**
     * @ModelAttribute 最简单的使用方法之一，获取url中节点中获取参数，转换成对象，需要定义Convert
     * localhost:8080/bindstudent/1:www:22
     * 通过参数初始化Student，通过转换方法，1:www:22自动转换成student
     * @GetMapping("/bindstudent/{student0}") 与@ModelAttribute("student0")的参数一定要保持一致
     * @param student 如果model中有了student0，会优先使用model中的，没有的话会通过参数创建
     * @param result 绑定结果，可以发现是否绑定错误
     */
    @GetMapping("/bindstudent/{student0}")
    public Student BindStudent(@ModelAttribute("student0") Student student, BindingResult result)
    {
        if (result.hasErrors())
            return new Student();
        return student;
    }

    /**
     * @ModelAttribute 最简单的使用方法之二，获取url参数，转换成对象，需要定义Convert
     * localhost:8080/bindstudent1?student01=1:www:22
     * 参数名称student01与@ModelAttribute("student01")参数保持一致
     * 如果model中有了student0，会优先使用model中的，没有的话会通过参数创建
     */
    @GetMapping(value="/bindstudent1")
    @ResponseBody
    public Map<String,Object> BindStudent1(@ModelAttribute("student01") Student student) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("student",student);
        return map;
    }

    /**
     * 一次从前台参数中绑定两个对象
     * /bindtwo?teacher=2:liu:30:Math&student02=1:www:22
     *@ModelAttribute 依旧会优先用model中已存在的对象，再从参数中获取
     */
    @GetMapping(value="/bindtwo")
    @ResponseBody
    public Map<String,Object> getTwoInstances(@ModelAttribute("student02") Student student,
                                              @ModelAttribute("teacher") Teacher teacher) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("student",student);
        map.put("teacher",teacher);
        return map;
    }

    /**
     * 在函数上使用 @ModelAttribute，getStudent()在调用前，会先CreateStudent()方法
     * CreateStudent()会初始化一个Student的实例
     * 并将实例通过键值对的方式放入Model中
     * 因为没有显式设置key值，Spring MVC会默认设置key为student
     * getStudent()运行时，通过@ModelAttribute("student")（key值）可以获取上一步初始化的实例
     */
    @ModelAttribute
    public Student CreateStudent()
    {
        Student student=new Student();
        student.setAge(30);
        student.setId(3);
        student.setName("yanpao");
        return student;
    }

    @GetMapping("/getstudent")
    public String getStudent(@ModelAttribute("student")Student student )
    {
        return student.getName()+student.getId()+student.getAge();
    }

    /**
     * 与上例相似，不同的是通过调用Model，显式设置key值为student1
     */
    @ModelAttribute
    public void CreateStudent1(Model model)
    {
        Student student=new Student();
        student.setAge(30);
        student.setId(1);
        student.setName("lihanzhang");
        model.addAttribute("student1", student);
    }

    @GetMapping("/getstudent1")
    public String getStudent1(@ModelAttribute("student1")Student student )
    {
        return student.getName()+student.getId()+student.getAge();
    }

    /**
     * 与上面的例子相似，只不过CreateStudent2()通过@ModelAttribute("student2")指定了
     * Model中key值，return Student的实例到Model中
     */
    @ModelAttribute("student2")
    public Student CreateStudent2()
    {
        Student student=new Student();
        student.setAge(26);
        student.setId(2);
        student.setName("sunshazi");
        return student;
    }

    @GetMapping("/getstudent2")
    public String getStudent2(@ModelAttribute("student2")Student student )
    {
        return student.getName()+student.getId()+student.getAge();
    }

    /**
     * 若是Model中没有找到student3对应的value，会调用Student的构造函数初始化一个Student
     */
    @GetMapping("/getstudent3")
    public String getStudent3(@ModelAttribute("student3")Student student)
    {
        return student.getName()+student.getId()+student.getAge();
    }

    /**
     * @ModelAttribute 与 @RequestMapping配合使用
     * @param name 姓名，通过请求"/getstudent4?name=lihanzhang"时带的参数赋值，可以加上@RequestParam，表示一定需要这个参数
     * 在访问"/getstudent4"时，先调用CreateStudent4()获取参数值并获取实例
     */
    @ModelAttribute
    public void CreateStudent4(String name, Model model) {
        Student student = new Student();
        student.setName(name);
        model.addAttribute("student4", student);
    }

    @GetMapping(value = "/getstudent4")
    public String getStudent4(Model model) {
        Student student = (Student) model.getAttribute("student4");
        return student.getName()+student.getId()+student.getAge();
    }

    /**
     * 暂时没搞明白怎么用
     */
    @GetMapping(value = "/createstudent5")
    @ModelAttribute("student5")
    public Student CreateStudent5() {
        Student student=new Student();
        student.setAge(26);
        student.setId(5);
        student.setName("sunrujie");
        return student;
    }

    /**
     * 返回所有model的所有内容
     * 注意：一个Controller对应一个Model
     * 注意：所有标注@ModelAttribute的方法，都会在别的没有标注的方法被调用前调用
     * 注意：并且会重新初始化对象，每次取得的对象实际是不一样的
     */
    @GetMapping(value = "/model/getallmodel")
    public String getallmodel(Model model)
    {
        return model.toString();
    }


}
