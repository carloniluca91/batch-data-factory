package it.luca.batch.factory.model.amex;

import it.luca.batch.factory.model.CustomGenerationTest;

import static it.luca.utils.functional.Optional.isPresent;
import static org.junit.jupiter.api.Assertions.*;

class AmexRecordGeneratorTest extends CustomGenerationTest<AmexRecord, AmexRecordGenerator> {

    public AmexRecordGeneratorTest() {
        super(AmexRecord.class, AmexRecordGenerator.class);
    }

    @Override
    protected void testAssertions(AmexRecord instance) {

        assertNotNull(instance.getProgressivoRecord());
        if (isPresent(instance.getDataInserimentoRichiestaCarta())) {

            assertNotNull(instance.getDataApprovazioneRichiestaCarta());
            assertEquals(instance.getDataApprovazioneRichiestaCarta().getHour(), instance.getDataInserimentoRichiestaCarta().plusHours(1).getHour());
            assertEquals(0, instance.getDataApprovazioneRichiestaCarta().getMinute());
        } else {
            assertNull(instance.getDataApprovazioneRichiestaCarta());
        }
    }
}