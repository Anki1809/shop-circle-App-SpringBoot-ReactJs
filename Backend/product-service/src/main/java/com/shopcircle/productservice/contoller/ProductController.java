package com.shopcircle.productservice.contoller;

import com.shopcircle.productservice.DTO.ProductDTO;
import com.shopcircle.productservice.DTO.ProductInfo;
import com.shopcircle.productservice.enums.Status;
import com.shopcircle.productservice.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/status")
    public String getMessage(){
        return  "Product service is working.";
    }

    @PostMapping("/")
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{productId}/{status}")
    public ResponseEntity<Object> getProduct(@Valid @Positive(message = "Product id must be positive.") @PathVariable Long productId
    ,@PathVariable Integer status){
        try {
            return new ResponseEntity<>(productService.getProduct(productId,status==1?Status.OPEN:Status.CLOSED),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{productId}")
    ResponseEntity<Object> updateProduct(
            @RequestParam(required = false) String name
            , @RequestParam(required = false)  String category
            , @RequestParam(name = "imagelink",required = false) String imageLink
            , @RequestParam(name = "unitprice",required = false) Double unitPrice
            , @RequestParam(required = false)  String status
            , @Valid @Positive(message = "Product id must be positive.") @PathVariable Long productId){
        try {
            return new ResponseEntity<>(productService.updateProduct( name,category,imageLink,unitPrice,Status.valueOf(status.toUpperCase().trim()), productId), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{shopId}/{status}/{pageNo}/{pageSize}")
    ResponseEntity<List<ProductInfo>> getAllByShopId(
            @Valid @Positive(message = "Shop id must be positive.") @PathVariable Long shopId,
            @PathVariable Integer status,
            @Min(value = 1, message = "Page number must be positive.") @PathVariable Integer pageNo,
            @Min(value = 1, message = "Min size must be greater then 1.") @Max(value = 100, message = "Page size must be less then or equal to 100.")  @PathVariable Integer pageSize) {

        return new ResponseEntity<>(productService.getAllByShopId(shopId,status==0?null:status==1?Status.OPEN:Status.CLOSED, pageNo-1, pageSize), HttpStatus.OK);
    }

    @PutMapping("/add/{productId}/{addQuantity}")
    ResponseEntity<String> addQuantityByProductId(@Valid @Positive(message = "Product id must be positive.") @PathVariable Long productId,
                                                  @Valid @Min(value = 1, message = "Quantity number must be positive.") @PathVariable Long addQuantity){
        try {
            return new ResponseEntity<>(productService.addQuantityByProductId(productId,addQuantity),HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/sub/{productId}/{subQuantity}")
    ResponseEntity<String> subQuantityByProductId(@Valid @Positive(message = "Product id must be positive.") @PathVariable Long productId,
                                                  @Valid @Min(value = 1, message = "Quantity number must be positive.") @PathVariable Long subQuantity){
        try{
            return new ResponseEntity<>(productService.subQuantityByProductId(productId,-subQuantity),HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
