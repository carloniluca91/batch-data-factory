package it.luca.batch.factory.configuration.output;

public class TestAvroRecordMapper implements AvroRecordMapper<TestBean, TestAvroRecord> {

    @Override
    public TestAvroRecord map(TestBean input) {

        return TestAvroRecord.newBuilder().build();
    }
}