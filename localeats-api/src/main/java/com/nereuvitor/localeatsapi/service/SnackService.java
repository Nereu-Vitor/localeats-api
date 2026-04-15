package com.nereuvitor.localeatsapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.model.Snack;
import com.nereuvitor.localeatsapi.repository.SnackRepository;
import com.nereuvitor.localeatsapi.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SnackService {

    private final SnackRepository snackRepository;

    @Transactional
    public Snack insert(Snack obj) {
        return snackRepository.save(obj);
    }

    @Transactional
    public Snack update(Long id, Snack obj) {
        Snack entity = snackRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Lanche não encontrado! Id: " + id));
        updateData(entity, obj);
        return snackRepository.save(entity);
    }

    private void updateData(Snack entity, Snack obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setImgUrl(obj.getImgUrl());
        entity.setIngredients(obj.getIngredients());
        entity.setIsVegan(obj.getIsVegan());
    }

}
