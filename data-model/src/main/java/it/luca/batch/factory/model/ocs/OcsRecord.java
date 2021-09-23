package it.luca.batch.factory.model.ocs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import it.luca.data.factory.annotation.*;
import it.luca.data.factory.generator.function.Now;
import it.luca.data.factory.generator.function.PlusOrMinusTime;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@JsonPropertyOrder({"dataOperazione",
        "tipoOperazione", "ricorrente", "storno",
        "canale", "ndgCliente", "ndgConto",
        "dataContabilizzazione", "dataGenerazioneRecord"})
public class OcsRecord {

    public final static String DATA_OPERAZIONE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Nullable(probability = 0.0001)
    @BoundedDateTime(
            lower = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 24 * 60 + 30, unit = ChronoUnit.MINUTES),
            upper = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 30, unit = ChronoUnit.MINUTES))
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATA_OPERAZIONE_FORMAT)
    @JsonProperty("data_operazione")
    private LocalDateTime dataOperazione;

    @JsonProperty("tipo_operazione")
    @RandomValue(useClasspathLocator = true)
    private String tipoOperazione;

    @RandomValue(useClasspathLocator = true)
    private String ricorrente;

    @RandomValue(useClasspathLocator = true)
    private String storno;

    @RandomValue(useClasspathLocator = true)
    private String canale;

    @Nullable(probability = 0.0001)
    @RandomNumber(min = 100000, max = 999999, as = Integer.class)
    @JsonProperty("ndg_cliente")
    private Integer ndgCliente;

    @Nullable(probability = 0.0001)
    @RandomNumber(min = 100000, max = 999999, as = Integer.class)
    @JsonProperty("ndg_conto")
    private Integer ndgConto;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATA_OPERAZIONE_FORMAT)
    @JsonProperty("data_contabilizzazione")
    private LocalDateTime dataContabilizzazione;

    @RandomDateTime(supplier = Now.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATA_OPERAZIONE_FORMAT)
    @JsonProperty("data_generazione_record")
    private LocalDateTime dataGenerazioneRecord;
}
