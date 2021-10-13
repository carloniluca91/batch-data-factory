package it.luca.batch.factory.model.amex;

import it.luca.batch.factory.model.AvroRecordMapperTest;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static it.luca.utils.functional.Optional.isPresent;
import static org.junit.jupiter.api.Assertions.*;

class AmexAvroRecordMapperTest extends AvroRecordMapperTest<AmexRecord, AmexAvroRecord, AmexAvroRecordMapper> {

    public AmexAvroRecordMapperTest() {
        super(AmexRecord.class, AmexAvroRecord.class, AmexAvroRecordMapper.class);
    }

    @Override
    protected List<AmexRecord> getInputRecordList() throws Exception {

        return new AmexRecordGenerator()
                .generateListOf(100, recordsClass);
    }

    @Override
    protected void testRecordMapping(AmexRecord input, AmexAvroRecord output) {

        assertEquals(input.getTipoRecord(), output.getTipoRecord());
        assertEquals(input.getProgressivoRecord(), output.getProgressivoRecord());
        assertEquals(input.getNdgBanca(), Integer.parseInt(output.getNdgBanca()));

        if (isPresent(input.getDataInserimentoRichiestaCarta())) {
            assertNotNull(output.getDataInserimentoRichiestaCarta());
            assertEquals(input.getDataInserimentoRichiestaCarta()
                            .format(DateTimeFormatter.ofPattern(AmexAvroRecordMapper.DATA_INSERIMENTO_RICHIESTA_PATTERN)),
                    output.getDataInserimentoRichiestaCarta());
        } else {
            assertNull(output.getDataInserimentoRichiestaCarta());
        }

        if (isPresent(input.getDataApprovazioneRichiestaCarta())) {
            assertNotNull(output.getDataApprovazioneRichiestaCarta());
            assertEquals(input.getDataApprovazioneRichiestaCarta()
                    .format(DateTimeFormatter.ofPattern(AmexAvroRecordMapper.DATA_INSERIMENTO_RICHIESTA_PATTERN)),
                    output.getDataApprovazioneRichiestaCarta());
        } else {
            assertNull(output.getDataApprovazioneRichiestaCarta());
        }

        assertEquals(input.getDescrizioneProdotto(), output.getDescrizioneProdotto());
        assertEquals(input.getTipoCarta(), output.getTipoCarta());
        assertEquals(input.getProductTypeCode(), output.getProductTypeCode());
        assertEquals(input.getTotaleSpesoMensileCartaSegno(), output.getTotaleSpesoMensileCartaSegno());
        assertEquals(input.getTotaleSpesoMensileCartaAnnoSegno(), output.getTotaleSpesoMensileCartaAnnoSegno());
        assertEquals(input.getQuotaAnnuaPrimoAnnoMeseSegno(), output.getQuotaAnnuaPrimoAnnoMeseSegno());
        assertEquals(input.getQuotaAnnuaPrimoAnnoAnnoSegno(), output.getQuotaAnnuaPrimoAnnoAnnoSegno());
        assertEquals(input.getLendingIndicator(), output.getLendingIndicator());
        assertEquals(input.getRevolveIndicator(), output.getRevolveIndicator());
    }
}