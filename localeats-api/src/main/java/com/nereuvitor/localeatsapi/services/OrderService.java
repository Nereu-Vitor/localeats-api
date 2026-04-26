package com.nereuvitor.localeatsapi.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.models.Address;
import com.nereuvitor.localeatsapi.models.Order;
import com.nereuvitor.localeatsapi.models.OrderItem;
import com.nereuvitor.localeatsapi.models.Product;
import com.nereuvitor.localeatsapi.models.User;
import com.nereuvitor.localeatsapi.models.enums.OrderStatus;
import com.nereuvitor.localeatsapi.repositories.OrderRepository;
import com.nereuvitor.localeatsapi.services.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final AddressService addressService;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> findByUser(Long userId) {
        User user = userService.findById(userId);
        return orderRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Order> findPendingOrders() {
        List<Integer> codes = Arrays.asList(
                OrderStatus.PENDING.getCode(),
                OrderStatus.PREPARING.getCode(),
                OrderStatus.SHIPPED.getCode());

        return orderRepository.findByStatusIn(codes);
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

        Address address = addressService.findById(obj.getDeliveryAddress().getId());
        obj.setDeliveryAddress(address);

        BigDecimal deliveryFee = calculateFee(address.getNeighborhood());
        obj.setDeliveryFee(deliveryFee);

        obj.setOrderDate(LocalDateTime.now());
        obj.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : obj.getItems()) {
            Long productId = item.getProduct().getId();

            Product product = productService.findById(productId);
            item.setProduct(product);
            item.setOrder(obj);

            BigDecimal precoVenda = (product.getOnPromotion() && product.getPromotionalPrice() != null)
                    ? product.getPromotionalPrice()
                    : product.getPrice();

            item.setPrice(precoVenda);
            total = total.add(item.getSubTotal());
        }

        obj.setTotalPrice(total.add(deliveryFee));
        return orderRepository.save(obj);
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus status) {
        Order entity = findById(id);
        entity.setStatus(status);
        return orderRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Order entity = findById(id);
        orderRepository.delete(entity);
    }

    private BigDecimal calculateFee(String neighborhood) {
        if (neighborhood == null || neighborhood.isBlank()) {
            return new BigDecimal("5.00");
        }

        String n = neighborhood.trim().toLowerCase();

        if (n.contains("centro") || n.contains("matriz")) {
            return new BigDecimal("2.00");
        }

        if (n.contains("cohab") || n.contains("alto") || n.contains("loteamento")) {
            return new BigDecimal("4.00");
        }

        if (n.contains("rural") || n.contains("sitio") || n.contains("povoado")) {
            return new BigDecimal("10.00");
        }

        return new BigDecimal("3.00");
    }

}
