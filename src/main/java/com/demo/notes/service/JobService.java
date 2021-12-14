package com.demo.notes.service;

import com.demo.notes.domain.DataTypeEnum;
import com.demo.notes.entity.NoteEntity;
import com.demo.notes.entity.UserEntity;
import com.demo.notes.processing.json.JsonWriter;
import com.demo.notes.processing.parquet.ParquetWriter;
import com.demo.notes.repository.NoteRepository;
import com.demo.notes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final JsonWriter jsonWriter;
    private final ParquetWriter parquetWriter;

    public String writeParquet() {
        try {
            parquetWriter.write(getNotesAsList(), DataTypeEnum.NOTE);
            parquetWriter.write(getUsersAsList(), DataTypeEnum.USER);
        } catch (IOException e) {
            e.printStackTrace();
            return "Fail" + e.getMessage();
        }
        return "Success";
    }

    public String writeJSON() {
        try {
            jsonWriter.writeData(getNotesAsMap());
            jsonWriter.writeData(getUsersAsMap());
        } catch (IOException e) {
            e.printStackTrace();
            return "Fail" + e.getMessage();
        }
        return "Success";
    }

    private List<List<String>> getNotesAsList() {
        return noteRepository.findAll().stream()
                .map(noteEntity -> {
                    List<String> list = new ArrayList<>();
                    list.add(noteEntity.getId().toString());
                    list.add(noteEntity.getUserID().toString());
                    list.add(noteEntity.getTitle());
                    list.add(noteEntity.getNote());
                    return list;
                })
                .collect(Collectors.toList());
    }

    private List<List<String>> getUsersAsList() {
        return userRepository.findAll().stream()
                .map(user -> {
                    List<String> list = new ArrayList<>();
                    list.add(user.getId().toString());
                    list.add(user.getEmail());
                    return list;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getNotesAsMap() {
        return noteRepository.findAll()
                .stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getUsersAsMap() {
        return userRepository.findAll()
                .stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    private Map<String, Object> toMap(NoteEntity note) {
        final var map = new HashMap<String, Object>();
        map.put("id", note.getId());
        map.put("userId", note.getUserID());
        map.put("title", note.getTitle());
        map.put("note", note.getNote());
        return map;
    }

    private Map<String, Object> toMap(UserEntity user) {
        final var map = new HashMap<String, Object>();
        map.put("id", user.getId());
        map.put("userId", user.getEmail());
        return map;
    }
}
