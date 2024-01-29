package cn.hihcxcn.patek.philippe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yongdeng on 29/01/2024.
 */
@RestController
@RequestMapping("/welcome")
public class IndexController {

    @RequestMapping("/index.do")
    public String index() {
        return "Welcome to Patek Philippe";
    }

}
