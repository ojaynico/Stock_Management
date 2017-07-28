package com.stock.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by nick on 6/26/17.
 */
@Repository
public class ProductTemplate {

    private JdbcTemplate template;

    public ProductTemplate(JdbcTemplate template){
        this.template = template;
    }
}
