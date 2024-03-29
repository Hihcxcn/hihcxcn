package cn.hihcxcn.patek.philippe.domain.service;

import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author yongdeng on 06/02/2024.
 */
public interface UserService {

    void createUser(User user);
    User getById(Long id);
}
