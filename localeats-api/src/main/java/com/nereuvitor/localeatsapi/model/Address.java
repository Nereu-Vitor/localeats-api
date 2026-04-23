package com.nereuvitor.localeatsapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = Address.TABLE_NAME)
public class Address implements Serializable {

    public static final String TABLE_NAME = "addresses";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street", length = 150, nullable = false)
    @NotBlank(message = "A rua é obrigatória")
    @Size(max = 150, message = "A rua deve ter no máximo 150 caracteres")
    private String street;

    @Column(name = "number", length = 10, nullable = false)
    @NotBlank(message = "O número é obrigatório")
    @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
    private String number;

    @Column(name = "neighborhood", length = 100, nullable = false)
    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 100, message = "O bairo deve ter no máximo 100 caracteres")
    private String neighborhood;

    @Column(name = "complement", length = 150)
    @Size(max = 150, message = "O complemento deve ter no máximo 150 caracteres")
    private String complement;

    @Column(name = "city", length = 100, nullable = false)
    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String city = "Terra Nova";

    @Column(name = "state", length = 2, nullable = false)
    @NotBlank(message = "O estado é obrigatório")
    @Size(min = 2, max = 2, message = "Use a sigla do estado (ex: PE)")
    private String state = "PE";

    @Column(name = "zip_code", length = 9, nullable = false)
    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 8, max = 9, message = "CEP inválido")
    private String zipCode;

    @NotNull(message = "O endereço deve pertencer a um usuário")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

}
