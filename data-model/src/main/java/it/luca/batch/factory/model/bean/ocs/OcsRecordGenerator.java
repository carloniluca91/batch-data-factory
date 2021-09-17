package it.luca.batch.factory.model.bean.ocs;

import it.luca.data.factory.generator.bean.CustomGenerator;

import java.time.LocalDateTime;

import static it.luca.utils.functional.Optional.orNull;

public class OcsRecordGenerator implements CustomGenerator<OcsRecord> {

    @Override
    public OcsRecord setNonRandomAttributes(OcsRecord ocsRecord) {

        LocalDateTime dataContabilizzazione = orNull(ocsRecord.getDataOperazione(), l -> l.plusMinutes(10));
        ocsRecord.setDataContabilizzazione(dataContabilizzazione);
        return ocsRecord;
    }
}
