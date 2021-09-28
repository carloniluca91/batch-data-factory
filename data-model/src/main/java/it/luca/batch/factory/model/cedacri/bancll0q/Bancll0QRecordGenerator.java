package it.luca.batch.factory.model.cedacri.bancll0q;

import it.luca.data.factory.generator.bean.CustomGenerator;

import java.time.LocalDateTime;

import static it.luca.utils.functional.Optional.orNull;

public class Bancll0QRecordGenerator implements CustomGenerator<Bancll0QRecord> {

    @Override
    public Bancll0QRecord setNonRandomAttributes(Bancll0QRecord bancll0QRecord) {

        LocalDateTime dataRil = orNull(bancll0QRecord.getDataRif(), x -> {
            int minutesToNextHour = 59 - x.getMinute();
            int secondsToNextMinute = 60 - x.getSecond();
            return x.plusSeconds(secondsToNextMinute).plusMinutes(minutesToNextHour);
        });

        bancll0QRecord.setDataRil(dataRil);
        return bancll0QRecord;
    }
}
