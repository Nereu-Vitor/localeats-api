package com.nereuvitor.localeatsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.model.Product;
import com.nereuvitor.localeatsapi.repository.ProductRepository;

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
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException(
            "Produto não encontrado! Id: " + id
        ));
    }

    @Transactional
    public Product insert(Product obj) {
        return productRepository.save(obj);
    }

    @Transactional
    public Product update(Product obj) {
        Product entity = findById(obj.getId());
        updateData(entity, obj);
        return productRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Product entity = findById(id);
        productRepository.delete(entity);
    }

    private void updateData(Product entity, Product obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
    }
}
