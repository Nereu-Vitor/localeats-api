package com.nereuvitor.localeatsapi.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nereuvitor.localeatsapi.models.Order;
import com.nereuvitor.localeatsapi.models.enums.OrderStatus;
import com.nereuvitor.localeatsapi.models.validation.Create;
import com.nereuvitor.localeatsapi.services.OrderService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> list = orderService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> findByUser(@PathVariable @Min(1) Long userId) {
        List<Order> list = orderService.findByUser(userId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<Order>> findPendingOrders() {
        List<Order> list = orderService.findPendingOrders();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable @Min(1) Long id) {
        Order obj = orderService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Order> create(@Validated(Create.class) @RequestBody Order obj) {
        obj = orderService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable @Min(1) Long id, @RequestBody OrderStatus status) {
        Order obj = orderService.updateStatus(id, status);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
