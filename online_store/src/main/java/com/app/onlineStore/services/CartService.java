package com.app.onlineStore.services;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.responseDto.CartResponseDto;
import com.app.onlineStore.models.Cart;

import java.util.List;

public interface CartService {

    public void removeCartById (Long id);
    public void addNewCart (Cart cart);

    public Cart getCartByIdCart(Long id);
    public Cart getCartByUserId (Long id);
    public List<CartResponseDto> retreiveAllCarts ();
    public void addProductToCartById (Long cartId, Long productId, Integer quantity) throws MessageException;
    public void removeProductFromCartById (Long cartId,Long productId) throws MessageException;
    public boolean checkUserBalanceByProductsTotalPrice (Long CartId);
    public void deleteAllProductsFromCartById (Long cartId);
    public List<Cart> retreiveListCartsByProductId(Long idProduct) throws MessageException;
    public void deleteProductsById(List<Long> idProducts) throws MessageException;
    public void removeProductById(Long idProduct) throws MessageException;
    public void updateProductQuantityByCartId (Long idCart, Long idProduct, Integer newQuantity) throws MessageException;


}
