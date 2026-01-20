package site.elven.planning.plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"site.elven.planning.boot.base", "site.elven.planning.api", "site.elven.planning.plan"})
public class PlanApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PlanApplication.class, args);
        System.out.println("PlanApplication started.");
    }
}
