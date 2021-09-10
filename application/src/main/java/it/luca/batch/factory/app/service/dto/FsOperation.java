package it.luca.batch.factory.app.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class FsOperation {

    public enum FsOperationStatus {

        FAILED,
        SUCCESS
    }

    protected final FsOperationStatus status;

    public FailedFsOperation asFailure() {
        return (FailedFsOperation) this;
    }

    public boolean isFailure() {
        return this instanceof FailedFsOperation;
    }
}
