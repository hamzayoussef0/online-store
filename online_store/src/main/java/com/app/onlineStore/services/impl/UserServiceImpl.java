package com.app.onlineStore.services.impl;


import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.Mapper;
import com.app.onlineStore.dtos.requestDto.UserRequestDto;
import com.app.onlineStore.dtos.responseDto.ProductResponseDto;
import com.app.onlineStore.models.Cart;
import com.app.onlineStore.models.Product;
import com.app.onlineStore.models.User;

import com.app.onlineStore.repositories.UserRepo;
import com.app.onlineStore.services.CartService;
import com.app.onlineStore.services.ProductService;
import com.app.onlineStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private  UserRepo userRepo;
    private  CartService  cartService;
    private ProductService productService;
    @Autowired
    public UserServiceImpl(UserRepo userRepo,CartService cartService, ProductService productService) {
        this.userRepo = userRepo;
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * registration of user data
     * for each registered user his own cart is created
     * initially, cart created is empty whith no products inside
     * @param userRequestDto
     * @return user inserted
     */
    @Override
    public User userRegistration(UserRequestDto userRequestDto) {

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setBalance(userRequestDto.getBalance());
        user.setPassword(userRequestDto.getPassword());
        user.setLogged(false);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProducts(new ArrayList<>(Arrays.asList())); // initially, cart created ha no products inside
        cart.setProductsQuantities(new ArrayList<>(Arrays.asList())); // there are no products inside cart
        cart.setTotalPrice(0.0);

        cartService.addNewCart(cart);
        userRepo.save(user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Transactional
    @Override
    public User removeUserById(Long id) throws MessageException {
        User user = getUserById(id);
        Cart cart = cartService.getCartByUserId(id);
        List<Product> products = cart.getProducts(); // retreive products list in cart
        for (int i = 0; i < products.size(); i++) {
            cartService.removeProductFromCartById(cart.getId(),products.get(i).getId()); // for each product remove it from cart
        }
       cartService.removeCartById(cart.getId()); // delete cart

        return user;

    }
    @Transactional
    @Override
    public User editUserById(Long id, UserRequestDto userRequestDto) throws MessageException {
        User user = getUserById(id);
        if (Objects.nonNull(userRequestDto.getEmail())) user.setEmail(userRequestDto.getEmail()); // if user email is not null set the new one
        if (Objects.nonNull(userRequestDto.getName())) user.setName(userRequestDto.getName()); // if username is not null set the new one
        if (Objects.nonNull(userRequestDto.getBalance())) user.setBalance(userRequestDto.getBalance()); // if user balance is not null set the new one
        return user;
    }

    @Override
    public List<User> retreiveAllUsers() {
        return userRepo.findAll();
    }
    @Override
    public User getUserById(Long id) throws MessageException {
        return userRepo.findById(id).orElseThrow( () ->
                 new MessageException("user with id " + id + " doesn't exist"));
    }

    @Transactional
    @Override
    public Map<String,Integer>  confirmOrderByUserId (Long userId) throws MessageException {

        Cart cart = cartService.getCartByUserId(userId);
        User user  = cart.getUser();


        Map<String,Integer> productsDetails = new HashMap<>();
        // for loop to map all products name and their quantity ordered by the user
        for (int i = 0; i < cart.getProductsQuantities().size(); i++) {
            productsDetails.put(cart.getProducts().get(i).getName(),cart.getProductsQuantities().get(i));
        }


        if (cartService.checkUserBalanceByProductsTotalPrice(cart.getId())){ // if the user balance is sufficient to buy these products

            List<Product> products = new ArrayList<>(); // new array list for all products to be updated
            for (int i = 0; i < cart.getProducts().size(); i++) { // for each product in array list of products inside the cart
                // update their total quantity in the store using the function in the product service
                Product product =   productService.updateProductQuantity(cart.getProducts().get(i).getId(),cart.getProductsQuantities().get(i));
                products.add(product); // add product already updated in the array list
            }

            user.setBalance(user.getBalance() - cart.getTotalPrice()); // update the user balance after order confirmation
            cartService.deleteAllProductsFromCartById(cart.getId()); // delete all products from the cart

            // here i  update all products quantites in the other carts
           for (Product product: products ) {  // for each product

                for (Cart cart1: product.getCarts()) { // retreive all carts for a single product
                    if (!cart1.getProducts().isEmpty()) { // if product list in cart1 is not empty
                        int indexProduct = cart1.getProducts().indexOf(product); // retreive index of single product in cart
                        // if the product quantity in cart is greater than the actual product total quantity
                        if (cart1.getProductsQuantities().get(indexProduct) > productService.getProductById(product.getId()).getTotalQuantity())
                        {
                            // update the product quantity in the cart
                            cart1.getProductsQuantities().set(indexProduct, product.getTotalQuantity());
                        }
                    }
                }

            }
            return productsDetails;

        }

        // if the user balance is not sufficient to do the operation throw an exception
        else throw  new MessageException("user balance: " + cart.getUser().getBalance() +  " is less than total price: " + cart.getTotalPrice());
    }
}
