package cn.hihcxcn.patek.philippe.gray;

/**
 * 灰度接口
 * @author yong
 * @date 2024/02/06
 */
public interface GrayReleaseService {

    /**
     * 当根据百分比灰度时，使用此功能 同时支持白名单设置
     * @param userId 用户ID，此功能只支持用户维度的灰度，如果有名单，dataId为${functionName}_whitelist
     * @param functionName 本次功能代号，0到100之间的值，表示灰度范围
     * @return 是否在灰度范围内
     */
    boolean isInGrayScale(Long userId, String functionName);
}