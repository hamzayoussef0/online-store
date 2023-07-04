package com.app.onlineStore.services.impl;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.Login;
import com.app.onlineStore.models.StockManager;
import com.app.onlineStore.models.Person;
import com.app.onlineStore.models.User;
import com.app.onlineStore.services.StockManagerService;
import com.app.onlineStore.services.LogInService;
import com.app.onlineStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class LogInServiceimpl implements LogInService {
    UserService userService;
    StockManagerService stockManagerService;
    @Autowired
    public LogInServiceimpl(UserService userService, StockManagerService stockManagerService) {
        this.userService = userService;
        this.stockManagerService = stockManagerService;
    }

    /**
     *
     * @param login is an object form class Login that include email and password
     * @return String of userName and email for the user logged in
     */
    @Transactional
    @Override
    public String logInUserByEmailAndPassword(Login login) throws MessageException {
        User user = userService.getUserByEmail(login.getEmail()); // get user By email
        if (Objects.nonNull(user)) { // if user is not null
            if (user.getPassword().equals(login.getPassword())){ // if the  current user password is equals to the password in input
                user.setLogged(true); // set user is logged

            }
            Person person = (Person)user;
            String returnString = person.toString();
            returnString += " login= " + String.valueOf(user.isLogged());
            return returnString;
        }
        throw  new MessageException("email or password incorrect, try again or go to registration page!!");

    }
    @Transactional
    @Override
    public String logInStockManagerByEmailAndPassword(Login login) throws MessageException {
        StockManager stockManager = stockManagerService.getStockManagerByEmail(login.getEmail()); // get stock manager By email
        if (Objects.nonNull(stockManager)) { // if stock manager is not null
            if (stockManager.getPassword().equals(login.getPassword())){ // if the  current user password is equals to the password in input
                stockManager.setLogged(true); // set stock manager is logged

            }
            Person person = (Person) stockManager;
            String returnString = person.toString();
            returnString += " login= " + String.valueOf(stockManager.isLogged());
            return returnString;
        }
        throw  new MessageException("email or password incorrect, try again or go to registration page!!");
    }

    @Transactional
    @Override
    public String logOutUserById(Long idUser) throws MessageException {
        User user = userService.getUserById(idUser);
        user.setLogged(false);

        String returnString = user.toString();
        returnString += " login= " + String.valueOf(user.isLogged());
        return returnString;
    }
    @Transactional
    @Override
    public String logOutStockManagerById(Long idStockManager) throws MessageException {
        StockManager stockManager = stockManagerService.getStockManagerById(idStockManager) ;
        stockManager.setLogged(false);

        String returnString = stockManager.toString();
        returnString += " login= " + String.valueOf(stockManager.isLogged());
        return returnString;
    }




}



