package com.tis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.tis.mapper")
public class TisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TisApplication.class,args);
    }
}
