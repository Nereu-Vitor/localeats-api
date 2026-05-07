package com.nereuvitor.localeatsapi.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profile {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private final int code;
    @JsonValue
    private final String description;

    public static Profile toEnum(Integer code) {
        if (code == null) return null;
        for (Profile profile : Profile.values()) {
            if (code.equals(profile.getCode())) {
                return profile;
            }
        }
        throw new IllegalArgumentException("Perfil de usuário inválido: " + code);
    }
}
