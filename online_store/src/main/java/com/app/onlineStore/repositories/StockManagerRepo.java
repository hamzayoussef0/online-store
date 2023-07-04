package com.app.onlineStore.repositories;


import com.app.onlineStore.models.StockManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface StockManagerRepo extends JpaRepository<StockManager, Long> {
    @Query(value = "select * from manager_tb where email=:email", nativeQuery = true) // native query to serch an stock manager by email
    public StockManager findStockManagerByEmail(@RequestParam String email); //

}
