package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvroSerialization extends OutputSerialization {

    public AvroSerialization(@JsonProperty(FORMAT) String type) {
        super(type);
    }
}
