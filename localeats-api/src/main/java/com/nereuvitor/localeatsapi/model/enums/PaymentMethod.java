package com.nereuvitor.localeatsapi.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {

    CASH(1, "Dinheiro"),
    CREDIT_CARD(2, "Cartão de Crédito"),
    DEBIT_CARD(3, "Cartão de Débito"),
    PIX(4, "PIX");

    private final int code;
    @JsonValue
    private final String description;

    public static PaymentMethod toEnum(Integer code) {
        if (code == null) return null;
        for (PaymentMethod value : PaymentMethod.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Método de pagamento inválido: " + code);
    }
}
