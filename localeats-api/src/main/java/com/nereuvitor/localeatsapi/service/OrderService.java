package com.nereuvitor.localeatsapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.model.Order;
import com.nereuvitor.localeatsapi.model.OrderItem;
import com.nereuvitor.localeatsapi.model.Product;
import com.nereuvitor.localeatsapi.model.User;
import com.nereuvitor.localeatsapi.model.enums.OrderStatus;
import com.nereuvitor.localeatsapi.repository.OrderRepository;
import com.nereuvitor.localeatsapi.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                    "Pedido não encontrado! Id: " + id));
    }

    @Transactional
    public Order insert(Order obj) {
        User user = userService.findById(obj.getUser().getId());
        obj.setUser(user);

        obj.setOrderDate(LocalDateTime.now());

        obj.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;
        
        for (OrderItem item : obj.getItems()) {
            Long productId = item.getProduct().getId();

            Product product = productService.findById(productId);
            item.setProduct(product);
            item.setOrder(obj);

            BigDecimal precoVenda = (product.getOnPromotion() && product.getPromotionalPrice() != null) ? product.getPromotionalPrice() : product.getPrice();

            item.setPrice(precoVenda);
            total = total.add(item.getSubTotal());
        }

        obj.setTotalPrice(total);
        return orderRepository.save(obj);
    }

    @Transactional
    public void delete(Long id) {
        Order entity = findById(id);
        orderRepository.delete(entity);
    }

}
