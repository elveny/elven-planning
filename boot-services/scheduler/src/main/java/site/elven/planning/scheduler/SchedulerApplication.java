package site.elven.planning.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"site.elven.planning.boot.base", "site.elven.planning.boot.common", "site.elven.planning.api", "site.elven.planning.scheduler"})
public class SchedulerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SchedulerApplication.class, args);
        System.out.println("SchedulerApplication started.");
    }
}
