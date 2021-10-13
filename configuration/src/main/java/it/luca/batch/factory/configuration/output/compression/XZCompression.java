package it.luca.batch.factory.configuration.output.compression;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.xz.XZUtils;

import java.io.OutputStream;

public class XZCompression extends Compression {

    @Override
    public String getCompressedFileName(String fileName) {
        return XZUtils.getCompressedFilename(fileName);
    }

    @Override
    public OutputStream getCompressedStream(OutputStream outputStream) throws CompressorException {
        return streamFactory.createCompressorOutputStream(CompressorStreamFactory.XZ, outputStream);
    }
}
