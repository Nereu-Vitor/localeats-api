package com.nereuvitor.localeatsapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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

    public static final String TABLE_NAME = "snack";

    @NotBlank(message = "A lista de ingredientes é obrigatória")
    private String ingredients;

    private Boolean isVegan = false;
    
}
