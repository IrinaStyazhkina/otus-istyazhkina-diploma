package ru.otus.istyazhkina.constructor;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
