package com.eurm.vaadin.flow.test;

import java.io.IOException;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.spring.annotation.EnableVaadin;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableVaadin
public class FlowSeleniumTestApplication extends SpringBootServletInitializer{
	
	private static final int SCHEDULER_THREAD_POLL_SIZE = 10;
	private final Logger logger = LoggerFactory.getLogger(FlowSeleniumTestApplication.class);
	
	@PostConstruct
	public void started() {
		// First spring BEAN created, then set default time zone
		logger.info("SETTING DEFAULT TIME ZONE UTC");
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@Bean
	public ThreadPoolTaskScheduler getEnableSchedulingExecutor() {
	    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
	    threadPoolTaskScheduler.setPoolSize(SCHEDULER_THREAD_POLL_SIZE);
	    return threadPoolTaskScheduler;
	}
	
	@Bean
    public ServletRegistrationBean frontendServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean<>(new VaadinServlet() {
            @Override
            protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            	logger.info("AGGGAGGGAGAGAGA: " + req.getRequestURI());
                if (!serveStaticOrWebJarRequest(req, resp)) {
                    resp.sendError(404);
                }
            }
        }, "/frontend/*");
        bean.setLoadOnStartup(1);
        
        return bean;
    }
	
    public static void main(String[] args) {
        SpringApplication.run(FlowSeleniumTestApplication.class, args);
    }

}
