package com.yanpao.Controller;

import com.yanpao.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 测试ModelAndView和@ModelAttribute
 */
@Controller
public class ModelAndViewController {

    /**
     * @RequestAttribute
     * Redirect Attributes
     * Flash Attributes
     * 这三种参数主要用于重定向传参
     * 以后如果要研究模板或者视图时一起研究
     */

    /**
     * 使用ModelAndView进行Student和前端的Form进行绑定
     */
    @RequestMapping(value = "/studentmav", method = RequestMethod.GET)
    public ModelAndView student() {
        /**
         * viewName是对应的jsp页面，modelName对应form的属性modelAttribute，最后一个参数是绑定的对象
         */
        return new ModelAndView("student", "student", new Student());
    }

    /**
     * 使用Model进行Student和前端的Form进行绑定
     */
    @RequestMapping(value = "/studentm", method = RequestMethod.GET)
    public String student(Model model)
    {
        /**
         * s对应form的属性modelAttribute，最后一个参数是绑定的对象
         */
        model.addAttribute("student",new Student());
        return "student";
    }

    /**
     * 使用HashMap进行Student和前端的Form进行绑定
     */
    @RequestMapping(value = "/studenth", method = RequestMethod.GET)
    public String student(HashMap<String, Object> hashMap)
    {
        /**
         * key对应form的属性modelAttribute，最后一个参数是绑定的对象
         */
        hashMap.put("student", new Student());
        return "student";
    }

    /**
     * 使用HttpSession进行Student和前端的Form进行绑定
     */
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String student(HttpSession session)
    {
        /**
         * s对应form的属性modelAttribute，最后一个参数是绑定的对象
         */
        session.setAttribute("student", new Student());
        return "student";
    }

    /**
     * 使用HttpServletRequest进行Student和前端的Form进行绑定
     */
    @RequestMapping(value = "/studentr", method = RequestMethod.GET)
    public String student(HttpServletRequest httpServletRequest)
    {
        /**
         * s对应form的属性modelAttribute，最后一个参数是绑定的对象
         */
        httpServletRequest.setAttribute("student", new Student());
        return "student";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent( Student student, ModelMap model) {//@ModelAttribute("SpringWeb")
        model.addAttribute("name", student.getName());
        model.addAttribute("age", student.getAge());
        model.addAttribute("id", student.getId());
        return "result";
    }

//暂时没用
//    @RequestMapping(value = "/getstudent", method = RequestMethod.GET)
//    @ResponseBody
//    public Student GetStudent(Student student, BindingResult result)
//    {
//        if (result.hasErrors())
//            return new Student();
//        return student;
//    }


}
