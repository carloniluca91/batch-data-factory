package it.luca.batch.factory.app.service.dto;

public abstract class FsOperation {

    public FailedFsOperation asFailure() {
        return (FailedFsOperation) this;
    }

    public boolean isFailure() {
        return this instanceof FailedFsOperation;
    }
}
