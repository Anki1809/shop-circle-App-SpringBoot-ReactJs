package com.shopcircle.productservice.DAO;

import com.shopcircle.productservice.entity.Product;
import com.shopcircle.productservice.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p.currentQuantity from Product p where p.productId = :productId")
    Long findQuantityByProductId(@Param("productId") @NonNull Long productId);

    @Transactional
    @Modifying
    @Query("""
            update Product p 
            set p.quantityHistory = concat( p.quantityHistory ,',',:currentQuantity)
            , p.currentQuantity = p.currentQuantity+ :currentQuantity
            where p.productId = :productId""")
    int updateCurrentQuantityByProductId(@NonNull @Param("currentQuantity") Long currentQuantity, @NonNull @Param("productId") Long productId);
    //List<Product> getByShopId(Long shopId, Pageable pageable);
    



    @Query("select p from Product p where p.shopId = :shopId and p.status = COALESCE(:status,p.status) order by p.productId DESC")
    List<Product> findByProductIdAndStatusOrderByProductIdDesc(@Param("shopId") @NonNull Long shopId, @Param("status") @Nullable Status status, Pageable pageable);
    //Optional<Product> findByProductId(Long productId);

    @Query("select p from Product p where p.productId = :productId and p.status = COALESCE(:status,p.status)")
    Optional<Product> findByProductIdAndStatus(@Param("productId") @NonNull Long productId, @Param("status") @Nullable Status status);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Product p SET
                p.name = COALESCE(:name, p.name),
                p.category = COALESCE(:category, p.category),
                p.imageLink = COALESCE(:imageLink, p.imageLink),
                p.unitPrice = COALESCE(:unitPrice, p.unitPrice),
                p.status = COALESCE(:status, p.status)
            WHERE
                p.productId = :productId""")
    int updateNameAndCategoryAndImageLinkAndUnitPriceAndStatusByProductId(@Nullable @Param("name") String name
            , @Nullable @Param("category") String category
            , @Nullable @Param("imageLink") String imageLink
            , @Nullable @Param("unitPrice") Double unitPrice
            , @Nullable @Param("status") Status status
            , @Param("productId") Long productId);


}