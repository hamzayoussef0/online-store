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
@Table(name = "manager_tb")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)


public class StockManager extends Person {

    @JsonIgnore
    private boolean isLogged; // a boolean variable to verify if  the user is logged in  entering the rights password and email

    public StockManager(String name, String email, String password) {
        super(name, email, password);

    }

    @Override
    public String toString() {
        return "id=" + this.getId() +
                " ,name=" + this.getName()+
                " ,email=" + this.getEmail()
                ;
    }
}
