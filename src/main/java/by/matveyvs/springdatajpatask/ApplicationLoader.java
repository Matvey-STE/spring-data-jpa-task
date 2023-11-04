package by.matveyvs.springdatajpatask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationLoader {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationLoader.class, args);
    }

}
