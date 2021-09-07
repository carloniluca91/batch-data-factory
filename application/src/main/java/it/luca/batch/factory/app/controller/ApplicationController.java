package it.luca.batch.factory.app.controller;

import it.luca.batch.factory.app.configuration.ApplicationYaml;
import it.luca.batch.factory.app.configuration.BatchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ApplicationController {

    @Autowired
    private ApplicationYaml yaml;

    public void run(List<String> dataSourceIds) {

        dataSourceIds.forEach(id ->
        {
            try {
                BatchDataSource<?> matchingDataSource = yaml.getDataSourceWithId(id);
            } catch (Exception e) {
                log.error("Caught exception while generating data for {}. Stack trace: ", id, e);
            }
        });
    }
}
