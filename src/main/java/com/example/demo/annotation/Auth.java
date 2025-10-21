package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标记需要 Token 校验的方法
 */
@Target(ElementType.METHOD) // 作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface Auth {
}
