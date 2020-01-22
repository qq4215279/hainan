package com.gobestsoft.core.util;

import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;

/**
 * create by gutongwei
 * on 2018/6/12 下午3:23
 */
public class EmojiUtil {

    /**
     * 将字符串含有的转义过的emoji符号转换为emoji标签
     * <p>
     * create by gutongwei
     * 2918/6/12
     *
     * @param input
     * @return
     */
    public static String parseToUnicode(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }
        return EmojiParser.parseToUnicode(input);
    }


    /**
     * 字符串中含有的emoji标签转换为普通字符
     * <p>
     * create by gutongwei
     * 2918/6/12
     *
     * @param input
     * @return
     */
    public static String parseToAliases(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }
        return EmojiParser.parseToAliases(input);
    }
}
