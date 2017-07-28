package com.stock.repositories;

import com.stock.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nick on 6/26/17.
 */
public interface ProductRepositories extends JpaRepository<Product, Integer> {
}
