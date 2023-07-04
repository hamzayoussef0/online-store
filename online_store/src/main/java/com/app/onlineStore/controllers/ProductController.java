package com.app.onlineStore.controllers;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.requestDto.ProductRequestDto;
import com.app.onlineStore.dtos.responseDto.ProductResponseDto;
import com.app.onlineStore.models.Product;
import com.app.onlineStore.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {

    private  ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/{idStockManager}")
    public ProductResponseDto addProductToStore (@RequestBody  ProductRequestDto productRequestDto, @PathVariable Long idStockManager) throws MessageException {
        return productService.addProductToStore(productRequestDto, idStockManager);
    }
    @GetMapping("/{idProduct}")
    public Product getProductById (@PathVariable  Long idProduct) throws MessageException {
        return productService.getProductById(idProduct);
    }

    @GetMapping("/all")
    public List<ProductResponseDto> retreiveAllProducts (){
        return productService.retreiveAllProducts();
    }

    @DeleteMapping("/{idProduct}")
    public void removeProductById (@PathVariable Long idProduct) throws MessageException {
         productService.removeProductById(idProduct);
    }

    @PostMapping("/list/{idStockManager}")
    public List<ProductResponseDto> addListOfProducts(@RequestBody List<ProductRequestDto> productRequestDtos, @PathVariable Long idStockManager) throws MessageException {
    return productService.addListOfProducts(productRequestDtos, idStockManager);
    }

    @PutMapping("/{idProduct}/{idStockManager}")
    public ProductResponseDto editProductById(@PathVariable Long idProduct,@RequestBody ProductRequestDto productRequestDto,@PathVariable Long idStockManager) throws MessageException{
        return productService.editProductById(idProduct, productRequestDto,idStockManager);
    }



}

