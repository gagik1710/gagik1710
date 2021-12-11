package com.demo.notes.service;

import com.demo.notes.configuration.security.domain.UserDetailsImpl;
import com.demo.notes.domain.Note;
import com.demo.notes.mapper.NoteMapper;
import com.demo.notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService implements CRUDService<Note> {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> listAll() {
        final var userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return noteRepository.findAllByUserID(userDetails.getId())
                .stream()
                .map(NoteMapper.INSTANCE::entityToDomain)
                .toList();
    }

    @Override
    public Note getById(Long id) {
        final var userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return noteRepository.findByIdAndUserID(id, userDetails.getId())
                .map(NoteMapper.INSTANCE::entityToDomain)
                .orElse(null);
    }

    @Override
    public Note saveOrUpdate(Note domainObject) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
