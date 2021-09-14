package it.luca.batch.factory.model.bean.horizon;

import it.luca.batch.factory.model.output.avro.AvroRecordMapper;

import java.time.format.DateTimeFormatter;

public class HorizonAvroMapper implements AvroRecordMapper<HorizonRecord, HorizonAvroRecord> {

    @Override
    public HorizonAvroRecord map(HorizonRecord input) {
        return HorizonAvroRecord.newBuilder()
                .setDataInvio(input.getDataInvio().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
