package com.shopcircle.productservice.service;

import com.shopcircle.productservice.DTO.ProductDTO;
import com.shopcircle.productservice.DTO.ProductInfo;
import com.shopcircle.productservice.enums.Status;

import java.util.List;

public interface ProductService {

    String createProduct(ProductDTO productDTO);
    ProductInfo getProduct(Long productId, Status status) throws Exception;
    String updateProduct(String name,String category,String imageLink,Double unitPrice,Status status,Long productId) throws Exception;
    List<ProductInfo> getAllByShopId(Long shopId,Status status, Integer pageNo, Integer pageSize);
    String addQuantityByProductId(Long productId, Long addQuantity) throws Exception;
    String subQuantityByProductId(Long productId, Long subQuantity) throws Exception;


}
