package it.luca.batch.factory.model.ocs;

import it.luca.batch.factory.model.CustomGenerationTest;

import static it.luca.utils.functional.Optional.isPresent;
import static org.junit.jupiter.api.Assertions.*;

class OcsRecordTest extends CustomGenerationTest<OcsRecord, OcsRecordGenerator> {

    public OcsRecordTest() {
        super(OcsRecord.class, OcsRecordGenerator.class);
    }

    @Override
    protected void testAssertions(OcsRecord instance) {

        if (isPresent(instance.getDataOperazione())) {
            assertTrue(instance.getDataContabilizzazione().isAfter(instance.getDataOperazione()));
        } else {
            assertNull(instance.getDataContabilizzazione());
        }
    }
}