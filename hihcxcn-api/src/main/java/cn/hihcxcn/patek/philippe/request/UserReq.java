package cn.hihcxcn.patek.philippe.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReq implements Serializable {

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

}