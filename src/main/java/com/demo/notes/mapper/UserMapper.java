package com.demo.notes.mapper;

import com.demo.notes.domain.User;
import com.demo.notes.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User entityToDomain(UserEntity entity);

    UserEntity domainToEntity(User domain);
}
