package com.nereuvitor.localeatsapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.models.Product;
import com.nereuvitor.localeatsapi.repositories.ProductRepository;
import com.nereuvitor.localeatsapi.services.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllActive() {
        return productRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Produto não encontrado! Id: " + id));
    }

    @Transactional
    public Product insert(Product obj) {
        return productRepository.save(obj);
    }

    @Transactional
    public Product update(Long id, Product obj) {
        Product entity = findById(id);
        updateData(entity, obj);
        return productRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Product obj = findById(id);
        obj.setActive(false);
        productRepository.save(obj);
    }

    private void updateData(Product entity, Product obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setImgUrl(obj.getImgUrl());
    }
}
