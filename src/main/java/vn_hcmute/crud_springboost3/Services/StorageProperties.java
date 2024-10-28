package vn_hcmute.crud_springboost3.Services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@ConfigurationProperties("storage")

public class StorageProperties {
    private String location;
    @SpringBootApplication
    @EnableConfigurationProperties(StorageProperties.class) // thêm cấu hình storage
    public class SpringbootThymeleafApplication {
        public static void main(String[] args) {
            SpringApplication.run(SpringbootThymeleafApplication.class, args);
        }

        // thêm cấu hình storage
      /*  @Bean
        CommandLineRunner init(IStorageService storageService) {
            return (args -> {
                storageService.init();
            });*/
        }
    }

