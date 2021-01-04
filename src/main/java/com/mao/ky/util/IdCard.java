package com.mao.ky.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证信息识别
 * @author : create by zongx at 2020/10/22 15:19
 */
public class IdCard {

    private static final int[] POWER = new int[] {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private static final Map<String, String> CITY_CODE = new HashMap<>();

    static {
        CITY_CODE.put("11", "北京");
        CITY_CODE.put("12", "天津");
        CITY_CODE.put("13", "河北");
        CITY_CODE.put("14", "山西");
        CITY_CODE.put("15", "内蒙古");
        CITY_CODE.put("21", "辽宁");
        CITY_CODE.put("22", "吉林");
        CITY_CODE.put("23", "黑龙江");
        CITY_CODE.put("31", "上海");
        CITY_CODE.put("32", "江苏");
        CITY_CODE.put("33", "浙江");
        CITY_CODE.put("34", "安徽");
        CITY_CODE.put("35", "福建");
        CITY_CODE.put("36", "江西");
        CITY_CODE.put("37", "山东");
        CITY_CODE.put("41", "河南");
        CITY_CODE.put("42", "湖北");
        CITY_CODE.put("43", "湖南");
        CITY_CODE.put("44", "广东");
        CITY_CODE.put("45", "广西");
        CITY_CODE.put("46", "海南");
        CITY_CODE.put("50", "重庆");
        CITY_CODE.put("51", "四川");
        CITY_CODE.put("52", "贵州");
        CITY_CODE.put("53", "云南");
        CITY_CODE.put("54", "西藏");
        CITY_CODE.put("61", "陕西");
        CITY_CODE.put("62", "甘肃");
        CITY_CODE.put("63", "青海");
        CITY_CODE.put("64", "宁夏");
        CITY_CODE.put("65", "新疆");
        CITY_CODE.put("71", "台湾");
        CITY_CODE.put("81", "香港");
        CITY_CODE.put("82", "澳门");
        CITY_CODE.put("91", "国外");
    }

    public static boolean isIdcard(String idcard) {
        if (SU.isEmpty(idcard))
            return false;
        if (idcard.length() == 15) {
            idcard = to18Idcard(idcard);
            if (null == idcard)
                return false;
        }
        return is18Idcard(idcard);
    }

    /**
     * 获取身份证信息之前先验证正确性！
     */
    public static Map<String, Object> idcardInfo(String idcard, boolean checked) {
        Map<String, Object> map = new HashMap<>();
        map.put("idcard", idcard);
        boolean right = true;
        if (!checked) {
            right = isIdcard(idcard);
        }
        map.put("right", right);
        if (right) {
            map.put("province", CITY_CODE.get(idcard.substring(0,2)));
            int i = Integer.parseInt(idcard.substring(16, 17));
            map.put("sex", i%2 != 0 ? "男" : "女");
            LocalDate birthday = SU.getLocalData(idcard.substring(6, 14), "yyyyMMdd");
            if (null != birthday) {
                map.put("birthday", birthday.toString());
                LocalDate now = LocalDate.now();
                long age = birthday.until(now, ChronoUnit.YEARS);
                map.put("age", age);
            }
        }
        return map;
    }

    /**
     * 将15位的身份证转成18位身份证
     */
    private static String to18Idcard(String idcard) {
        if (SU.isDigit(idcard)) {
            String birthday = "19" + idcard.substring(6,12);
            LocalDate birthDate = SU.getLocalData(birthday, "yyyyMMdd");
            if (null == birthDate)
                return null;
            int year = birthDate.getYear();
            String res = idcard.substring(0,6) + year + idcard.substring(8);
            char[] c = res.toCharArray();
            int[] bit = convertCharToInt(c);
            int sum17 = getPowerSum(bit);
            String checkCode = getCheckCodeBySum(sum17);
            if (null == checkCode)
                return null;
            res += checkCode;
            return res;
        }
        return null;
    }

    private static boolean is18Idcard(String idcard) {
        if (idcard.length() != 18)
            return false;
        //前17位
        String all17 = idcard.substring(0,17);
        String sum18 = idcard.substring(17,18);
        if (!SU.isDigit(all17))
            return false;
        char[] c = all17.toCharArray();
        int[] bit = convertCharToInt(c);
        int sum17 = getPowerSum(bit);
        String checkCode = getCheckCodeBySum(sum17);
        if (null == checkCode)
            return false;
        return sum18.equalsIgnoreCase(checkCode);
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     */
    private static int getPowerSum(int[] bit) {
        int sum = 0;
        if (POWER.length != bit.length)
            return sum;
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < POWER.length; j++) {
                if (i == j) {
                    sum += bit[i] * POWER[j];
                    break;
                }
            }
        }
        return sum;
    }

    /**
     * 将和值与11取模得到余数进行校验码判断
     */
    private static String getCheckCodeBySum(int sum17) {
        switch (sum17 % 11) {
            case 10:
                return "2";
            case 9:
                return "3";
            case 8:
                return "4";
            case 7:
                return "5";
            case 6:
                return "6";
            case 5:
                return "7";
            case 4:
                return "8";
            case 3:
                return "9";
            case 2:
                return "x";
            case 1:
                return "0";
            case 0:
                return "1";
            default:
                return null;
        }
    }

    /**
     * 将字符数组转为整型数组
     */
    private static int[] convertCharToInt(char[] c) {
        int[] a = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            a[i] = Integer.parseInt(String.valueOf(c[i]));
        }
        return a;
    }

}
