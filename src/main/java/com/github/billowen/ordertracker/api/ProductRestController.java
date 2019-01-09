package com.github.billowen.ordertracker.api;

import com.github.billowen.ordertracker.dao.ProductItemRepository;
import com.github.billowen.ordertracker.dao.ProductPictureRepository;
import com.github.billowen.ordertracker.dao.ProductRepository;
import com.github.billowen.ordertracker.model.Product;
import com.github.billowen.ordertracker.model.ProductItem;
import com.github.billowen.ordertracker.model.ProductPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    private ProductRepository productRepository;
    private ProductItemRepository productItemRepository;
    private ProductPictureRepository productPictureRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductItemRepository(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    @Autowired
    public void setProductPictureRepository(ProductPictureRepository productPictureRepository) {
        this.productPictureRepository = productPictureRepository;
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @PostMapping("/product/{productId}/productItem")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductItem> addProductItems(@PathVariable Long productId,
                                             @RequestBody List<ProductItem> items) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductId " + productId + " not found"));
        for (ProductItem item : items) {
            item.setProduct(product);
        }

        return productItemRepository.saveAll(items);
    }

    @PostMapping("/product/{productId}/pic")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProductPicture(@PathVariable Long productId,
                                       @RequestParam MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductId " + productId + " not found"));
        ProductPicture pic = new ProductPicture();
        pic.setBytes(file.getBytes());
        pic.setProduct(product);
        productPictureRepository.save(pic);
    }
}
