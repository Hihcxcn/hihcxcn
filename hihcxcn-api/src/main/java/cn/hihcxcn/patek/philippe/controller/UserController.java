package cn.hihcxcn.patek.philippe.controller;

import cn.hihcxcn.patek.philippe.domain.service.UserService;
import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import cn.hihcxcn.patek.philippe.request.UserReq;
import cn.hihcxcn.patek.philippe.response.ResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
    public ResultDTO<User> create(@RequestBody UserReq req) {
        User user = new User();
        user.setUserId(req.getUserId());
        user.setUserName(req.getUserName());
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setDescription(req.getDescription());
        userService.createUser(user);
        return ResultDTO.valueOfOK(userService.getById(user.getUserId()));
    }

    @GetMapping("/getUserInfo")
    public ResultDTO<User> getUserInfo(@RequestParam(value = "user_id") Long userId) {
        User user = userService.getById(userId);
        return ResultDTO.valueOfOK(user);
    }

}