package com.demo.notes.service;

import com.demo.notes.configuration.security.domain.UserDetailsImpl;
import com.demo.notes.domain.Note;
import com.demo.notes.entity.NoteEntity;
import com.demo.notes.mapper.NoteMapper;
import com.demo.notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService implements CRUDService<Note> {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> listAll(PageRequest pageRequest) {
        final var userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return noteRepository.findAllByUserID(userDetails.getId(), pageRequest)
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
        final var userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NoteEntity entity;
        if (domainObject.getId() == null) {
            entity = NoteMapper.INSTANCE.domainToEntity(domainObject);
            entity.setUserID(userDetails.getId());
        } else {
            entity = noteRepository.findByIdAndUserID(domainObject.getId(), userDetails.getId()).orElseThrow();
            entity.setTitle(domainObject.getTitle());
            entity.setNote(domainObject.getNote());
        }
        return NoteMapper.INSTANCE.entityToDomain(noteRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}
