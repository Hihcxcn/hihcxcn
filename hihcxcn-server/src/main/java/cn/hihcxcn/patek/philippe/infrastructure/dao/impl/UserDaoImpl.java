package cn.hihcxcn.patek.philippe.infrastructure.dao.impl;

import cn.hihcxcn.patek.philippe.domain.dto.UserQueryDTO;
import cn.hihcxcn.patek.philippe.infrastructure.dao.UserDao;
import cn.hihcxcn.patek.philippe.infrastructure.mapper.UserMapper;
import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yongdeng on 07/02/2024.
 */
@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> findList(UserQueryDTO userQueryDto) {
        return null;
    }
}
