package com.github.caijh.framework.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtils {

    private PinYinUtils() {

    }

    /**
     * 获取中文对应的拼音字符串.
     *
     * @param chinese 中文字符串
     * @return 中文的拼音字符串
     */
    public static String toPinyin(String chinese) {
        StringBuilder pinyin = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : newChar) {
            if (c > 128) {
                try {
                    pinyin.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyin.append(c);
            }
        }
        return pinyin.toString();
    }

}
