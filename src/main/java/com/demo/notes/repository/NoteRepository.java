package com.demo.notes.repository;

import com.demo.notes.entity.NoteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    Optional<NoteEntity> findByIdAndUserID(Long id, Long userId);
    List<NoteEntity> findAllByUserID(Long userId, Pageable pageable);
}
