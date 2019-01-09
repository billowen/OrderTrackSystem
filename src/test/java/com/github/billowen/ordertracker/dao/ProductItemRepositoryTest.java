package com.github.billowen.ordertracker.dao;

import com.github.billowen.ordertracker.model.Product;
import com.github.billowen.ordertracker.model.ProductItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductItemRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Before
    public void prepareData() {
        Product product = new Product("p1", Arrays.asList("秋冬", "婚庆"), "秋冬");
        testEntityManager.persist(product);
        ProductItem item1 = new ProductItem("200X230", 250.0, product);
        ProductItem item2 = new ProductItem("180X200", 200.0, product);
        testEntityManager.persist(item1);
        testEntityManager.persist(item2);
        testEntityManager.flush();
    }

    @Test
    public void whenFindByProductAndSize_thenReturnProductItem() {
        Product product = productRepository.findByProductName("p1").get(0);
        ProductItem productItem = productItemRepository.findByProductAndSize(product, "180X200");

        assertThat(productItem.getSize(), is("180X200"));
    }

    @Test
    public void whenFindByProduct_thenReturnItemList() {
        Product product = productRepository.findByProductName("p1").get(0);
        List<String> items = productItemRepository.findByProduct(product)
                .stream().map(ProductItem::getSize)
                .collect(Collectors.toList());

        assertThat(items, hasSize(2));
        assertThat(items, hasItem("200X230"));
        assertThat(items, hasItem("180X200"));

    }
}