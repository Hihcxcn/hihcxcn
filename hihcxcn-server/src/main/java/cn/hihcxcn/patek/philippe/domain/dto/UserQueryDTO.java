package cn.hihcxcn.patek.philippe.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserQueryDTO {

    private String userName;

    private String description;

    private String phoneNumber;

    private String email;

}