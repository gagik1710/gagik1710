package com.demo.notes.service;

import com.demo.notes.domain.User;
import com.demo.notes.mapper.UserMapper;
import com.demo.notes.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements CRUDService<User> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listAll(PageRequest of) {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User getById(Long id) {
        return UserMapper.INSTANCE.entityToDomain(userRepository.findById(id).get());
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        final var userEntity = UserMapper.INSTANCE.domainToEntity(domainObject);
        //ToDo improve
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
        final var updatedEntity = userRepository.save(userEntity);
        return UserMapper.INSTANCE.entityToDomain(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
