package it.luca.batch.factory.model.cedacri.bancll11;

import it.luca.batch.factory.model.StandardGenerationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class Bancll11RecordTest extends StandardGenerationTest<Bancll11Record> {

    public Bancll11RecordTest() {
        super(Bancll11Record.class);
    }

    @Override
    protected void testAssertions(Bancll11Record instance) {

        assertNotNull(instance.getAsseDa());
        assertNotNull(instance.getTotAsse());
    }
}