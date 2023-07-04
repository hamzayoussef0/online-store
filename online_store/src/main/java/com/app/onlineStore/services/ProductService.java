package com.app.onlineStore.services;

import com.app.onlineStore.Exception.MessageException;
import com.app.onlineStore.dtos.requestDto.ProductRequestDto;
import com.app.onlineStore.dtos.responseDto.ProductResponseDto;
import com.app.onlineStore.models.Product;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addProductToStore(ProductRequestDto productRequestDto, Long idStockManager) throws MessageException;
    public ProductResponseDto editProductById(Long idProduct,ProductRequestDto productRequestDto, Long idStockManager) throws MessageException;
    public Product getProductById (Long id) throws MessageException;
    public List<ProductResponseDto> addListOfProducts (List<ProductRequestDto> productRequestDtos,Long idStockManager) throws MessageException;
    public Product updateProductQuantity (Long id, Integer quantity) throws MessageException;
    public List<ProductResponseDto> retreiveAllProducts ();
    public boolean checkQuantityAvailableByIdProduct (Long id,Integer quantity) throws MessageException;
    public void removeProductByStockManagerId(Long idProduct, Long idStockManager) throws MessageException;
    public void removeProductById(Long idProduct) throws MessageException;



}
