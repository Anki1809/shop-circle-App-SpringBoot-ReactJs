package com.shopcircle.productservice.service;

import com.shopcircle.productservice.DAO.ProductRepository;
import com.shopcircle.productservice.DTO.ProductDTO;
import com.shopcircle.productservice.DTO.ProductInfo;
import com.shopcircle.productservice.entity.Product;
import com.shopcircle.productservice.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String createProduct(ProductDTO productDTO)  {
        final Product product = ProductDTO.ProductDTOToProduct(productDTO);
        product.setQuantityHistory(productDTO.currentQuantity().toString());
        try {
            Product product1 = productRepository.save(product);
            return "Product successfully created with Product Id : "+product1.getProductId();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public ProductInfo getProduct(Long productId,Status status) throws Exception {
        final Optional<Product> product = productRepository.findByProductIdAndStatus(productId,status);
        if (product.isPresent()){
            return Product.ProductToProductInfo(product.get());
        }
        else throw new Exception("Product not found with id : "+productId);
    }

    @Override
    public String updateProduct(String name
            ,String category
            ,String imageLink
            ,Double unitPrice
            ,Status status
            ,Long productId) throws Exception {
        int rowAffected = productRepository.updateNameAndCategoryAndImageLinkAndUnitPriceAndStatusByProductId(
                name.trim().toLowerCase()
                ,category.trim().toLowerCase()
               ,imageLink.trim().toLowerCase()
                ,unitPrice
                ,status
                , productId
        );
        if (rowAffected != 0){
            return "Data successfully updated for product id "+productId;
        }
        else throw new Exception("Product not found with id : "+productId);
    }

    @Override
    public List<ProductInfo> getAllByShopId(Long shopId,Status status, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        final List<Product> products = productRepository.findByProductIdAndStatusOrderByProductIdDesc(shopId,status, pageable);
        return products.stream().map(Product::ProductToProductInfo).toList();
    }

    @Override
    public String addQuantityByProductId(Long productId, Long addQuantity) throws Exception {
        int rowAffected = productRepository.updateCurrentQuantityByProductId(
                addQuantity,productId);
        if (rowAffected != 0){
            return "Quantity successfully updated for product id "+productId;
        }
        else throw new Exception("Product not found with id : "+productId);
    }

    @Override
    public String subQuantityByProductId(Long productId, Long subQuantity) throws Exception {
        try {
            Long currQuantity = productRepository.findQuantityByProductId(productId);
            System.out.println(currQuantity + subQuantity);
            if (currQuantity + subQuantity >= 0) {
                productRepository.updateCurrentQuantityByProductId(
                        subQuantity, productId);
                return "Quantity successfully updated for product id " + productId;
            } else
                return "Current Quantity " + currQuantity + " is less then subtract quantity " + subQuantity + ".";
        }
        catch (Exception e){
            throw new Exception("Product not found with id : "+productId);
        }
    }

}
