package com.yanpao.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * wildcards 通配符
 * 测试URL的匹配，主要是通配符的匹配
 */
@Controller
public class WildcardsController {

    /**
     * 多个路径指向同一个方法
     * @return 无论访问url1","url2","url3哪一个路径，都会返回相同的内容
     */
    @GetMapping(value={"url1","url2","url3"})
    public String returnPara()
    {
        return "you visited url1 or url2 or url3";
    }

    /**
     * 使用？通配符，统配一个字母、数字或者不特殊的符号
     */
    @GetMapping(value = "/match1?")
    public String match1()
    {
        return "all match? you can get";
    }

    /**
     * 这样写没什么用，获取不到参数的
     */
    @GetMapping(value = "/{match2?}")
    public String match(@PathVariable String match2)
    {
        return "you can get "+match2;
    }

    /**
     * 使用*通配符，通配零个、一个或多个字母或者数字
     * @return
     */
    @GetMapping(value = "/match*")
    public String matchstar()
    {
        return "all match* you can get";
    }

    /**
     * 使用**通配符，通配零个或者多个path segment
     * 规则是优先匹配精确的，**最不精确，*较不精确
     * 具体规则详见AntPatternComparator类
     * 可以在配置文件中配置PathMatcher自定义匹配规则（configurePathMatch()）
     */
    @GetMapping(value = "/match/**")
    public String matchtwostats()
    {
        return "all match** you can get";
    }

    @GetMapping(value = "/match/**/match2")
    public String matchtwostats2()
    {
        return "all match**/222 you can get";
    }


}
