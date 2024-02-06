package cn.hihcxcn.patek.philippe.request;

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

    private static final long serialVersionUID = -1L;

    // 目标账号aliId
    private Long targetAliId;
    // 联系人来源
    private Integer fromSource;
    // 分组ID
    private Long groupId;
    // 展示名
    private String displayName;
    // 备注信息
    private String remarkInfo;

    //联系人状态信息
    private Integer status;

}