package com.app.onlineStore.repositories;


import com.app.onlineStore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public User  findByEmail (String email);

}
