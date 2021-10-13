package it.luca.batch.factory.configuration.output.compression;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.bzip2.BZip2Utils;

import java.io.OutputStream;

public class BZip2Compression extends Compression {

    @Override
    public String getCompressedFileName(String fileName) {
        return BZip2Utils.getCompressedFilename(fileName);
    }

    @Override
    public OutputStream getCompressedStream(OutputStream outputStream) throws CompressorException {
        return streamFactory.createCompressorOutputStream(CompressorStreamFactory.BZIP2, outputStream);
    }
}
