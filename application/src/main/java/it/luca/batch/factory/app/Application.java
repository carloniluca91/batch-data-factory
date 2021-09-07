package it.luca.batch.factory.app;

import it.luca.batch.factory.app.controller.ApplicationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Value("${id}")
    private List<String> dataSourceIds;

    @Autowired
    private ApplicationController controller;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {

        log.info("Found {} dataSource(s) to be generated ({})", dataSourceIds.size(), String.join(", ", dataSourceIds));
        controller.run(dataSourceIds);
    }
}
