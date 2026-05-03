package com.nereuvitor.localeatsapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.models.User;
import com.nereuvitor.localeatsapi.repositories.UserRepository;
import com.nereuvitor.localeatsapi.services.exceptions.DataBaseException;
import com.nereuvitor.localeatsapi.services.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

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
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        return userRepository.save(obj);
    }

    @Transactional
    public User update(Long id, User obj) {
        User entity = findById(id);
        if (Objects.nonNull(obj.getPassword()) && !obj.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(obj.getPassword()));
        }
        updateData(entity, obj);
        return userRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        
        if (!userRepository.existsById(id)) {
            throw new ObjectNotFoundException("Usuário não encontrado! Id: " + id);
        }
        
        try {
            userRepository.deleteById(id);         
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Não é possível excluir o usuário pois ele já possui pedidos no histórico.");
        }
    }

    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setUsername(obj.getUsername());
        entity.setPhone(obj.getPhone());
        entity.setEmail(obj.getEmail());
    }
}
