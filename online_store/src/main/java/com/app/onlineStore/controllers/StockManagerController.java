package com.app.onlineStore.controllers;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.requestDto.StockManagerRequestDto;
import com.app.onlineStore.models.StockManager;
import com.app.onlineStore.services.StockManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockManager")

public class StockManagerController {
  private StockManagerService stockManagerService;

    public StockManagerController(StockManagerService stockManagerService) {
        this.stockManagerService = stockManagerService;
    }
    @PostMapping
    public StockManager stockManagerRegistration(@RequestBody StockManagerRequestDto stockManagerRequestDto){
        return stockManagerService.stockManagerRegistration(stockManagerRequestDto);
    }
    @DeleteMapping("/{idStockManager}")
    public StockManager removeStockManagerById(@PathVariable Long idStockManager) throws MessageException{
        return stockManagerService.removeStockManagerById(idStockManager);
    }
    @PutMapping("/{idStockManager}")
    public StockManager editStockManagerById (@PathVariable Long idStockManager, @RequestBody StockManagerRequestDto stockManagerRequestDto) throws MessageException{
        return stockManagerService.editStockManagerById(idStockManager, stockManagerRequestDto);
    }
    @GetMapping("/all")
    public List<StockManager> retreiveAllStockManagers(){
        return stockManagerService.retreiveAllStockManagers();
    }
    @GetMapping("/{idStockManager}")
    public StockManager getStockManagerById(@PathVariable  Long idStockManager) throws MessageException{
        return stockManagerService.getStockManagerById(idStockManager);
    }








}
