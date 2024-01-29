package cn.hihcxcn.patek.philippe;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yongdeng on 24/01/2024.
 */
@SpringBootApplication(scanBasePackages = "cn.hihcxcn.patek.philippe")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}