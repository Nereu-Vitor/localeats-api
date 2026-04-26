package com.nereuvitor.localeatsapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = Snack.TABLE_NAME)
@PrimaryKeyJoinColumn(name = "id")
public class Snack extends Product {

    public static final String TABLE_NAME = "snacks";

    @Column(name = "ingredients", length = 500, nullable = false)
    @NotBlank(message = "A lista de ingredientes é obrigatória")
    private String ingredients;

    @Column(name = "is_vegan", nullable = false)
    @NotNull(message = "O campo vegano é obrigatório")
    private Boolean isVegan;    
    
}
