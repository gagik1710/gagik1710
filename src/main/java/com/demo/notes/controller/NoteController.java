package com.demo.notes.controller;

import com.demo.notes.domain.Note;
import com.demo.notes.repository.NoteRepository;
import com.demo.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getById(Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

}
