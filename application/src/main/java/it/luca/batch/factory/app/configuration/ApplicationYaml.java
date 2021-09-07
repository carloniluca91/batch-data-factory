package it.luca.batch.factory.app.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationYaml {

    private List<BatchDataSource<?>> dataSources;

    public BatchDataSource<?> getDataSourceWithId(String id) {

        List<BatchDataSource<?>> matchingDataSources = dataSources.stream()
                .filter(x -> x.getId().equalsIgnoreCase(id))
                .collect(Collectors.toList());

        int numberOfMatches = matchingDataSources.size();
        if (numberOfMatches == 0) {
            throw new NoSuchElementException(String.format("Found no dataSource with id %s", id));
        } else if (numberOfMatches > 1) {
            throw new RuntimeException(String.format("Found %s dataSource(s) with id %s", numberOfMatches, id));
        } else return matchingDataSources.get(0);
    }
}
