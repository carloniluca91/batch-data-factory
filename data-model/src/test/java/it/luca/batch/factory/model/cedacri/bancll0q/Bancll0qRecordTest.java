package it.luca.batch.factory.model.cedacri.bancll0q;

import it.luca.batch.factory.model.CustomGenerationTest;

import java.time.LocalDateTime;

import static it.luca.utils.functional.Optional.isPresent;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bancll0qRecordTest extends CustomGenerationTest<Bancll0QRecord, Bancll0QRecordGenerator> {

    public Bancll0qRecordTest() {
        super(Bancll0QRecord.class, Bancll0QRecordGenerator.class);
    }

    @Override
    protected void testAssertions(Bancll0QRecord instance) {

        LocalDateTime dataRif = instance.getDataRif();
        LocalDateTime dataRil = instance.getDataRil();
        assertEquals(isPresent(dataRif), isPresent(dataRil));
        if (isPresent(dataRif)) {

            assertEquals(dataRif.plusHours(1).getHour(), dataRil.getHour());
            assertEquals(0, dataRil.getMinute());
        }
    }
}
