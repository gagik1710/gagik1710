package com.demo.notes.controller;

import com.demo.notes.domain.Note;
import com.demo.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getUserAllNotes(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                             @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

        return ResponseEntity.ok(noteService.listAll(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.saveOrUpdate(note));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.saveOrUpdate(note));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.ok().build();
    }

}
