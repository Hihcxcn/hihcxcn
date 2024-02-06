package cn.hihcxcn.patek.philippe.domain.service.impl;

import cn.hihcxcn.patek.philippe.domain.service.UserService;
import cn.hihcxcn.patek.philippe.infrastructure.dao.UserDao;
import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * @author yongdeng on 06/02/2024.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {


    @Override
    public User getById(Long id) {
        return null;
    }


}
