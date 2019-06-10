package com.myself.JAVA_8_New_Character.customFuntionalInterface;


@FunctionalInterface
public interface Transformer<T> {
    T transform(T input);
}
