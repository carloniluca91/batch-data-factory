package it.luca.batch.factory.model.output;

public class TestAvroRecordMapper implements AvroRecordMapper<TestBean, TestAvroRecord> {

    @Override
    public TestAvroRecord map(TestBean input) {

        return TestAvroRecord.newBuilder().build();
    }
}
