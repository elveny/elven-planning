package site.elven.planning.experiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "site.elven.planning.boot.base",
        "site.elven.planning.api",
        "site.elven.planning.experiment"
})
public class ExperimentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExperimentApplication.class, args);
        System.out.println("ExperimentApplication started.");
    }
}
