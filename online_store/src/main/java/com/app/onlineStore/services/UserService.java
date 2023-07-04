package com.app.onlineStore.services;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.requestDto.UserRequestDto;
import com.app.onlineStore.dtos.responseDto.ProductResponseDto;
import com.app.onlineStore.models.Product;
import com.app.onlineStore.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public User userRegistration (UserRequestDto userRequestDto);
    public User getUserByEmail(String email);
    public User  removeUserById (Long id) throws MessageException;
    public User editUserById (Long id,UserRequestDto userRequestDto) throws MessageException;
    public List<User> retreiveAllUsers ();
    public User getUserById (Long id) throws MessageException;
    public Map<String,Integer>  confirmOrderByUserId(Long userId) throws MessageException;
 }
