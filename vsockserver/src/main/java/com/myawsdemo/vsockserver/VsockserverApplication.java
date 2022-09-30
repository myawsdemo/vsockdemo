package com.myawsdemo.vsockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VsockserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(VsockserverApplication.class, args);
        HelloWorld.helloNative();
    }
}
