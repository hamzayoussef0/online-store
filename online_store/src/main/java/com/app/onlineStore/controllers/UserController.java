package com.app.onlineStore.controllers;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.requestDto.UserRequestDto;
import com.app.onlineStore.dtos.responseDto.ProductResponseDto;
import com.app.onlineStore.models.Product;
import com.app.onlineStore.models.User;
import com.app.onlineStore.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")

public class UserController {
  private   UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public User userRegistration (@RequestBody UserRequestDto userRequestDto){
        return userService.userRegistration(userRequestDto);
    }
    @DeleteMapping("/{idUser}")
    public User  removeUserById (@PathVariable  Long idUser) throws MessageException {
        return userService.removeUserById(idUser);

    }
    @PutMapping("/{idUser}")
    public User editUserById (@PathVariable Long idUser,@RequestBody UserRequestDto userRequestDto ) throws MessageException {
        return userService.editUserById(idUser,userRequestDto);

    }
    @GetMapping("/all")
    public List<User> retreiveAllUsers (){
       return userService.retreiveAllUsers();
    }
    @GetMapping("/{idUser}")
    public User getUserById (@PathVariable Long idUser) throws MessageException {
        return userService.getUserById(idUser);
    }
    @PutMapping("/confirm/{userId}")
    public Map<String,Integer>  confirmOrderByUserId(@PathVariable Long userId) throws MessageException{
        return userService.confirmOrderByUserId(userId);
    }
}
