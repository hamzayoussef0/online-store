package com.app.onlineStore.controllers;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.Login;
import com.app.onlineStore.services.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogInController {

    LogInService logInService;
    @Autowired
    public LogInController(LogInService logInService) {
        this.logInService = logInService;
    }

    @GetMapping("/login/user")
    public String loginUserUsingEmailAndPassword (@RequestBody Login login) throws MessageException {
        return logInService.logInUserByEmailAndPassword(login);
    }
    @GetMapping("/logout/user/{idUser}")
    public String LogOutUserById (@PathVariable Long idUser) throws MessageException {
        return logInService.logOutUserById(idUser);
    }
    @GetMapping("/login/stockManager")
    public String logInStockManagerByEmailAndPassword (@RequestBody Login login) throws MessageException {
        return logInService.logInStockManagerByEmailAndPassword(login);
    }
    @GetMapping("/logout/stockManager/{idStockManager}")
    public String logOutStockManagerById (@PathVariable Long idStockManager) throws MessageException {
        return logInService.logOutStockManagerById(idStockManager);
    }
}
