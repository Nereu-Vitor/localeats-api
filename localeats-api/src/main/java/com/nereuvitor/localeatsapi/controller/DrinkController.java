package com.nereuvitor.localeatsapi.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nereuvitor.localeatsapi.model.Drink;
import com.nereuvitor.localeatsapi.service.DrinkService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/drinks")
@RequiredArgsConstructor
@Validated
public class DrinkController {

    private final DrinkService drinkService;

    @PostMapping
    public ResponseEntity<Drink> create(@Valid @RequestBody Drink obj) {
        obj = drinkService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drink> update(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody Drink obj) {
        obj = drinkService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
