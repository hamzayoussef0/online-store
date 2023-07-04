package com.app.onlineStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "user_tb")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends Person {
    @JsonIgnore
    private boolean isLogged; // a boolean variable to verify if  the user is logged in  entering the rights password and email

    @Column(nullable = false)
    private Double balance; // the current balance


    public User(String name, String email, String password, Double balance) {
        super(name, email, password);
        this.balance = balance;
    }



    @Override
    public String toString() {
        return "id=" + this.getId() +
                " ,name=" + this.getName()+
                " ,email=" + this.getEmail()
                ;
    }
}
