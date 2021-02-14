package net.okhotnikov.aws_signer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class AwsSignerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AwsSignerApplication.class, args);
    }

}
