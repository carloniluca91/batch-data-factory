package it.luca.batch.factory.model.horizon;

import it.luca.data.factory.annotation.RandomDateTime;
import it.luca.data.factory.generator.function.Now;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HorizonRecord {

    @RandomDateTime(supplier = Now.class)
    private LocalDateTime dataInvio;
}
