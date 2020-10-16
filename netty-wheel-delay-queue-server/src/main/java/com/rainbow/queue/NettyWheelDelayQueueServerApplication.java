package com.rainbow.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyWheelDelayQueueServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyWheelDelayQueueServerApplication.class, args);
    }

}
