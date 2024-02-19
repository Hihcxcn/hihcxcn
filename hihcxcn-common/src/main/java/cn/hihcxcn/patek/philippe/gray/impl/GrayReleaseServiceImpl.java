package cn.hihcxcn.patek.philippe.gray.impl;

import cn.hihcxcn.patek.philippe.gray.GrayReleaseService;
import cn.hihcxcn.patek.philippe.nacos.NacosConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 灰度接口实现
 * @author  lousp  @create  2022/3/4 14:23
 **/
@Component
@Slf4j
public class GrayReleaseServiceImpl implements GrayReleaseService {

    /**
     * 默认分组
     **/
    private static final String GRAY_GROUP = "grayscale";
    private static final String WHITELIST = "_whitelist";
    /**
     * 最小百分比
     **/
    private static final Long MIN_PERCENT = 0L;
    /**
     * 最大百分比
     **/
    private static final Long MAX_PERCENT = 100L;
    /**
     * 灰度方法列表
     **/
    private static final List<String> functionNameList = new ArrayList<>();

    @Override
    public boolean isInGrayScale(Long userId, String functionName) {
        if (userId == null || StringUtils.isBlank(functionName)) {
            return false;
        }
        try {
            return dealGrayScale(userId, functionName);
        } catch (Exception e) {
            log.error("isInGrayScale error,userId={}|functionName={}", userId, functionName, e);
            return false;
        }
    }

    /**
     * 执行灰度处理
     *
     * @param userId    用户ID，此功能只支持公司维度的灰度，如果有名单，dataId为${functionName}_whitelist的配置。
     * @param functionName  本次功能代号，需要在nacos配置groupId为grayscale，dataId为functionName的配置项
     * @return 是否在灰度范围内
     */
    private boolean dealGrayScale(Long userId, String functionName) {
        if (userId == null || StringUtils.isBlank(functionName)) {
            return false;
        }
        try {
            if (!functionNameList.contains(functionName)) {
                registerFunction(functionName);
                functionNameList.add(functionName);
            }
            Boolean whiteGray = grayByWhiteList(userId, functionName);
            if (whiteGray != null) {
                return whiteGray;
            }
            Boolean percentGray = grayByPercent(userId, functionName);
            if (percentGray != null) {
                return percentGray;
            }
        } catch (Exception e) {
            log.error("dealGrayScale err,userId={}|functionName={}", userId, functionName, e);
        }
        return false;
    }

    /**
     * 根据白名单灰度
     *
     * @param userId   用户ID
     * @param functionName 方法名
     * @return 是否在白名单中
     */
    private Boolean grayByWhiteList(Long userId, String functionName) {
        try {
            String whiteList = NacosConfig.getData(functionName + WHITELIST, GRAY_GROUP);
            if (!StringUtils.isEmpty(whiteList)) {
                String whiteListStr = "," + whiteList + ",";
                if (whiteListStr.contains(userId.toString())) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("grayByWhiteList err,userId={}|functionName={}", userId, functionName, e);
        }
        return null;
    }

    /**
     * 根据百分比灰度
     *
     * @param userId   用户ID
     * @param functionName  方法名
     * @return  是否在百分比范围内
     */
    private Boolean grayByPercent(Long userId, String functionName) {
        try {
            String grayValueStr = NacosConfig.getData(functionName, GRAY_GROUP);
            if (StringUtils.isBlank(grayValueStr)) {
                return false;
            }
            Long grayValue = Long.parseLong(grayValueStr);
            if (grayValue == null) {
                return false;
            } else if (grayValue < MIN_PERCENT) {
                return false;
            } else if (grayValue >= MAX_PERCENT) {
                return true;
            } else {
                long userGroup = userId % 100 + 1;
                if (userGroup <= grayValue) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("grayByPercent error,userId={}|functionName={}", userId, functionName, e);
        }
        return null;
    }

    /**
     * 获取Nacos配置数据
     *
     * @param functionName 方法名
     */
    private static void registerFunction(String functionName) {
        try {
            NacosConfig.registerDynamicConfig(functionName, GRAY_GROUP);
            NacosConfig.registerDynamicConfig(functionName + WHITELIST, GRAY_GROUP);
        } catch (Exception e) {
            log.error("NacosConfig.registerDynamicConfig error,functionName={}", functionName, e);
        }
    }
}