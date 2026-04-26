package com.nereuvitor.localeatsapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.models.Drink;
import com.nereuvitor.localeatsapi.repositories.DrinkRepository;
import com.nereuvitor.localeatsapi.services.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;

    @Transactional
    public Drink insert(Drink obj) {
        return drinkRepository.save(obj);
    }

    @Transactional
    public Drink update(Long id, Drink obj) {
        Drink entity = drinkRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Bebida não encontrada! Id: " + id));
        updateData(entity, obj);
        return drinkRepository.save(entity);
    }

    private void updateData(Drink entity, Drink obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setImgUrl(obj.getImgUrl());
        entity.setDrinkType(obj.getDrinkType());
        entity.setVolumeMl(obj.getVolumeMl());
        entity.setIsAlcoholic(obj.getIsAlcoholic());
    }

}
