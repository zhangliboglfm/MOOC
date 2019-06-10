package com.myself.JAVA_8_New_Character.customFuntionalInterface;

import java.util.List;
import java.util.stream.Stream;

public class Order {

    List<OrderItem> items;

    public Order(List<OrderItem> orderItems) {
        items = orderItems;
    }

    public void transformAndPrint(
            Transformer<Stream<OrderItem>> transformOrderItems) {

        transformOrderItems.transform(items.stream())
                .forEach(System.out::println);
    }
}