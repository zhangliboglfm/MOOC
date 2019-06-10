package com.myself.JAVA_8_New_Character.customFuntionalInterface;

import java.util.*;
import static java.util.Comparator.comparing;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

class Sample {
    public static void main(String[] args) {
        Order order = new Order(Arrays.asList(
                new OrderItem(1, 1225),
                new OrderItem(2, 983),
                new OrderItem(3, 1554)
        ));


        order.transformAndPrint(new Transformer<Stream<OrderItem>>() {
            public Stream<OrderItem> transform(Stream<OrderItem> orderItems) {
                return orderItems.sorted(comparing(OrderItem::getPrice));
            }
        });

        /*order.transformAndPrint(orderItems -> orderItems.sorted(comparing(OrderItem::getPrice)));*/

    }

}
