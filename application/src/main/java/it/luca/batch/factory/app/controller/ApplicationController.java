package it.luca.batch.factory.app.controller;

import it.luca.batch.factory.app.configuration.ApplicationYaml;
import it.luca.batch.factory.app.service.ApplicationService;
import it.luca.batch.factory.model.BatchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ApplicationController {

    @Autowired
    private ApplicationYaml yaml;

    @Autowired
    private ApplicationService service;

    public void run(List<String> dataSourceIds) {

        dataSourceIds.forEach(id -> {
            BatchDataSource<?> dataSource = yaml.getDataSourceWithId(id);
            service.generateBatch(dataSource);
        });
    }
}
