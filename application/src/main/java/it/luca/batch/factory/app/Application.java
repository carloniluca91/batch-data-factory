package it.luca.batch.factory.app;

import it.luca.batch.factory.app.configuration.ApplicationYaml;
import it.luca.batch.factory.app.service.ApplicationService;
import it.luca.batch.factory.model.BatchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    private ApplicationYaml yaml;

    @Autowired
    private ApplicationService service;

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.close();
    }

    @Override
    public void run(ApplicationArguments args) {

        List<String> dataSourceIds = args.getOptionValues("id");
        log.info("Found {} dataSource(s) to be generated ({})", dataSourceIds.size(), String.join("|", dataSourceIds));
        dataSourceIds.forEach(id -> {

            BatchDataSource<?> dataSource = yaml.getDataSourceWithId(id);
            service.generateBatch(dataSource);
        });

        log.info("Exiting main application. Goodbye ;)");
    }
}
