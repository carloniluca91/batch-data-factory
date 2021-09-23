package it.luca.batch.factory.configuration.generation;

import it.luca.data.factory.generator.bean.CustomGenerator;

public class TestBeanGenerator implements CustomGenerator<TestBean> {

    @Override
    public TestBean setNonRandomAttributes(TestBean testBean) {
        return null;
    }
}
