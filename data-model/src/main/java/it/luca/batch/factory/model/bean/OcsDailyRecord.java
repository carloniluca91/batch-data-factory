package it.luca.batch.factory.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import it.luca.data.factory.annotation.MappedByField;
import it.luca.data.factory.annotation.RandomDateTime;
import it.luca.data.factory.annotation.RandomValue;
import it.luca.data.factory.generator.function.Now;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"tipoOperazione", "descrizioneOperazione"})
public class OcsDailyRecord {

    @JsonProperty("tipo_operazione")
    @RandomValue(useClasspathLocator = true)
    private String tipoOperazione;

    @JsonProperty("descrizione_operazione")
    @MappedByField(fieldName = "tipoOperazione")
    private String descrizioneOperazione;

    @RandomDateTime(supplier = Now.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime dataInvio;
}
