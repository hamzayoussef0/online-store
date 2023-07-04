package com.app.onlineStore.services.impl;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.Mapper;
import com.app.onlineStore.dtos.responseDto.CartResponseDto;
import com.app.onlineStore.models.Cart;
import com.app.onlineStore.models.Product;
import com.app.onlineStore.repositories.CartRepo;
import com.app.onlineStore.services.CartService;
import com.app.onlineStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    private  CartRepo cartRepo;
    private ProductService productService;

    @Autowired
    public CartServiceImpl(CartRepo cartRepo, ProductService productService) {
        this.cartRepo = cartRepo;
        this.productService = productService;
    }

   @Transactional
    @Override
    public void removeCartById(Long id) {
        cartRepo.deleteById(id);
    }


    @Override
    public void addNewCart(Cart cart) {
        Cart newCart = new Cart();
        newCart.setProducts(cart.getProducts());
        newCart.setUser(cart.getUser());
        newCart.setTotalPrice(cart.getTotalPrice());
        newCart.setProductsQuantities(cart.getProductsQuantities());
        cartRepo.save(newCart);
    }

    @Override
    public Cart getCartByIdCart(Long idCart) {
       return cartRepo.findById(idCart).orElseThrow(()-> new IllegalArgumentException("cart with id " + idCart + " not exist"));
    }

    @Override
    public Cart getCartByUserId(Long id) {
      return  cartRepo.findAll().stream().filter(cart -> cart.getUser().getId() == id).findFirst().get();

    }
    @Override
    public List<CartResponseDto> retreiveAllCarts (){
        List<Cart> carts = new ArrayList<>();
        for (Cart cart: cartRepo.findAll()) carts.add(cart);
        return Mapper.cartsToCartsResponseDtos(carts);
    }

    /**
     * the product totalquantity in the store it will update only when a specific user confirm his order
     * @param cartId
     * @param productId
     * @param quantity of product to be added i  n the cart
     */
    @Transactional
    @Override
    public void addProductToCartById(Long cartId, Long productId, Integer quantity) throws MessageException {
        Cart cart = getCartByIdCart(cartId);
        Product product = productService.getProductById(productId);
        if (cart.getUser().isLogged() == true) { // if the user is logged in correctly
             if (productService.checkQuantityAvailableByIdProduct(productId, quantity)) { // check if quanitity requested is available in the stroe

                // check if the product is already exist in the cart
                Optional<Product> productAv = cart.getProducts().stream().filter(product1 -> product1.getId() == productId).findFirst();
                if (cart.getProducts().isEmpty() || productAv.isEmpty()) { // if there are no products in cart or the product doesn't exist in cart

                    cart.setTotalPrice(cart.getTotalPrice() + product.getProductPrice() * quantity); // update totalprice
                    cart.addProductsQuantities(quantity); // add the product quantity in the arrayList
                    cart.addProduct(product);

                }
                else { // if product is already exist in cart

                    int index = cart.getProducts().indexOf(product); // search his index in array
                    Integer oldQuantity = cart.getProductsQuantities().get(index); // search his actual quantity in the arrayList of products quantities
                    cart.getProductsQuantities().set(index, quantity); // only update his quantity with the new one
                    cart.setTotalPrice(cart.getTotalPrice() + (quantity - oldQuantity) * product.getProductPrice()); // update total price in cart whith the new product quantity

                }

            } else throw new MessageException("the available quantity is: " + product.getTotalQuantity());
        }
        else throw new MessageException("you are not Logged in!!");
    }

    /**
     * function to remove a specific product from a specific cart
     * @param cartId
     * @param productId
     */
    @Transactional
    @Override
    public void removeProductFromCartById(Long cartId, Long productId) throws MessageException {
        Cart cart = getCartByIdCart(cartId);
        Product product = productService.getProductById(productId);
        int indexProduct = cart.getProducts().indexOf(product); // search product index in array list of products in cart
        Integer quantity = cart.getProductsQuantities().get(indexProduct); // get product quantity in cart
        cart.setTotalPrice(cart.getTotalPrice() - quantity * product.getProductPrice()); // update total price
        cart.removeProduct(product); // remove product from arraylist
        cart.removeProductsQuantitiesByIndex(indexProduct); // remove his quantity from cart
    }




    /**
     * check if the user balance is sufficient to buy all products in the cart
     * @param cartId
     * @return
     */
    @Override
    public boolean checkUserBalanceByProductsTotalPrice(Long cartId) {
        Cart cart = getCartByIdCart(cartId);
        Double userBalance  = cart.getUser().getBalance();
        Double totalPrice  = cart.getTotalPrice();
        if (userBalance >= totalPrice) return true;
        return false;
    }

    @Transactional
    @Override
    public void deleteAllProductsFromCartById(Long cartId) {
        Cart cart = getCartByIdCart(cartId);
        cart.removeAllProducts(); // remove all products form the cart
        cart.removeAllProductsQuantities(); // remove products quantities form the list
        cart.setTotalPrice(0.0); // set total products price to zero because initially the products list is empty
    }

    /**
     * retreive all carts where the product is located
     * @param idProduct
     * @return
     */
    @Override
    public List<Cart> retreiveListCartsByProductId(Long idProduct) throws MessageException {
       Product product = productService.getProductById(idProduct);
       List<Cart> carts = product.getCarts();
       return carts;


    }
    @Transactional
    @Override
    public void deleteProductsById(List <Long> idProducts) throws MessageException {
        for (Long id: idProducts) removeProductById(id);
    }
    @Transactional
    @Override
    public void removeProductById(Long idProduct) throws MessageException {
        List<Cart> cartList =  retreiveListCartsByProductId(idProduct); // retreive all carts where the product is located
        if(!cartList.isEmpty()){ // if carList is not empty
            for (Cart cart: cartList) { // for each cart in cart list
                removeProductFromCartById(cart.getId(),idProduct);// remove the product from cart

            }

        }
        productService.removeProductById(idProduct); // remove product from the store

    }
    @Transactional
    @Override
    public void updateProductQuantityByCartId(Long idCart, Long idProduct, Integer newQuantity) throws MessageException {
        Cart cart = getCartByIdCart(idCart);
        Product product = cart.getProducts().
                stream().
                filter(product1 -> product1.getId() == idProduct).
                findFirst().orElseThrow(() -> new MessageException("product with id: " + idProduct + "doesn't exist"));
        int indexProduct = cart.getProducts().indexOf(product);
        cart.getProductsQuantities().set(indexProduct, newQuantity);

    }


}

