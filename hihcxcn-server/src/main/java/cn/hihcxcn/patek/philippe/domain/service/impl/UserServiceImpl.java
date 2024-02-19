package cn.hihcxcn.patek.philippe.domain.service.impl;

import cn.hihcxcn.patek.philippe.domain.service.UserService;
import cn.hihcxcn.patek.philippe.infrastructure.dao.UserDao;
import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


/**
 * @author yongdeng on 06/02/2024.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void createUser(User user) {
        user.setPassword("123456");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userDao.createUser(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.selectById(id);
    }


}
