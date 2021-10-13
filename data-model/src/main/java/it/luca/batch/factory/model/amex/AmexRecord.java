package it.luca.batch.factory.model.amex;

import it.luca.data.factory.annotation.*;
import it.luca.data.factory.generator.function.PlusOrMinusTime;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class AmexRecord {

    private final String tipoRecord = "01";

    private Integer progressivoRecord;

    @RandomNumber(min = 100000, max = 5999999, as = Integer.class)
    private Integer ndgBanca;

    @Nullable(probability = 0.0005)
    @BoundedDateTime(
            lower = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 24 * 60 + 30, unit = ChronoUnit.MINUTES),
            upper = @BoundedDateTime.Bound(signum = PlusOrMinusTime.Signum.MINUS, amount = 30, unit = ChronoUnit.MINUTES))
    private LocalDateTime dataInserimentoRichiestaCarta;

    private LocalDateTime dataApprovazioneRichiestaCarta;

    @RandomValue(useClasspathLocator = true)
    private String descrizioneProdotto;

    @RandomValue(useClasspathLocator = true)
    private String tipoCarta;

    @MappedByField(fieldName = "tipoCarta")
    private String productTypeCode;

    @RandomValue(values = {" ", "-"})
    private String totaleSpesoMensileCartaSegno;

    @RandomValue(values = {" ", "-"})
    private String totaleSpesoMensileCartaAnnoSegno;

    @RandomValue(values = {" ", "-"})
    private String quotaAnnuaPrimoAnnoMeseSegno;

    @RandomValue(values = {" ", "-"})
    private String quotaAnnuaPrimoAnnoAnnoSegno;

    @Nullable(probability = 0.0005)
    @RandomValue(values = {"0", "1"})
    private String lendingIndicator;

    @Nullable(probability = 0.0005)
    @RandomValue(values = {"0", "1"})
    private String revolveIndicator;
}
