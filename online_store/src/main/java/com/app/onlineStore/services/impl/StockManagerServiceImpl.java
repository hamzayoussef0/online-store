package com.app.onlineStore.services.impl;


import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.requestDto.StockManagerRequestDto;
import com.app.onlineStore.models.StockManager;
import com.app.onlineStore.repositories.StockManagerRepo;
import com.app.onlineStore.services.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class StockManagerServiceImpl implements StockManagerService {

    private StockManagerRepo stockManagerRepo;
    @Autowired
    public StockManagerServiceImpl(StockManagerRepo stockManagerRepo) {
        this.stockManagerRepo = stockManagerRepo;
    }

    @Transactional
    @Override
    public StockManager stockManagerRegistration(StockManagerRequestDto stockManagerRequestDto) {
        StockManager stockManager = new StockManager(); // create a new stock Manager
        stockManager.setName(stockManagerRequestDto.getName());
        stockManager.setEmail(stockManagerRequestDto.getEmail());
        stockManager.setPassword(stockManagerRequestDto.getPassword());
        stockManager.setLogged(false); // set user logged to false
        stockManagerRepo.save(stockManager);
        return stockManager;
    }

    @Override
    public StockManager getStockManagerByEmail(String email) {
        return stockManagerRepo.findStockManagerByEmail(email);
    }
    @Transactional
    @Override
    public StockManager removeStockManagerById(Long id) throws MessageException {
        StockManager stockManager = getStockManagerById(id);
        stockManagerRepo.delete(stockManager);
        return stockManager;
    }
    @Transactional
    @Override
    public StockManager editStockManagerById(Long id, StockManagerRequestDto stockManagerRequestDto) throws MessageException {
        StockManager stockManager = getStockManagerById(id);
        if (Objects.nonNull(stockManagerRequestDto.getEmail())) stockManager.setEmail(stockManagerRequestDto.getEmail()); // if requested email is not null update the current stock manager email
        if (Objects.nonNull(stockManagerRequestDto.getName())) stockManager.setName(stockManagerRequestDto.getName()); // if requested name is not null update the current  stock manager name
        if (Objects.nonNull(stockManagerRequestDto.getPassword())) stockManager.setPassword(stockManagerRequestDto.getPassword()); // if requested password is not null update the current  stock manager password
        return stockManager;

    }

    @Override
    public List<StockManager> retreiveAllStockManagers() {
        return stockManagerRepo.findAll();
    }

    @Override
    public StockManager getStockManagerById(Long id) throws MessageException {
        return stockManagerRepo.findById(id).orElseThrow( () ->
                new MessageException("stock Manager with id " + id + " doesn't exist"));

    }
}
