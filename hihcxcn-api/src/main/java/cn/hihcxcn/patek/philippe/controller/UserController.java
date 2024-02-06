package cn.hihcxcn.patek.philippe.controller;

import cn.hihcxcn.patek.philippe.domain.service.UserService;
import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import cn.hihcxcn.patek.philippe.response.ResultDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * @author yong
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResultDTO<User> create(@RequestBody User user) {
        if (user.getUserId() == null) {
            user.setCreateTime(LocalDateTime.now());
        }
        user.setPassword("123456");
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
        return ResultDTO.valueOfOK(user);
    }

}