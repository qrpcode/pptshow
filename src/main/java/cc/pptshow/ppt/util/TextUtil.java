package cc.pptshow.ppt.util;

import cc.pptshow.ppt.domain.TextCount;

public class TextUtil {

    public static TextCount findTextCount(String str) {
        String E1 = "[\u4e00-\u9fa5]";// 中文
        String E2 = "[a-zA-Z]";// 英文
        String E3 = "[0-9]";// 数字
        int chineseCount = 0;
        int englishCount = 0;
        int numberCount = 0;
        String temp;
        for (int i = 0; i < str.length(); i++)
        {
            temp = String.valueOf(str.charAt(i));
            if (temp.matches(E1))
            {
                chineseCount++;
            }
            if (temp.matches(E2))
            {
                englishCount++;
            }
            if (temp.matches(E3))
            {
                numberCount++;
            }
        }

        return new TextCount(chineseCount, englishCount, numberCount,
                (str.length() - (chineseCount + englishCount + numberCount)));
    }

}
