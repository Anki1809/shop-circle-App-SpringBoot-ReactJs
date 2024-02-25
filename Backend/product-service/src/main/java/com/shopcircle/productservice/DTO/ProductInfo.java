package com.shopcircle.productservice.DTO;

import com.shopcircle.productservice.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.shopcircle.productservice.entity.Product}
 */
public record ProductInfo(Long productId, String name, String category, String imageLink, Long shopId, Double unitPrice,
                          String quantityHistory, Long currentQuantity, String status, LocalDateTime creationDate,
                          LocalDateTime LastModifiedDate) implements Serializable {
}