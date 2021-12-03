package com.company.test;

import com.company.ProductDTO;
import com.company.ProductValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ProductValidatorTest {

    private ProductDTO getDefaultProductDTO() {
        return (new ProductDTO()).setCategoryId(1).setStatus(0).setHit(0);
    }

    @Test
    public void checkWhenCategoryIdEqual0() {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setCategoryId(0);
        assertThrows(Throwable.class, () -> ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenCategoryIdEqual16() {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setCategoryId(16);
        assertThrows(Throwable.class, () -> ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenCategoryIdEqual1() throws Exception {
        ProductDTO productDTO = getDefaultProductDTO();
        assertTrue(ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenCategoryIdEqual15() throws Exception {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setCategoryId(15);
        assertTrue(ProductValidator.isProductValid(productDTO));
    }
    @Test
    public void checkWhenStatusEqual0() throws Exception {
        ProductDTO productDTO = getDefaultProductDTO();
        assertTrue(ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenStatusEqual1() throws Exception {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setStatus(1);
        assertTrue(ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenStatusLessThan0() {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setStatus(-1);
        assertThrows(Throwable.class, () -> ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenStatusEqual2() {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setStatus(2);
        assertThrows(Throwable.class, () -> ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenHintEqual0() throws Exception {
        ProductDTO productDTO = getDefaultProductDTO();
        assertTrue(ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenHintEqual1() throws Exception {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setHit(1);
        assertTrue(ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenHintLessThan0() {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setHit(-1);
        assertThrows(Throwable.class, () -> ProductValidator.isProductValid(productDTO));
    }

    @Test
    public void checkWhenHintEqual2() {
        ProductDTO productDTO = getDefaultProductDTO();
        productDTO.setHit(2);
        assertThrows(Throwable.class, () -> ProductValidator.isProductValid(productDTO));
    }
}
