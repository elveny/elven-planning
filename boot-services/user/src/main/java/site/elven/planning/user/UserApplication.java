package site.elven.planning.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"site.elven.planning.boot.base", "site.elven.planning.api", "site.elven.planning.user"})
@SpringBootApplication(scanBasePackages = {"site.elven.planning.boot.base", "site.elven.planning.api", "site.elven.planning.user"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        System.out.println("UserApplication started.");
    }
}
