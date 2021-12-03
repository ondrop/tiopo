package com.company;

public class ProductValidator {

    public static boolean isProductValid(ProductDTO product) throws Exception {

        Integer categoryId = product.getCategoryId();
        boolean isCategoryIdValid = categoryId >= 1 && categoryId <= 15;
        if (!isCategoryIdValid) {
            throw new Exception("Category id is not valid. Must be a number from 1 to 15.");
        }

        Integer status = product.getStatus();
        boolean isStatusValid = status >= 0 && status <= 1;
        if (!isStatusValid) {
            throw new Exception("Status is not valid. Must be 0 or 1.");
        }

        Integer hit = product.getHit();
        boolean isHitValid = hit >= 0 && hit <= 1;
        if (!isHitValid) {
            throw new Exception("Hit is not valid. Must be 0 or 1.");
        }

        return true;
    }
}
