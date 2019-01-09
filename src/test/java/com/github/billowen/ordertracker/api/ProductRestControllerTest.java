package com.github.billowen.ordertracker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.billowen.ordertracker.dao.ProductItemRepository;
import com.github.billowen.ordertracker.dao.ProductPictureRepository;
import com.github.billowen.ordertracker.dao.ProductRepository;
import com.github.billowen.ordertracker.model.Product;
import com.github.billowen.ordertracker.model.ProductItem;
import com.github.billowen.ordertracker.model.ProductPicture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductItemRepository productItemRepository;

    @MockBean
    private ProductPictureRepository productPictureRepository;

    @Test
    public void postNewProduct() throws Exception {
        Product product = new Product("p1", Collections.emptyList(), "tttt");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(product);
        when(productRepository.save(product)).thenReturn(product);

        MvcResult result =  mvc.perform(post("/api/product")
                .contentType(APPLICATION_JSON).content(json))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    public void postProductItems() throws Exception {
        Product product = new Product("p1", Collections.emptyList(), "tttt");
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        List<ProductItem> items = Arrays.asList(
                new ProductItem("180X200", 200.0),
                new ProductItem("200X230", 300.0)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(items);
        when(productItemRepository.saveAll(items)).thenReturn(items);

        MvcResult result = mvc.perform(post("/api/product/1/productItem")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

}