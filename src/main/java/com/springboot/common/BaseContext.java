package com.springboot.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户id
 *  笔记中有记载
 */
public class BaseContext {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(String id) {
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static String getCurrentId() {
        return threadLocal.get();
    }

}
