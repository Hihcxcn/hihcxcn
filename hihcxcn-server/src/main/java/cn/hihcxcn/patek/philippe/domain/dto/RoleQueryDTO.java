package cn.hihcxcn.patek.philippe.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yongdeng on 06/02/2024.
 */
@Data
@NoArgsConstructor
public class RoleQueryDTO {

    private String name;

    private String description;

    private String roleKey;

}
