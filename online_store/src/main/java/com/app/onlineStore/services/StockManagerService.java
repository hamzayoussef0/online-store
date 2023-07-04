package com.app.onlineStore.services;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.requestDto.StockManagerRequestDto;
import com.app.onlineStore.models.StockManager;

import java.util.List;

public interface StockManagerService {

    public StockManager stockManagerRegistration(StockManagerRequestDto stockManagerRequestDto);
    public StockManager getStockManagerByEmail(String email);
    public StockManager removeStockManagerById(Long id) throws MessageException;
    public StockManager editStockManagerById(Long id, StockManagerRequestDto stockManagerRequestDto) throws MessageException;
    public List<StockManager> retreiveAllStockManagers();
    public StockManager getStockManagerById(Long id) throws MessageException;


}
