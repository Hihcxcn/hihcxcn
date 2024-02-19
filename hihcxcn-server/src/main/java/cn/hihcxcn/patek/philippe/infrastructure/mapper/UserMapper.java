package cn.hihcxcn.patek.philippe.infrastructure.mapper;

import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yongdeng on 07/02/2024.
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    void createUser(User user);
    User getById(Long id);
}
