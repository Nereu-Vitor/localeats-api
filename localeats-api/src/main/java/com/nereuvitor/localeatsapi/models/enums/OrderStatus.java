package com.nereuvitor.localeatsapi.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING(1, "Pendente"),
    PREPARING(2, "Em Preparação"),
    SHIPPED(3, "Saiu para Entrega"),
    DELIVERED(4, "Entregue"),
    CANCELED(5, "Cancelado"),
    DELIVERY_FAILED(6, "Entrega Falhou");

    private final int code;
    @JsonValue
    private final String description;

    public static OrderStatus toEnum(Integer code) {
        if (code == null) return null;
        for (OrderStatus status : OrderStatus.values()) {
            if (code.equals(status.getCode())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + code);
    }
}
