package com.app.onlineStore.controllers;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.responseDto.CartResponseDto;
import com.app.onlineStore.services.CartService;
import com.app.onlineStore.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")

public class CartController {

    private CartService cartService;
    private UserService userService;

    public CartController(CartService cartService,UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }


    @GetMapping("/all")
    public List<CartResponseDto> retreiveAllCarts() {
        return cartService.retreiveAllCarts();
    }

    @PutMapping("/{idCart}/product/{idProduct}/{quantityProduct}")
    public String addProductToCartById(@PathVariable Long idCart, @PathVariable Long idProduct, @PathVariable Integer quantityProduct) throws MessageException {
      cartService.addProductToCartById(idCart, idProduct, quantityProduct);
      return "product added correctly";
    }

    @DeleteMapping("/{idCart}/{idProduct}")
    public void removeProductFromCartById(@PathVariable Long idCart,@PathVariable Long idProduct) throws MessageException {
        cartService.removeProductFromCartById(idCart,idProduct);

    }

    @DeleteMapping("/list")
    public void deleteProductsById(@RequestBody List <Long> idProducts) throws MessageException {
        cartService.deleteProductsById(idProducts);
    }

    @DeleteMapping("/product/{idProduct}")
    public String removeProductById(@PathVariable Long idProduct) throws MessageException {
        cartService.removeProductById(idProduct);
        return "ok";
    }
    @PutMapping("/cart/{idCart}/product/{idProduct}/{newQuantity}")
    public void updateProductQuantityByCartId(@PathVariable Long idCart,@PathVariable Long idProduct,@PathVariable Integer newQuantity) throws MessageException{
        cartService.updateProductQuantityByCartId(idCart, idProduct, newQuantity);
    }

}
