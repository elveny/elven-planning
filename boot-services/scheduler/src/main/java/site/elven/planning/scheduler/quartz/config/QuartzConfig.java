package site.elven.planning.scheduler.quartz.config;

import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import site.elven.planning.scheduler.quartz.listener.tracking.DelayTrackingJobListener;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Autowired
    private DelayTrackingJobListener delayTrackingJobListener;

    @Bean
    public JobFactory jobFactory(){
        return new SpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("default-scheduler");
        factory.setDataSource(dataSource);
        factory.setOverwriteExistingJobs(true);
        Properties properties = new Properties();
        properties.put("org.quartz.jobStore.misfireThreshold", "60000");
        properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        factory.setQuartzProperties(properties);
        factory.setGlobalJobListeners(delayTrackingJobListener);
        factory.setJobFactory(jobFactory);
        // 自定义配置...
        return factory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory) {
        return factory.getScheduler();
    }
}
