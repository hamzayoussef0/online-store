package com.app.onlineStore.services;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.Login;

public interface LogInService {
    public String logInUserByEmailAndPassword(Login login) throws MessageException;
    public String logInStockManagerByEmailAndPassword(Login login) throws MessageException;

    public String logOutUserById(Long idUser) throws MessageException;
    public String logOutStockManagerById(Long idAdmin) throws MessageException;

}


