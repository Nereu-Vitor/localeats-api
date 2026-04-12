package com.nereuvitor.localeatsapi.model;

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
@Table(name = Drink.TABLE_NAME)
@PrimaryKeyJoinColumn(name = "id")
public class Drink extends Product {

    public static final String TABLE_NAME = "drink";
    
    @Column(name = "volume_ml", nullable = false)
    @NotNull(message = "O volume em ml é obrigatório")
    private Integer volumeMl;

    @Column(name = "drink_type", length = 50, nullable = false)
    @NotBlank(message = "O tipo da bebida é obrigatório")
    private String drinkType;

    @Column(name = "is_alcoholic", nullable = false)
    @NotNull(message = "O campo alcoólico é obrigatório")
    private Boolean isAlcoholic;
    
}
