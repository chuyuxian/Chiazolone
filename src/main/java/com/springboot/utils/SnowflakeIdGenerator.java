package com.springboot.utils;

/**
 * Snowflake 算法生成唯一性 ID工具类
 * 生成的ID为九位数或十位数
 *  目前学习的技术是看不懂的
 */
public class SnowflakeIdGenerator {
    /**
     *    需要注意的是，如果机器 ID 的分配不当，例如多个机器 ID 相同，
     * 那么就会出现重复的情况。因此，使用 Snowflake 算法时，需要确保机器 ID 是唯一的，可以考虑将机器 ID 存储在配置文件中，
     * 并在应用程序启动时读取机器 ID。
     */

    private static final long START_TIMESTAMP = 1420041600000L; // 2015-01-01 00:00:00

    private static long lastTimestamp = -1L;
    private static long sequence = 0L;

    // 下面这两个参数根据实际情况调整
    private static final long MAX_SEQUENCE = 999L; // 最大序列号，9 位数的最大值是 999, 10 位数的最大值是 9999
    private static final int TIMESTAMP_BITS = 41; // 时间戳占用的位数

    /**
     * 生成唯一 ID
     * @return 唯一 ID
     */
    public static synchronized String generateUniqueId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                currentTimestamp = waitUntilNextTimestamp();
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        long uniqueId = ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_BITS) | sequence;

        return String.format("%010d", uniqueId % 10000000000L);
    }

    private static long waitUntilNextTimestamp() {
        long currentTimestamp = System.currentTimeMillis();
        while (currentTimestamp <= lastTimestamp) {
            currentTimestamp = System.currentTimeMillis();
        }
        return currentTimestamp;
    }
}
