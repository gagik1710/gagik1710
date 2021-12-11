package com.demo.notes.mapper;

import com.demo.notes.domain.User;
import com.demo.notes.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings(
            {
                    @Mapping(source = "entity.role.role", target = "role")
            }
    )
    User entityToDomain(UserEntity entity);

    @Mappings(
            {
                    @Mapping(source = "domain.role", target = "role.role")
            }
    )
    UserEntity domainToEntity(User domain);
}
