package com.nereuvitor.localeatsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.model.User;
import com.nereuvitor.localeatsapi.repository.UserRepository;
import com.nereuvitor.localeatsapi.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Usuário não encontrado! Id: " + id));
    }

    @Transactional
    public User insert(User obj) {
        return userRepository.save(obj);
    }

    @Transactional
    public User update(Long id, User obj) {
        User entity = findById(id);
        updateData(entity, obj);
        return userRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        User entity = findById(id);
        userRepository.delete(entity);
    }

    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setUsername(obj.getUsername());
        entity.setPhone(obj.getPhone());
        entity.setEmail(obj.getEmail());
    }
}
