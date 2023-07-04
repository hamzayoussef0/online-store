package com.app.onlineStore.dtos;


import com.app.onlineStore.dtos.responseDto.CartResponseDto;
import com.app.onlineStore.dtos.responseDto.ProductResponseDto;
import com.app.onlineStore.models.Cart;
import com.app.onlineStore.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapper {

    public static CartResponseDto cartToCartResponseDto (Cart cart){
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setId(cart.getId());
        cartResponseDto.setUserName(cart.getUser().getName());
        Map<String,Integer> productDetails = new HashMap<>();
        int i = -1; // index to do a for loop and start form the first index in the map
        for (Product product: cart.getProducts() ) { // for each product in list of products into the cart
            i++;
            productDetails.put(product.getName(),cart.getProductsQuantities().get(i));// add name and quantity product used by cart

        }
        cartResponseDto.setProductDetails(productDetails);
        cartResponseDto.setTotalPrice(cart.getTotalPrice());
        return cartResponseDto;
    }

    public static ProductResponseDto productToProductResponseDto (Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getProductPrice());
        productResponseDto.setQuantity(product.getTotalQuantity());
        List<Long> ids = new ArrayList<>();
        for (Cart cart: product.getCarts() ) { // for each cart in list of carts into the product
            ids.add(cart.getId()); // add cart id to the list
        }
        productResponseDto.setCartsId(ids);
        return productResponseDto;
    }

    public static List<CartResponseDto> cartsToCartsResponseDtos (List<Cart> carts) {
        List<CartResponseDto> cartResponseDtos = new ArrayList<>();
        for (Cart cart: carts) cartResponseDtos.add(cartToCartResponseDto(cart));

        return cartResponseDtos;

    }

    public static List<ProductResponseDto> productsToProductsResponseDtos (List<Product> products) {
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product: products) productResponseDtos.add(productToProductResponseDto(product));

        return productResponseDtos;

    }

}
