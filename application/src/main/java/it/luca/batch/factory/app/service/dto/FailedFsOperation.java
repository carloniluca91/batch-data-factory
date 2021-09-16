package it.luca.batch.factory.app.service.dto;

import lombok.Getter;

@Getter
public class FailedFsOperation extends FsOperation {

    private final Exception exception;

    public FailedFsOperation(Exception exception) {

        this.exception = exception;
    }
}
