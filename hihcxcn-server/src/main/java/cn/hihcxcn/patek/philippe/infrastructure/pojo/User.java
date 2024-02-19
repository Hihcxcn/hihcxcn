package cn.hihcxcn.patek.philippe.infrastructure.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yong
 */
@Getter
@Setter
@TableName(value = "tbl_user", autoResultMap = true)
public class User implements BaseEntity {

    @TableId
    private Long userId;

    private String userName;

    private transient String password;

    private String email;

    private long phoneNumber;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Role> roles;

}