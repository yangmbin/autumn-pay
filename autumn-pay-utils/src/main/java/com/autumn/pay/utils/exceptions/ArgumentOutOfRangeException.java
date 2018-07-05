package com.autumn.pay.utils.exceptions;

/**
 * @author yangmbin
 * @date 2018/7/5 14:20
 * @since 1.0.0
 */
public class ArgumentOutOfRangeException extends RuntimeException {
    public ArgumentOutOfRangeException() {
    }

    public ArgumentOutOfRangeException(String message) {
        super(message);
    }
}
