package com.mao.ky.util;

import com.mao.ky.entity.log.req.ReqData;
import com.mao.ky.entity.log.req.ReqType;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 字符串工具类
 * @author : create by zongx at 2020/10/15 17:12
 */
public class SU {

    private static final String SELF_IP6 = "0:0:0:0:0:0:0:1";
    private static final String SELF_IP4 = "127.0.0.1";
    private static final String FORWARDED_FOR = "x-forwarded-for";
    private static final String PROXY_CLIENT = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT = "WL-Proxy-Client-IP";
    private static final String UNKNOWN = "unknown";
    private static final String RANDOM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 获取随机字符串
     * @param len 字符串长度
     * @return String
     */
    public static String randomStr(int len) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        if (len <= 0)
            return null;
        for (int i = 0; i < len; i++) {
            sb.append(RANDOM.charAt(random.nextInt(RANDOM.length())));
        }
        return sb.toString();
    }

    /**
     * 判断Long型id是否为正数
     */
    public static boolean isZS(Long id) {
        return null != id && id > 0;
    }

    /**
     * 判断是否全是数字
     */
    public static boolean isDigit(String str) {
        return str.matches("^[0-9]*$");
    }

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return Boolean
     */
    public static boolean isEmpty(String str) {
        return null == str || str.length() <= 0;
    }

    /**
     * 判断字符串是否不为空
     * @param str 字符串
     * @return Boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 打印成功标识
     */
    public static void pfSuccess() {
        System.out.println(" ___ _   _  ___ ___ ___  ___ ___ \n" +
                "/ __| | | |/ __/ __/ _ \\/ __/ __|\n" +
                "\\__ \\ |_| | (_| (_|  __/\\__ \\__ \\\n" +
                "|___/\\__,_|\\___\\___\\___||___/___/");
    }

    /**
     * 获取访问用户ip
     */
    public static Long getIp(HttpServletRequest request) {
        String ip = request.getHeader(FORWARDED_FOR);
        if (emptyIp(ip))
            ip = request.getHeader(PROXY_CLIENT);
        if (emptyIp(ip))
            ip = request.getHeader(WL_PROXY_CLIENT);
        if (emptyIp(ip))
            ip = request.getRemoteAddr();
        if (ip.contains(","))
            ip = ip.split(",")[0];
        if (ip.equals(SELF_IP6))
            ip = SELF_IP4;
        return ipToLong(ip);
    }

    /**
     * 字符串型ip 转 long型ip
     */
    public static Long ipToLong(String strIp) {
        String[] s = strIp.split("\\.");
        return  (Long.parseLong(s[0]) << 24)
                + (Long.parseLong(s[1]) << 16) +
                (Long.parseLong(s[2]) << 8)
                + (Long.parseLong(s[3]));
    }

    /**
     * long型ip转字符串型ip
     */
    public static String longToIp(Long longIp) {
        return  (longIp >> 24) + "." +
                ((longIp & 0x00ffffff) >> 16) + "." +
                ((longIp & 0x0000ffff) >> 8) + "." +
                (longIp & 0x000000ff);
    }

    /**
     * IP是否为空
     */
    private static boolean emptyIp(String ip) {
        return isEmpty(ip) || ip.equals(UNKNOWN);
    }

    /**
     * 字符串id转Long型id
     */
    public static Long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 转换金额
     * @param money 金额
     * @return 以分为单位的金额
     */
    public static Integer parseMoney(String money) {
        if (SU.isEmpty(money))
            return null;
        try {
            float v = Float.parseFloat(money);
            if (v == 0)
                return null;
            return (int)(v * 100);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 字符串转LocalDate
     */
    public static LocalDate getLocalData(String str, String pattern) {
        try {
            return LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前时间14位时间字符串
     * yyyyMMddHHmmss
     */
    public static String getNowTimeStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * 时间戳转时间字符串
     * @param timestamp 时间戳
     * @return String
     */
    public static String timestampToStr(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 字符串列表以逗号形式拼接
     */
    public static String join(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i != list.size() - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

    public static Set<String> unJoin(String str) {
        Set<String> set = new HashSet<>();
        if (isEmpty(str))
            return set;
        if (str.contains(",")) {
            String[] split = str.split(",");
            set.addAll(Arrays.asList(split));
        } else {
            set.add(str);
        }
        return set;
    }

    public static int getRequestMethod(String method) {
        if (isEmpty(method))
            return 5;
        String str = method.toLowerCase();
        switch (str) {
            case "get": return 1;
            case "post": return 2;
            case "put": return 3;
            case "delete": return 4;
            default: return 5;
        }
    }

    public static String getRequestMethod(Integer method) {
        if (null == method || method <= 0)
            return "ERROR";
        switch (method) {
            case 1:
                return "GET";
            case 2:
                return "POST";
            case 3:
                return "PUT";
            case 4:
                return "DELETE";
            default:
                return "ERROR";
        }
    }

    /**
     * 将请求参数转化一下，用于日志储存
     */
    public static String paramToLog(Map<String, String[]> map) {
        if (null == map)
            return null;
        StringBuilder sb = new StringBuilder();
        map.forEach((k,v) -> sb.append(k).append(":").append(arrayToLog(v)).append("\n"));
        return sb.toString().trim();
    }

    /**
     * 将请求参数转化一下，用于日志储存
     * 数组数据的转化
     */
    private static String arrayToLog(String[] array) {
        if (null != array && array.length > 0) {
            if (array.length == 1) {
                return array[0];
            } else {
                StringBuilder res = new StringBuilder("[");
                for (int i = 0; i < array.length; i++) {
                    res.append(array[i]);
                    if (i != array.length - 1) {
                        res.append(",");
                    }
                }
                res.append("]");
                return res.toString();
            }
        }
        return "";
    }

    public static ReqType requestType(Integer type) {
        if (null == type)
            return null;
        for (ReqType value : ReqType.values()) {
            if (value.getType() == type)
                return value;
        }
        return null;
    }

    public static ReqData requestData(Integer type) {
        if (null == type)
            return null;
        for (ReqData value : ReqData.values()) {
            if (value.getType() == type)
                return value;
        }
        return null;
    }

    /**
     * 获取HTTP body参数
     * @param inputStream InputStream
     * @return String
     */
    public static String getBodyData(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> parseLogParamData(String params) {
        Map<String, String> map = new LinkedHashMap<>();
        if (SU.isEmpty(params))
            return map;
        String[] list = params.split("\\r?\\n");
        for (String str : list) {
            if (str.contains(":")) {
                String[] split = str.split(":", 2);
                map.put(split[0],split[1]);
            } else {
                map.put(str,"");
            }
        }
        return map;
    }

    public static void main(String[] args) {
        System.out.println(int.class.isPrimitive());
    }

}
