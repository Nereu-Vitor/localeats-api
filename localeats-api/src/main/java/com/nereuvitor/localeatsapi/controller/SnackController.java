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

import com.nereuvitor.localeatsapi.model.Product;
import com.nereuvitor.localeatsapi.model.Snack;
import com.nereuvitor.localeatsapi.service.SnackService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/snacks")
@RequiredArgsConstructor
@Validated
public class SnackController {

    private final SnackService snackService;

    @PostMapping
    public ResponseEntity<Snack> create(@Valid @RequestBody Snack obj) {
        obj = snackService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody Snack obj) {
        obj = snackService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
