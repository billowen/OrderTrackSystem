package com.github.billowen.ordertracker.dao;

import com.github.billowen.ordertracker.model.Product;
import com.github.billowen.ordertracker.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    ProductItem findByProductAndSize(Product product, String size);
    List<ProductItem> findByProduct(Product product);
}
