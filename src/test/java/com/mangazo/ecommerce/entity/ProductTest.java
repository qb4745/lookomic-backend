package com.mangazo.ecommerce.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
    }

    @Test
    @DisplayName("Setting and getting ID")
    public void id() {
        Long id = 1L;
        product.setId(id);
        assertEquals(id, product.getId());
    }

    @Test
    @DisplayName("Setting and getting category")
    public void category() {
        ProductCategory category = new ProductCategory();
        product.setCategory(category);
        assertEquals(category, product.getCategory());
    }

    @Test
    @DisplayName("Setting and getting SKU")
    public void sku() {
        String sku = "Test SKU";
        product.setSku(sku);
        assertEquals(sku, product.getSku());
    }

    @Test
    @DisplayName("Setting and getting name")
    public void name() {
        String name = "Test Name";
        product.setName(name);
        assertEquals(name, product.getName());
    }

    @Test
    @DisplayName("Setting and getting description")
    public void description() {
        String description = "Test Description";
        product.setDescription(description);
        assertEquals(description, product.getDescription());
    }

    @Test
    @DisplayName("Setting and getting unit price")
    public void unitPrice() {
        int unitPrice = 100;
        product.setUnitPrice(unitPrice);
        assertEquals(unitPrice, product.getUnitPrice());
    }

    @Test
    @DisplayName("Setting and getting image URL")
    public void imageUrl() {
        String imageUrl = "Test URL";
        product.setImageUrl(imageUrl);
        assertEquals(imageUrl, product.getImageUrl());
    }

    @Test
    @DisplayName("Setting and getting active status")
    public void active() {
        product.setActive(true);
        assertTrue(product.isActive());
    }

    @Test
    @DisplayName("Setting and getting units in stock")
    public void unitsInStock() {
        int unitsInStock = 10;
        product.setUnitsInStock(unitsInStock);
        assertEquals(unitsInStock, product.getUnitsInStock());
    }
}