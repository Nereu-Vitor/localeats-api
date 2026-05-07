package com.nereuvitor.localeatsapi.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nereuvitor.localeatsapi.models.enums.Profile;
import com.nereuvitor.localeatsapi.models.validation.Create;
import com.nereuvitor.localeatsapi.models.validation.Update;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = User.TABLE_NAME)
public class User implements Serializable {

    public static final String TABLE_NAME = "users";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "O nome completo é obrigatório", groups = { Create.class, Update.class })
    @Size(groups = { Create.class, Update.class }, min = 2, max = 100)
    private String name;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O nome de usuário é obrigatório", groups = { Create.class, Update.class })
    @Size(groups = { Create.class, Update.class }, min = 2, max = 100)
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    @NotBlank(message = "A senha é obrigatória", groups = { Create.class, Update.class })
    @Size(groups = { Create.class, Update.class }, min = 8, max = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "phone", length = 25, nullable = false)
    @NotBlank(message = "O telefone é obrigatório", groups = Create.class)
    @Size(groups = { Create.class, Update.class }, min = 8, max = 25)
    private String phone;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O e-mail é obrigatório", groups = Create.class)
    @Email(message = "Insira um e-mail válido", groups = { Create.class, Update.class })
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profiles")
    private Set<Integer> profiles = new HashSet<>();

    public User() {
        addProfile(Profile.CLIENT);
    }

    public User(Long id, String name, String username, String password, String phone, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        addProfile(Profile.CLIENT);
    }

    public Set<Profile> getProfiles() {
        return profiles.stream()
                .map(x -> Profile.toEnum(x))
                .collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        profiles.add(profile.getCode());
    }

}
