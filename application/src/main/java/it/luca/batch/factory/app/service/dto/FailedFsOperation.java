package it.luca.batch.factory.app.service.dto;

import lombok.Getter;

@Getter
public class FailedFsOperation extends FsOperation {

    private final Exception exception;

    public FailedFsOperation(Exception exception) {

        super(FsOperationStatus.FAILED);
        this.exception = exception;
    }
}
