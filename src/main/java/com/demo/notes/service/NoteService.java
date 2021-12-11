package com.demo.notes.service;

import com.demo.notes.domain.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements CRUDService<Note>{

    @Override
    public List<Note> listAll() {
        return null;
    }

    @Override
    public Note getById(Long id) {
        return null;
    }

    @Override
    public Note saveOrUpdate(Note domainObject) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
