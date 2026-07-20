package org.example;

import org.example.payment.PaymentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context= new AnnotationConfigApplicationContext(AppConfig.class);
//        PaymentService payment = context.getBean(PaymentService.class);
//        OrderService orderService=context.getBean(OrderService.class);
        User user=context.getBean(User.class);
//        orderService.placeOrder();
        System.out.println(user.getName());
        System.out.println(user.getAge());
    }
}