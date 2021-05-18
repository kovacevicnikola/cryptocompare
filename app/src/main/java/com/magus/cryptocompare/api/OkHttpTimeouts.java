package com.magus.cryptocompare.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OkHttpTimeouts {
    int DEFAULT_CONNECTION_TIMEOUT = 30;
    int DEFAULT_READ_WRITE_TIMEOUT = 60;

    int connection() default DEFAULT_CONNECTION_TIMEOUT;

    int read() default DEFAULT_READ_WRITE_TIMEOUT;

    int write() default DEFAULT_READ_WRITE_TIMEOUT;

    TimeUnit unit() default TimeUnit.SECONDS;
}
