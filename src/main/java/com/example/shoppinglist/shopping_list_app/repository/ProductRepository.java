package com.example.shoppinglist.shopping_list_app.repository;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT MAX(p.positionOnTheList) FROM Product p")
    Integer findMaxPosition();

    List<Product> findAllByOrderByPositionOnTheListAsc();

    @Modifying
    @Query("UPDATE Product p SET p.positionOnTheList = p.positionOnTheList - 1 WHERE p.positionOnTheList > :position")
    void decrementPositionsAbove(@Param("position") int position);
}
