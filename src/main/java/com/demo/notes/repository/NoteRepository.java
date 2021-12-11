package com.demo.notes.repository;

import com.demo.notes.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
}
