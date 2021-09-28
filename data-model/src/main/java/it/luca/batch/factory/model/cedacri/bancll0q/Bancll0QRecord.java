package it.luca.batch.factory.model.cedacri.bancll0q;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import it.luca.data.factory.annotation.BoundedDateTime;
import it.luca.data.factory.annotation.Nullable;
import it.luca.data.factory.annotation.RandomValue;
import it.luca.data.factory.generator.function.PlusOrMinusTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@JsonPropertyOrder({
        "codIsti", "codIsin", "dataRif",
        "dataRil", "tipoRec", "percCom",
        "percRet", "impor01", "impor02"
})
public class Bancll0QRecord {

    @Getter
    @Setter
    @Nullable(probability = 0.0001)
    @RandomValue(useClasspathLocator = true)
    @JsonProperty("cod_isti")
    private String codIsti;

    @Getter
    @Setter
    @Nullable(probability = 0.0001)
    @RandomValue(useClasspathLocator = true)
    @JsonProperty("cod_isin")
    private String codIsin;

    @Getter
    @Setter
    @Nullable(probability = 0.0001)
    @BoundedDateTime(
            lower = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 60*24 + 30, unit = ChronoUnit.MINUTES),
            upper = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 30, unit = ChronoUnit.MINUTES))
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonProperty("data_rif")
    private LocalDateTime dataRif;

    @Getter
    @Setter
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:00")
    @JsonProperty("data_ril")
    private LocalDateTime dataRil;

    @Getter
    @JsonProperty("tipo_rec")
    private final String tipoRec = "IE7";

    @Getter
    @JsonProperty("perc_com")
    private final String percCom = "0,01";

    @Getter
    @JsonProperty("perc_ret")
    private final String percRet = "0,005";

    @Getter
    @JsonProperty("impor_01")
    private final String imporO1 = "1.000,00";

    @Getter
    @JsonProperty("impor_02")
    private final String imporO2 = "100.000,00";
}
