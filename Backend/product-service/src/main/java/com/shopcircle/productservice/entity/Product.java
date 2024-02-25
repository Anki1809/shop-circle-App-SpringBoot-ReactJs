package com.shopcircle.productservice.entity;

import com.shopcircle.productservice.DTO.ProductInfo;
import com.shopcircle.productservice.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Entity
@Table(name = "Product", indexes = {
        @Index(name = "idx_product_product_id", columnList = "product_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "image_link", nullable = false, length = 2000)
    private String imageLink;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "quantity_history", nullable = false)
    private String quantityHistory;

    @Column(name = "current_quantity", nullable = false)
    private Long currentQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "creation_date")
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private LocalDateTime LastModifiedDate;

    public void setName(String name) {
        this.name = name.toLowerCase().trim();
    }

    public void setCategory(String category) {
        this.category = category.toLowerCase().trim();
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink.toLowerCase().trim();
    }

    public static ProductInfo ProductToProductInfo(Product product){
        return new ProductInfo(product.getProductId(),product.getName(),product.getCategory(),product.getImageLink(),product.getShopId(),
                product.getUnitPrice(),product.getQuantityHistory(),product.getCurrentQuantity(),product.status.name(),product.getCreationDate(),
                product.getLastModifiedDate());
    }

}
