package com.yanpao.Controller;

import com.yanpao.model.Teacher;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 测试PathVariable、MatrixVariabble传参
 */
@RestController
@RequestMapping("school/{schoolname}")
public class PathVariableController {

    //region @PathVariable
    /**
     * 简单的url获取参数
     * @param schoolname 从类上的{schoolname}获取
     * @param teachername 从方法的{teachername}获取
     */
    @GetMapping("teacher/{teachername}")
    public String getTeacherName(@PathVariable String schoolname,@PathVariable String teachername)
    {
        return schoolname+"--"+teachername;
    }

    /**
     * 可以只获取到school一层
     * @param schoolname
     */
    @GetMapping
    public String getSchoolName(@PathVariable String schoolname)
    {
        return schoolname;
    }

    /**
     * 测试从url中获取复杂对象
     * @param teacher 通过StringToStudent类进行转换
     * @return
     */
    @PostMapping(value = "addteacher",consumes = "application/json",produces = "application/json")
    public Teacher AddTeacher(@RequestBody Teacher teacher)
    {
        return teacher;
    }

    /**
     * 使用正则表达式匹配参数
     * @return 不符合正则表达的会返回404错误
     */
    @GetMapping("/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
    public String handle(@PathVariable String name ,@PathVariable String version, @PathVariable String ext)
    {
        return name+"-"+version+"-"+ext;
    }

    //endregion

    //region @MatrixVariable

    /**
     * 测试@MatrixVariable
     * 可以通过/student/sss;q=2,3;x=sssss
     * q只能取得2
     * @MatrixVariable 标注的参数必须在url的{}中，否则会报400错误
     */
    @GetMapping("/student/{stuId}")
    public String findStudent(@PathVariable String stuId, @MatrixVariable int q, @MatrixVariable String x) {
        return stuId+q+x;
    }

    /**
     * /school/schoolname;q=222/class/classid;q=xxx4
     * 使用@MatrixVariable时，参数可以重复
     * 指定参数名称（name）和所在位置（pathVar）(就是参数写在哪一个url节点上，这个节点必须是@PathVariable节点，普通节点不行)
     * 用处：可以用于输入不同对象的查询条件，比如学校和班级的查询条件
     */
    @GetMapping("/class/{classid}")
    public String findPet(@PathVariable String schoolname,
                          @MatrixVariable(name="q",pathVar = "schoolname") int q,
                          @MatrixVariable(name = "q",pathVar = "classid") String x) {
        return schoolname+q+x;
    }

    /**
     * 使用@MatrixVariable，可以指定不是必要的并设定默认值
     * 如果不指定默认值，url中没有参数时，该值会为null
     */
    @GetMapping("/subject/{subjectid}")
    public String FindChild(@MatrixVariable(required = false,defaultValue = "1")Integer q )
    {
        return String.valueOf(q);
    }

    /**
     * 使用MultiValueMap收集所有参数
     * @param matrixVars 不指定pathVar，会收集所有的参数，会导致混乱
     * @param petMatrixVars 指定pathVar，只收集指定的参数
     */
    @GetMapping("/owners/{ownerId}/pets/{petId}")
    public String findPet(
            @MatrixVariable MultiValueMap<String, String> matrixVars,
            @MatrixVariable(pathVar="petId") MultiValueMap<String, String> petMatrixVars)
    {
        return matrixVars.toString()+"/r/n"+petMatrixVars.toString();
        // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
        // petMatrixVars: ["q" : 22, "s" : 23]
    }

    //endregion

    //region @RequestHeader

    /**
     * 获取Http Header作为参数，使用@RequestHeader注释，可以指定某一个header项
     * 如果指定的项不存在会报400错误
     */
    @GetMapping("/header1")
    public String Header1(
            @RequestHeader("Accept-Encoding") String encoding) {
        return encoding;
    }

    /**
     * 获取全部Http Header参数，以下三种方法都可以
     */
    @GetMapping("/header2")
    public String Header2(
            @RequestHeader Map<String, String> Headers) {
        return Headers.toString();
    }

    @GetMapping("/header3")
    public String Header3(
            @RequestHeader MultiValueMap<String,String> Headers) {
        return Headers.toString();
    }

    @GetMapping("/header4")
    public String Header4(
            @RequestHeader HttpHeaders httpHeaders) {
        return httpHeaders.toString();
    }
    //endregion

    //region @CookieValue
    /**
     * 先空着，最后一起研究Cookie和Session
     * @CookieValue
     * @SessionAttributes
     * @SessionAttribute
     */
    //endregion

}
