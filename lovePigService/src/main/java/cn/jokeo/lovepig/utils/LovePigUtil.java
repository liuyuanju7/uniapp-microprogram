package cn.jokeo.lovepig.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * @author joke
 * @version 1.0
 * @date 2021/12/21 22:34
 */
public class LovePigUtil {
    /**
     * 获取临时用户名
     *
     * @return
     */
    public static String genUserName() {
        return Constants.TEMP_USERNAME_PREFIX + RandomUtil.randomString(10);
    }
}
