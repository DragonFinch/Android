package com.iyoyogo.android.utils.util;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 爱生活，爱代码
 * 创建于：2018/11/12 14:21
 * 作 者：T
 * 微信：704003376
 */
public class PinYinUtils {

    public static String getPinYin(String str) {
        String pinyin = "";
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        if (!TextUtils.isEmpty(str)) {
            char[] mCharArr = str.toCharArray();

            if (mCharArr != null && mCharArr.length > 0) {

                for (int i = 0; i < mCharArr.length; i++) {

                    char c = mCharArr[i];

                    //  TODO UTF-8格式下  1个汉字==2个字节   1个字节(-128----127)
                    if (c > 127) {
                        //不一定是汉字，也不一定是字母
                        try {
                            String[] strArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                            pinyin += strArr[0];

                        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                            badHanyuPinyinOutputFormatCombination.printStackTrace();
                            pinyin += c;
                        }
                    } else {
                        //一定不是汉字,有可能是字母或者标点或者数字
                        pinyin += c;
                    }
                }

            }
        }

        return pinyin;
    }

}
