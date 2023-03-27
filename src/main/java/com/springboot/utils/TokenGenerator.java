package com.springboot.utils;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * 生成token值的工具类
 *      获取当前时间戳和获取机器 IP 地址来生成 token值，保证了唯一性
 */
public class TokenGenerator {
    public static String generateToken(){
        String token = null;
        try {
            long timestamp = System.currentTimeMillis();
            InetAddress address = InetAddress.getLocalHost();
            String machineIdentifier = address.getHostAddress();
            long randomBits = new java.security.SecureRandom().nextLong();
            UUID uuid = new UUID(timestamp, randomBits);
            token = uuid.toString() + "-" + machineIdentifier;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return token;
    }
}
