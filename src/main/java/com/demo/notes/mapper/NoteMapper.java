package com.demo.notes.mapper;

import com.demo.notes.domain.Note;
import com.demo.notes.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);
    Note entityToDomain(NoteEntity noteEntity);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    NoteEntity domainToEntity(Note noteEntity);
}
