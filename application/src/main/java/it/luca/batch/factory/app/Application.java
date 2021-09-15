package it.luca.batch.factory.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.luca.batch.factory.app.service.ApplicationService;
import it.luca.batch.factory.model.DataSource;
import it.luca.batch.factory.model.DataSourcesWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static it.luca.utils.functional.Optional.isPresent;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    public static final String DATASOURCE_YAML = "datasource-yaml";
    public static final String ID = "id";

    @Autowired
    private ApplicationService service;

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.close();
    }

    @Override
    public void run(ApplicationArguments args) throws MissingOptionException, MissingArgumentException, IOException {

        String dataSourceYaml = getValueOfOption(args, DATASOURCE_YAML).get(0);
        List<String> dataSourceIds = getValueOfOption(args, ID);
        DataSourcesWrapper wrapper = new ObjectMapper(new YAMLFactory())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(new File(dataSourceYaml), DataSourcesWrapper.class);

        log.info("Found {} dataSource(s) to be generated ({})", dataSourceIds.size(), String.join("|", dataSourceIds));
        dataSourceIds.forEach(id -> {

            DataSource<?> dataSource = wrapper.getDataSourceWithId(id);
            service.generateAndWriteDataForDataSource(dataSource);
        });

        log.info("Exiting main application. Goodbye ;)");
    }

    public static List<String> getValueOfOption(ApplicationArguments args, String name)
            throws MissingOptionException, MissingArgumentException {

        if (!args.containsOption(name)) {
            throw new MissingOptionException("Missing option ".concat(name));
        }

        List<String> optionValues = args.getOptionValues(name);
        if (!isPresent(optionValues) || optionValues.isEmpty()) {
            throw new MissingArgumentException("Missing argument for option ".concat(name));
        }

        return optionValues;
    }
}
