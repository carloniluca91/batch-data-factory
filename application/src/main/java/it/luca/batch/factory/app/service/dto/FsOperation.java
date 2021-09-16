package it.luca.batch.factory.app.service.dto;

public abstract class FsOperation {

    public boolean isFailure() {
        return this instanceof FailedFsOperation;
    }
}
