package com.github.bingoohuang.mallscannertest.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 身份证号码
 * 1、号码的结构
 * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 2、地址码(前六位数）
 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * 3、出生日期码（第七位至十四位）
 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 4、顺序码（第十五位至十七位）
 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
 * 顺序码的奇数分配给男性，偶数分配给女性。
 * 5、校验码（第十八位数）
 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
 * X 9 8 7 6 5 4 3 2
 */
public class ChinaIdValidator {
    public static String randomGenerate() {
        String areaCode = CODE_ARRAY[RandomUtils.nextInt(0, AREACODE_MAP.size())]
                + StringUtils.leftPad((RandomUtils.nextInt(0, 9998) + 1) + "", 4, "0");

        String birthday = new SimpleDateFormat("yyyyMMdd").format(randomDate());
        String randomCode = String.valueOf(1000 + RandomUtils.nextInt(0, 999)).substring(1);
        String pre = areaCode + birthday + randomCode;
        char verifyCode = ChinaIdValidator.getVerifyCode(pre);

        return pre + verifyCode;
    }

    public static void validate(String chinaId) throws IllegalArgumentException {
        lengthValidate(chinaId);
        areaCodeValidate(chinaId);
        birthdayValidate(chinaId);
        lastLetterValidate(chinaId);
    }

    private static Date randomDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 1, 1);
        long earlierDate = calendar.getTimeInMillis();
        long laterDate = System.currentTimeMillis();

        long chosenDate = RandomUtils.nextLong(earlierDate, laterDate);

        return new Date(chosenDate);
    }

    private static void lastLetterValidate(String chinaId) {
        if (chinaId.length() == 15) return;

        if (chinaId.charAt(17) != getVerifyCode(chinaId))
            throw new IllegalArgumentException(chinaId + "18位身份证编码最后一位校验码不正确");
    }

    private static void birthdayValidate(String chinaId) {
        int year, month, day;
        if (chinaId.length() == 15) {
            year = Integer.parseInt(chinaId.substring(6, 8));
            month = Integer.parseInt(chinaId.substring(8, 10));
            day = Integer.parseInt(chinaId.substring(10, 12));
        } else {
            year = Integer.parseInt(chinaId.substring(6, 10));
            month = Integer.parseInt(chinaId.substring(10, 12));
            day = Integer.parseInt(chinaId.substring(12, 14));
        }

        if (!isValidBirthDay(year, month, day))
            throw new IllegalArgumentException("出生日期不正确");
    }

    private static Map<String, String> AREACODE_MAP = new HashMap<String, String>() {
        {
            put("11", "北京");
            put("12", "天津");
            put("13", "河北");
            put("14", "山西");
            put("15", "内蒙古");
            put("21", "辽宁");
            put("22", "吉林");
            put("23", "黑龙江");
            put("31", "上海");
            put("32", "江苏");
            put("33", "浙江");
            put("34", "安徽");
            put("35", "福建");
            put("36", "江西");
            put("37", "山东");
            put("41", "河南");
            put("42", "湖北");
            put("43", "湖南");
            put("44", "广东");
            put("45", "广西");
            put("46", "海南");
            put("50", "重庆");
            put("51", "四川");
            put("52", "贵州");
            put("53", "云南");
            put("54", "西藏");
            put("61", "陕西");
            put("62", "甘肃");
            put("63", "青海");
            put("64", "宁夏");
            put("65", "新疆");
            put("71", "台湾");
            put("81", "香港");
            put("82", "澳门");
            put("91", "国外");
        }
    };

    private static String[] CODE_ARRAY = AREACODE_MAP.keySet().toArray(new String[0]);

    private static void areaCodeValidate(String chinaId) {
        String areaCode = chinaId.substring(0, 2);
        String areaName = AREACODE_MAP.get(areaCode);
        if (areaName == null)
            throw new IllegalArgumentException(areaCode + "不是有效的行政区划代码");
    }

    private static Pattern oldPattern = Pattern.compile("^[0-9]{15}$");
    private static Pattern newPattern = Pattern.compile("^[0-9]{17}[0-9xX]$$");


    private static void lengthValidate(String chinaId) {
        if (chinaId == null || chinaId.length() != 15 && chinaId.length() != 18
                || !oldPattern.matcher(chinaId).matches()
                && !newPattern.matcher(chinaId).matches())
            throw new IllegalArgumentException(chinaId + "输入不是15位或者18位有效字符串");
    }

    private static boolean isValidBirthDay(int year, int month, int day) {
        if (month > 12) return false;
        if (day > 31) return false;
        // February can't be greater than 29 (leap year calculation comes later)
        if (month == 2 && day > 29) return false;
        // check for months with only 30 days
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) return false;
        }
        // if 2-digit year, use 50 as a pivot date
        if (year < 100) year += 1900;
        if (year > 9999) return false;

        // check for leap year if the month and day is Feb 29
        if ((month == 2) && (day == 29)) {
            int div4 = year % 4;
            int div100 = year % 100;
            int div400 = year % 400;
            // if not divisible by 4, then not a leap year so Feb 29 is invalid
            if (div4 != 0) return false;
            // at this point, year is divisible by 4. So if year is divisible by
            // 100 and not 400, then it's not a leap year so Feb 29 is invalid
            if (div100 == 0 && div400 != 0) return false;
        }

        // date is valid
        Calendar birthDay = new GregorianCalendar(year, month - 1, day);
        long birthDayTimeInMillis = birthDay.getTimeInMillis();
        long currentTimeMillis = System.currentTimeMillis();

        if (birthDayTimeInMillis - currentTimeMillis >= 0
                || birthDayTimeInMillis - new GregorianCalendar(1850, 1, 1).getTimeInMillis() <= 0)
            return false;

        return true;
    }

    // 校验码值
    static char[] validateCode = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    // 表示第i位置上的加权因子
    static int[] weightFactor = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private static char getVerifyCode(String cardId) {
        int sum = 0;
        // 十七位数字本体码加权求和
        for (int i = 0; i < weightFactor.length; i++)
            sum += (cardId.charAt(i) - '0') * weightFactor[i];

        int modValue = sum % 11;
        return validateCode[modValue];
    }


}
