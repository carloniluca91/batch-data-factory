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

    public SucceededFsOperation asSuccess() {
        return (SucceededFsOperation) this;
    }

    public boolean isFailure() {
        return this instanceof FailedFsOperation;
    }

    public boolean isSuccess() {
        return this instanceof SucceededFsOperation;
    }
}
