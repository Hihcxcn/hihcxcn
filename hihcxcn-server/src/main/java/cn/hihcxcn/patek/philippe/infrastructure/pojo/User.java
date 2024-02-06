package cn.hihcxcn.patek.philippe.infrastructure.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pdai
 */
@Getter
@Setter
@TableName(value = "tbl_user", autoResultMap = true)
public class User implements BaseEntity {

    /**
     * user id.
     */
    private Long userId;

    /**
     * username.
     */
    @JsonProperty("user_name")
    private String userName;

    /**
     * user pwd.
     */
    @JsonIgnore
    private String password;

    /**
     * email.
     */
    private String email;

    /**
     * phoneNumber.
     */
    @JsonProperty("phone_number")
    private long phoneNumber;

    /**
     * description.
     */
    private String description;

    /**
     * create date time.
     */
    private LocalDateTime createTime;

    /**
     * update date time.
     */
    private LocalDateTime updateTime;

    /**
     * join to role table.
     */
    @TableField(exist = false)
    private List<Role> roles;

}