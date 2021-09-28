package it.luca.batch.factory.model.cedacri.bancll11;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import it.luca.data.factory.annotation.BoundedDateTime;
import it.luca.data.factory.annotation.Nullable;
import it.luca.data.factory.annotation.RandomNumber;
import it.luca.data.factory.annotation.RandomValue;
import it.luca.data.factory.generator.function.PlusOrMinusTime;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@JsonPropertyOrder({
        "ist", "prodotto", "fil",
        "conto", "cin", "asseDa",
        "numAsse", "dataRil", "divisa",
        "asseA", "totAsse", "stato1",
        "stato2", "stato3", "stato4",
        "stato5", "stato6", "stato7",
        "stato8", "stato9", "stato10",
        "tipoCarnet"
})
public class Bancll11Record {

    @RandomValue(useClasspathLocator = true)
    private String ist;

    @Nullable(probability = 0.0001)
    @RandomValue(useClasspathLocator = true)
    private String prodotto;

    @RandomNumber(min = 1, max = 9999, as = Integer.class)
    private Integer fil;

    @RandomNumber(min = 100000, max = 999999, as = Integer.class)
    private Integer conto;

    @Nullable(probability = 0.0001)
    @RandomValue(useClasspathLocator = true)
    private String cin;

    @JsonProperty("asse_da")
    private final Integer asseDa = 1;

    @RandomNumber(min = 1, max = 10, as = Integer.class)
    @JsonProperty("num_asse")
    private Integer numAsse;

    @Nullable(probability = 0.0001)
    @BoundedDateTime(lower = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 12, unit = ChronoUnit.MONTHS),
            upper = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 1, unit = ChronoUnit.MONTHS))
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:00")
    @JsonProperty("data_ril")
    private LocalDateTime dataRil;

    @RandomValue(useClasspathLocator = true)
    private String divisa;

    @RandomNumber(min = 1, max = 10, as = Integer.class)
    @JsonProperty("asse_a")
    private Integer asseA;

    @JsonProperty("tot_asse")
    private final Integer totAsse = 10;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_1")
    private String stato1;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_2")
    private String stato2;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_3")
    private String stato3;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_4")
    private String stato4;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_5")
    private String stato5;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_6")
    private String stato6;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_7")
    private String stato7;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_8")
    private String stato8;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_9")
    private String stato9;

    @RandomValue(values = {"Y", "N"})
    @JsonProperty("stato_10")
    private String stato10;

    @RandomValue(values = {"FF", "P"})
    @JsonProperty("tipo_carnet")
    private String tipoCarnet;
}