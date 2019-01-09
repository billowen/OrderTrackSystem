package com.github.billowen.ordertracker.dao;

import com.github.billowen.ordertracker.model.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {
}
