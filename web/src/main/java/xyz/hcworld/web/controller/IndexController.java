package xyz.hcworld.web.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 主页的控制器
 *
 * @ClassName: IndexController
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@RestController
public class IndexController extends BaseController {
    /**
     * 首页
     * @return
     */
    @GetMapping({"", "/", "index"})
    public List<String> index() throws Exception {
        List<String> list = new ArrayList<>(16);;
        list.add("asf");
        list.add("zxc");
        System.out.println(currencyService.selectExistence("m_user",new HashMap<String,Object>(){{
            put("email","zhang@hcworld.xyz");
        }}));
        System.out.println(currencyService.selectExistence("m_user",new HashMap<String,Object>(){{
            put("email","123@hcworld.xyz");
            put("username","随机码");
        }}));
        return list;
    }


}
