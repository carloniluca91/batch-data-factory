package it.luca.batch.factory.app;

import it.luca.batch.factory.app.configuration.ApplicationYaml;
import it.luca.batch.factory.app.service.ApplicationService;
import it.luca.batch.factory.model.BatchDataSource;
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
    private ApplicationYaml yaml;

    @Autowired
    private ApplicationService service;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        log.info("Found {} dataSource(s) to be generated ({})", dataSourceIds.size(), String.join("|", dataSourceIds));
        dataSourceIds.forEach(id -> {

            log.info("Starting to generate data for dataSource {}", id);
            BatchDataSource<?> dataSource = yaml.getDataSourceWithId(id);
            service.generateBatch(dataSource);
            log.info("Successfully generated data for dataSource {}", id);
        });
    }
}
