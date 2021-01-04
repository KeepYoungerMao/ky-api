package com.mao.ky.config.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : create by zongx at 2020/11/10 18:05
 */
public class Cache {

    /**
     * 线程池
     */
    public static final ExecutorService SERVICE = Executors.newFixedThreadPool(4);

    /**
     * 黑名单
     */
    public static final Set<String> BLACK_IP = new HashSet<>();

    /**
     * 请求限制
     */
    public static boolean canRequest() {
        return true;
    }

}
