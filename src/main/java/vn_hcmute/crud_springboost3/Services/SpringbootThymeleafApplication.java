package vn_hcmute.crud_springboost3.Services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringbootThymeleafApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootThymeleafApplication.class, args);
    }
}