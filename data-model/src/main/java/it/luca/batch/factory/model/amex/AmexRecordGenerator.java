package it.luca.batch.factory.model.amex;

import it.luca.data.factory.generator.bean.CustomGenerator;

import java.util.List;
import java.util.stream.IntStream;

import static it.luca.utils.functional.Optional.orNull;

public class AmexRecordGenerator implements CustomGenerator<AmexRecord> {

    @Override
    public List<AmexRecord> generateListOf(int size, Class<AmexRecord> amexRecordClass) throws Exception {

        List<AmexRecord> amexRecords = CustomGenerator.super.generateListOf(size, amexRecordClass);
        IntStream.range(0, amexRecords.size())
                .forEach(i -> {

                    AmexRecord record = amexRecords.get(i);
                    record.setProgressivoRecord(i + 1);
                    setNonRandomAttributes(record);
                });

        return amexRecords;
    }

    @Override
    public AmexRecord setNonRandomAttributes(AmexRecord record) {

        record.setDataApprovazioneRichiestaCarta(orNull(record.getDataInserimentoRichiestaCarta(), x ->
                x.plusMinutes(59 - x.getMinute()).plusSeconds(60 - x.getSecond())));
        return record;
    }
}
