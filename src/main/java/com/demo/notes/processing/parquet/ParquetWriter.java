package com.demo.notes.processing.parquet;

import com.demo.notes.configuration.parquet.CustomParquetWriter;
import com.demo.notes.domain.DataTypeEnum;
import com.demo.notes.processing.PathUtils;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Component
public class ParquetWriter {
    private final Logger logger = LoggerFactory.getLogger(ParquetWriter.class);

    public void write(List<List<String>> columns, DataTypeEnum dataType) throws IOException {
        final var writer = getParquetWriter(dataType);
        for (final var column : columns) {
            logger.info("Writing line: " + Arrays.toString(column.toArray()));
            writer.write(column);
        }
        logger.info("Finished writing Parquet file.");

        writer.close();
    }

    private CustomParquetWriter getParquetWriter(DataTypeEnum dataType) throws IOException {
        final var schema = getSchemaForParquetFile(dataType);
        final var outputFilePath = PathUtils.getRootPathFor(PathUtils.FileType.PARQUET) + System.currentTimeMillis() + ".parquet";
        final var outputParquetFile = new File(outputFilePath);
        final var path = new Path(outputParquetFile.toURI().toString());
        logger.info("The path is " + path);
        return new CustomParquetWriter(path, schema, false, CompressionCodecName.SNAPPY);
    }

    private MessageType getSchemaForParquetFile(DataTypeEnum dataType) throws IOException {
        final var resource = getFileByType(dataType);
        final var rawSchema = new String(resource);
        return MessageTypeParser.parseMessageType(rawSchema);
    }

    private byte[] getFileByType(DataTypeEnum dataType) {
        try {
            return getClass().getResourceAsStream(dataType.equals(DataTypeEnum.NOTE) ? "/schemas/note.schema" : "/schemas/user.schema").readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
