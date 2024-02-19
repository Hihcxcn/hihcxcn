package cn.hihcxcn.patek.philippe.infrastructure.dao;

import cn.hihcxcn.patek.philippe.domain.dto.UserQueryDTO;
import cn.hihcxcn.patek.philippe.infrastructure.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yong
 */

public interface UserDao {

    void createUser(User user);
    User selectById(Long id);
    List<User> findList(UserQueryDTO userQueryDto);
}
