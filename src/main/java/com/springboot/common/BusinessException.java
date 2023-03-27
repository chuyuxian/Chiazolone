package com.springboot.common;

/**
 * 自定义异常
 *   现用于处理 登录的异常信息
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
    /**
     * 抛出业务异常
     *
     * @param message 异常信息
     * @throws BusinessException 业务异常
     */
    private void throwBusinessException(String message) throws BusinessException {
        throw new BusinessException(message);
    }
}
