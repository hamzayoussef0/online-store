package com.app.onlineStore.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3)
    private String name;
    @Email
    private String email;
    @JsonIgnore
    private String password;

    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


}
