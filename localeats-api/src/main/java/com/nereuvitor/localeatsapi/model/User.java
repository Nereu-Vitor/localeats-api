package com.nereuvitor.localeatsapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = User.TABLE_NAME)
public class User implements Serializable {

    public static final String TABLE_NAME = "users";

    private static final long serialVersionUID = 1L; 

    public interface CreateUser {
    }

    public interface UpdateUser {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 2, max = 100)
    private String name;
    
    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O nome de usuário é obrigatório", groups = CreateUser.class)
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;
    
    @Column(name = "password", length = 60, nullable = false)
    @NotBlank(message = "A senha é obrigatória", groups = { CreateUser.class, UpdateUser.class })
    @Size(groups = { CreateUser.class, UpdateUser.class }, min = 8, max = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @Column(name = "phone", length = 25, nullable = false)
    @NotBlank(message = "O telefone é obrigatório")
    private String phone;
    
    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Insira um e-mail válido")
    private String email;

}
