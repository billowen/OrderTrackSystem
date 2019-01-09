package com.github.billowen.ordertracker.dao;

import com.github.billowen.ordertracker.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByTag_thenReturnProduct() {
        Product product = new Product("p1", Arrays.asList("秋"), "秋被");
        testEntityManager.persist(product);
        testEntityManager.flush();

        List<Product> products = productRepository.findByTags("秋");

        assertThat(products, hasSize(1));
        assertThat(products.get(0).getProductName(), is(product.getProductName()));
    }
}