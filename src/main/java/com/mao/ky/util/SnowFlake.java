package com.mao.ky.util;

/**
 * @author : create by zongx at 2020/10/16 10:26
 */
public class SnowFlake {

    /**
     * 起始的时间戳
     * 2020-10-16 10:27:30
     */
    private final static long START_TIMESTAMP = 1602815224000L;

    private final long dataCenterId;    //数据中心
    private final long machineId;       //机器标识
    private long sequence = 0L;         //序列号
    private long lastTimestamp = -1L;   //上一次时间戳

    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > 31 || dataCenterId < 0)
            throw new IllegalArgumentException("dataCenterId can't be greater than 31 or less than 0");
        if (machineId > 31 || machineId < 0)
            throw new IllegalArgumentException("machineId can't be greater than 31 or less than 0");
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long current = now();
        if (current < lastTimestamp)
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        if (current == lastTimestamp) {
            sequence = (sequence + 1) & 4095;
            if (sequence == 0L)
                current = getNextMill();
        } else sequence = 0L;
        lastTimestamp = current;
        return (current - START_TIMESTAMP) << 22 | dataCenterId << 17 | machineId << 12 | sequence;
    }

    private long getNextMill() {
        long mill = now();
        while (mill <= lastTimestamp) {
            mill = now();
        }
        return mill;
    }

    private long now() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        /*SnowFlake snowFlake = new SnowFlake(2,3);
        for (int i = 0; i < 10; i++) {
            System.out.println(snowFlake.nextId());
        }*/
        //2020-10-16
        //2090-06-21
        long a = (7254947954000L - 1602815224000L) << 12 | 1;
        System.out.println(a);
    }

}
