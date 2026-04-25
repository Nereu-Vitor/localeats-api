package com.nereuvitor.localeatsapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nereuvitor.localeatsapi.model.enums.OrderStatus;
import com.nereuvitor.localeatsapi.model.enums.PaymentMethod;
import com.nereuvitor.localeatsapi.model.validation.Create;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = Order.TABLE_NAME)
public class Order implements Serializable {

    public static final String TABLE_NAME = "orders";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime orderDate;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.01", message = "O preço do pedido deve ser maior que zero")
    private BigDecimal totalPrice;

    @NotNull(groups = Create.class, message = "O pedido deve estar associado a um usuário")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(groups = Create.class, message = "O endereço de entrega é obrigatório")
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address deliveryAddress;
    
    @Column(name = "delivery_fee", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.00", message = "A taxa de entrega deve ser maior que zero")
    private BigDecimal deliveryFee;

    @NotEmpty(groups = Create.class, message = "O pedido deve ter pelo menos um item")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "status")
    private Integer status;

    @Column(name = "payment_method")
    private Integer paymentMethod;

    public OrderStatus getStatus() {
        return OrderStatus.toEnum(status);
    }

    public void setStatus(OrderStatus status) {
        if (status != null) {
            this.status = status.getCode();
        }
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.toEnum(paymentMethod);
    }

    public void setPaymentMethod(PaymentMethod method) {
        if (method != null) {
            this.paymentMethod = method.getCode();
        }
    }

}
