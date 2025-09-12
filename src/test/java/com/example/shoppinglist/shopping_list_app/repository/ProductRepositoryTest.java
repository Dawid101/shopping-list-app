package com.example.shoppinglist.shopping_list_app.repository;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldReturnNumberOfLastProductOnTheList() {
        Product product = new Product("Kawa",1,1);
        Product product1 = new Product("Mleko",3,2);

        testEntityManager.persistAndFlush(product);
        testEntityManager.persistAndFlush(product1);


        Integer maxPosition = productRepository.findMaxPosition();
        assertEquals(2,maxPosition);
    }
}