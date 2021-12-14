package com.demo.notes.processing.json;

import com.demo.notes.processing.PathUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Component
public class JsonWriter {
    private static final ObjectMapper writer = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(JsonWriter.class);

    public void writeData(Map<String, Object> data) throws IOException, URISyntaxException {
        writeData(List.of(data));
    }

    public void writeData(List<Map<String, Object>> data) throws IOException {
        final var fileName = System.currentTimeMillis() + ".json";
        final var path = PathUtils.getRootPathFor(PathUtils.FileType.JSON) + fileName;
        writer.writeValue(Path.of(path).toFile(), data);
        logger.info("Finished writing Json file.");
    }
}
