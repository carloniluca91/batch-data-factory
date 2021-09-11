package it.luca.batch.factory.model.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.data.factory.annotation.MappedByField;
import it.luca.data.factory.annotation.RandomValue;
import lombok.Data;

@Data
public class OcsDailyRecord {

    @JsonProperty("tipo_operazione")
    @RandomValue(useClasspathLocator = true)
    private String tipoOperazione;

    @JsonProperty("descrizione_operazione")
    @MappedByField(fieldName = "tipoOperazione")
    private String descrizioneOperazione;
}
