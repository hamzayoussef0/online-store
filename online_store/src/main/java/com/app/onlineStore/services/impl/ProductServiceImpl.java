package com.app.onlineStore.services.impl;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.Mapper;
import com.app.onlineStore.dtos.requestDto.ProductRequestDto;
import com.app.onlineStore.dtos.responseDto.ProductResponseDto;
import com.app.onlineStore.models.StockManager;
import com.app.onlineStore.models.Product;
import com.app.onlineStore.repositories.ProductRepo;
import com.app.onlineStore.services.StockManagerService;
import com.app.onlineStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private  ProductRepo productRepo;
    private StockManagerService stockManagerService;
    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, StockManagerService stockManagerService) {
        this.productRepo = productRepo;
        this.stockManagerService = stockManagerService;


    }
    @Transactional
    @Override
    public ProductResponseDto addProductToStore(ProductRequestDto productRequestDto, Long idStockManager) throws MessageException {
     Product productByName  =  productRepo.findByName(productRequestDto.getName()); // find user by name
        StockManager stockManager = stockManagerService.getStockManagerById(idStockManager);
        if(stockManager.isLogged() == true) { // if stock manager is logged in
            if (Objects.isNull(productByName)) { // if this name product doesn't  exist in store

                Product product = new Product();
                product.setCarts(new ArrayList<>()); // set an empty cart list
                product.setName(productRequestDto.getName());
                product.setProductPrice(productRequestDto.getPrice());
                product.setTotalQuantity(productRequestDto.getQuantity());
                productRepo.save(product);
                return Mapper.productToProductResponseDto(product);
            } else { // if product by name is already exist
                if (Objects.nonNull(productRequestDto.getPrice()) && productByName.getProductPrice() != productRequestDto.getPrice()) { // if the price is not null and is different
                    throw new MessageException("there is a product with the same name and a different price");
                }

                if (Objects.nonNull(productRequestDto.getQuantity())) { // if product quantity is not null
                    productByName.setTotalQuantity(productByName.getTotalQuantity() + productRequestDto.getQuantity()); // update  the quantity
                }
                return Mapper.productToProductResponseDto(productByName);
            }
        }
        else throw  new MessageException("stock manager with id " + stockManager.getId() + " is not logged in"); // if stock manager is not logged in throw a new exception
    }

    @Transactional
    @Override
    public ProductResponseDto editProductById(Long idProduct,ProductRequestDto productRequestDto,Long idStockManager) throws MessageException {
        Product product = getProductById(idProduct);
        StockManager stockManager = stockManagerService.getStockManagerById(idStockManager);
        if (stockManager.isLogged() == true) { // if stock manager is logged in
            if (Objects.nonNull(productRequestDto.getName()))
                product.setName(productRequestDto.getName()); // if product name is not null
            if (Objects.nonNull(productRequestDto.getQuantity())) // update product name
                product.setTotalQuantity(productRequestDto.getQuantity()); // if product quantity is not null
            if (Objects.nonNull(productRequestDto.getPrice())) // update product quantity
                product.setProductPrice(productRequestDto.getPrice()); // if produt price is not null
            return Mapper.productToProductResponseDto(product); // update product price
        }
        else throw  new MessageException("stock manager with id " + stockManager.getId() + " is not logged in"); // throw exception stock manager is not logged in
    }


    @Override
    public Product getProductById(Long id) throws MessageException {
        return productRepo.findById(id).orElseThrow(()->new MessageException("product whith id " + id + " not exist"));
    }

    /**
     * add to the store a list of products
     * @param productRequestDtos
     * @return
     */
    @Override
    public List<ProductResponseDto> addListOfProducts(List<ProductRequestDto> productRequestDtos,Long idStockManager) throws MessageException {
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        StockManager stockManager = stockManagerService.getStockManagerById(idStockManager);
        if (stockManager.isLogged() == true) { // if stock manager is logged in
            for (ProductRequestDto product : productRequestDtos) { // for each product in the list in input
                productResponseDtos.add(addProductToStore(product, idStockManager)); // i call the function add a single product
            }
            return productResponseDtos;
        }
        else throw  new MessageException("stock manager with id " + stockManager.getId() + " is not logged in");  // throw exception stock manager is not logged in
    }

    /**
     * function to update the total quantity of a specific product in store when it is taken from cart by order confiration
     * @param id
     * @param quantityInUse by a specific cart
     * @return
     */
    @Transactional
    @Override
    public Product updateProductQuantity(Long id, Integer quantityInUse) throws MessageException {
       Product product = getProductById(id);
       product.setTotalQuantity( product.getTotalQuantity() - quantityInUse); //update total quantity with the (old one - quanitty in use)
       if (product.getTotalQuantity() < 0 ) product.setTotalQuantity(0); // more sicurity minimum quantity value is zero
      return product;
    }


    @Override
    public List<ProductResponseDto> retreiveAllProducts() {
        List<Product> products = new ArrayList<>();
        for (Product product: productRepo.findAll() ) {
            products.add(product);
        }
        return Mapper.productsToProductsResponseDtos(products);
    }

    /**
     * check if the product quantity requested by a specific user is available in store
     * @param idProdut
     * @param quantity
     * @return
     */
    @Override
    public boolean checkQuantityAvailableByIdProduct(Long idProdut, Integer quantity) throws MessageException {
        Product product = getProductById(idProdut);
        if (product.getTotalQuantity() >= quantity) return true; // if product quantity is equal to the quantity request
        return false;
    }


    @Transactional
    @Override
    // remove product manually by a specific stock manager
    public void removeProductByStockManagerId(Long idProduct, Long idStockManager) throws MessageException {
        Product product = getProductById(idProduct);
        StockManager stockManager = stockManagerService.getStockManagerById(idStockManager);
        if (stockManager.isLogged() == true) { // if stock manager is logged in
            product.removeAllCarts(); // remove all carts from the product
            productRepo.delete(product); // delete product
        }
        else throw  new MessageException("stock manager with id " + stockManager.getId() + " is not logged in"); // throw exception stock manager is not logged i
    }
    @Transactional
    @Override
    // remove product automatically by his id
    public void removeProductById (Long idProduct) throws MessageException{
        Product product = getProductById(idProduct);
        product.removeAllCarts();
        productRepo.delete(product);

    }



}
