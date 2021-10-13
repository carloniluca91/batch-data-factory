package it.luca.batch.factory.model.amex;

import it.luca.batch.factory.configuration.output.AvroRecordMapper;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static it.luca.utils.functional.Optional.orNull;

public class AmexAvroRecordMapper implements AvroRecordMapper<AmexRecord, AmexAvroRecord> {

    public static final String DATA_INSERIMENTO_RICHIESTA_PATTERN = "HH:mm dd/MM/yyyy";

    public static <T> T nullOrIdentity(T value) {

        return orNull(value, Function.identity());
    }

    @Override
    public AmexAvroRecord map(AmexRecord input) {

        return AmexAvroRecord.newBuilder()
                .setTipoRecord(nullOrIdentity(input.getTipoRecord()))
                .setProgressivoRecord(nullOrIdentity(input.getProgressivoRecord()))
                .setNdgBanca(orNull(input.getNdgBanca(), String::valueOf))
                .setDataInserimentoRichiestaCarta(orNull(input.getDataInserimentoRichiestaCarta(),
                        x -> x.format(DateTimeFormatter.ofPattern(DATA_INSERIMENTO_RICHIESTA_PATTERN))))
                .setDataApprovazioneRichiestaCarta(orNull(input.getDataApprovazioneRichiestaCarta(),
                        x -> x.format(DateTimeFormatter.ofPattern(DATA_INSERIMENTO_RICHIESTA_PATTERN))))
                .setDescrizioneProdotto(nullOrIdentity(input.getDescrizioneProdotto()))
                .setTipoCarta(nullOrIdentity(input.getTipoCarta()))
                .setProductTypeCode(nullOrIdentity(input.getProductTypeCode()))
                .setTotaleSpesoMensileCartaSegno(nullOrIdentity(input.getTotaleSpesoMensileCartaSegno()))
                .setTotaleSpesoMensileCartaAnnoSegno(nullOrIdentity(input.getTotaleSpesoMensileCartaAnnoSegno()))
                .setQuotaAnnuaPrimoAnnoMeseSegno(nullOrIdentity(input.getQuotaAnnuaPrimoAnnoMeseSegno()))
                .setQuotaAnnuaPrimoAnnoAnnoSegno(nullOrIdentity(input.getQuotaAnnuaPrimoAnnoAnnoSegno()))
                .setLendingIndicator(nullOrIdentity(input.getLendingIndicator()))
                .setRevolveIndicator(nullOrIdentity(input.getRevolveIndicator()))
                .build();
    }
}
