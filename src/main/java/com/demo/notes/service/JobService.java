package com.demo.notes.service;

import com.demo.notes.parquet.CustomParquetWriter;
import com.demo.notes.repository.NoteRepository;
import com.demo.notes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(JobService.class);

    @PostConstruct
    public void writeNotes() throws IOException {
        final var columns = getNotes();
        final var schema = getSchemaForParquetFile();
        final var writer = getParquetWriter(schema);

        for (final var column : columns) {
            logger.info("Writing line: " + Arrays.toString(column.toArray()));
            writer.write(column);
        }
        logger.info("Finished writing Parquet file.");

        writer.close();
    }

    private CustomParquetWriter getParquetWriter(MessageType schema) throws IOException {
        final var outputFilePath = getClass().getResource("/").getPath() + "output/" + System.currentTimeMillis() + ".parquet";
        final var outputParquetFile = new File(outputFilePath);
        final var path = new Path(outputParquetFile.toURI().toString());
        return new CustomParquetWriter(path, schema, false, CompressionCodecName.SNAPPY);
    }

    private MessageType getSchemaForParquetFile() throws IOException {
        final var resource = new File(getClass().getResource("/schemas/note.schema").getFile());
        final var rawSchema = new String(Files.readAllBytes(resource.toPath()));
        return MessageTypeParser.parseMessageType(rawSchema);
    }

    private List<List<String>> getNotes() {
        return noteRepository.findAll(PageRequest.of(0, 200)).stream()
                .map(noteEntity -> {
                    List<String> list = new ArrayList<>();
                    list.add(noteEntity.getId().toString());
                    list.add(noteEntity.getUserID().toString());
                    list.add(noteEntity.getTitle());
                    list.add(noteEntity.getNote());
                    return list;
                })
                .toList();
    }
}
