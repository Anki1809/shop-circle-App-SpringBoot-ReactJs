package com.shopcircle.productservice.DTO;

import com.shopcircle.productservice.entity.Product;
import com.shopcircle.productservice.enums.Status;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@Validated
public record ProductDTO(
                         @NotBlank(message = "Enter valid name.") @Size(min = 3, max = 100, message = "Enter name between 3 to 100 characters.") String name,
                         @NotBlank(message = "Enter valid category.") @Size(min = 3, max = 100, message = "Enter category between 3 to 100 characters.") String category,
                         @NotBlank(message = "Enter valid image link.") String imageLink,
                         @Positive(message = "Shop id must be positive.") Long shopId,
                         @DecimalMin(value = "1.0", message = "Unit price must be greater than or equal to 1.") @DecimalMax(value = "100000.0", message = "Unit price must be less than or equal to 100000.") Double unitPrice,
                         @Positive(message = "Quantity must be positive.") @Max(value = 10000, message = "Quantity must be less than or equal to 10000.") Long currentQuantity,
                         @NotNull(message = "Status cannot be null.") Status status) implements Serializable {

    public ProductDTO(String name, String category, String imageLink, Long shopId, Double unitPrice, Long currentQuantity, Status status) {

        this.name = name;
        this.category = category;
        this.imageLink = imageLink;
        this.shopId = shopId;
        this.unitPrice = unitPrice;
        this.currentQuantity = currentQuantity;
        this.status = status;
    }

    // Getters and setters can be generated automatically by the record

    public static Product ProductDTOToProduct(ProductDTO productDTO) {
        return new Product( null,productDTO.name(), productDTO.category(), productDTO.imageLink(),
                productDTO.shopId(), productDTO.unitPrice(), null, productDTO.currentQuantity(),
                productDTO.status(), null, null);
    }
}
