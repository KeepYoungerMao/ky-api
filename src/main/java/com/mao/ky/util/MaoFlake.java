package com.mao.ky.util;

/**
 * 单机版，不分布
 * 比分布寿命长。能活200年
 * @author : create by zongx at 2020/11/25 15:28
 */
public class MaoFlake {

    //2020-11-25 15:30:00
    public static final long START_TIMESTAMP = 1606289378000L;

    private long sequence = 0L;

    private long last_timestamp = -1L;

    public synchronized long nextId() {
        long current = now();
        if (current < last_timestamp)
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        if (current == last_timestamp) {
            sequence = (sequence + 1) & 4095;
            if (sequence == 0)
                current = nextMill();
        } else sequence = 0L;
        last_timestamp = current;
        return (current - START_TIMESTAMP) << 12 | sequence;
    }

    private long nextMill() {
        long mill = now();
        while (mill <= last_timestamp) {
            mill = now();
        }
        return mill;
    }

    private long now() {
        return System.currentTimeMillis();
    }

}
