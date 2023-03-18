package ua.profitsoft.hw9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Hw9Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw9Application.class, args);
    }

}
