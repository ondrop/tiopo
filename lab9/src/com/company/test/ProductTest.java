package com.company.test;

import com.company.ProductDTO;
import com.company.ProductValidator;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class ProductTest {
    ArrayList<Integer> productsForDelete = new ArrayList<>();

    final String BASE_URL = "http://91.210.252.240:9010/api/";

    final String GET_ALL_ENDPOINT = "products";
    final String DELETE_ENDPOINT = "deleteproduct?id=";
    final String ADD_ENDPOINT = "addproduct";
    final String UPDATE_ENDPOINT = "editproduct";

    final String DATA_PRODUCTS_DIR = "src/com/company/data/";
    final String VALID_PRODUCTS_DIR = "valid/";
    final String INVALID_NOT_ADDABLE_PRODUCTS_DIR = "invalid/not_addable/";
    final String INVALID_ADDABLE_PRODUCTS_DIR = "invalid/addable/";

    final String[] VALID_PRODUCT_FILE_NAMES = {
            "validProduct1.json",
            "validProduct2.json"
    };
    final String[] INVALID_ADDABLE_PRODUCT_FILE_NAMES = {
            "hitEqual-1.json",
            "hitEqual2.json",
            "statusEqual-1.json",
            "statusEqual2.json"
    };
    final String[] INVALID_NOT_ADDABLE_PRODUCT_FILE_NAMES = {
            "categoryIdEqual0.json",
            "categoryIdEqual16.json",
            "invalidTypes.json"
    };

    final String RESPONSE_BODY_STATUS = "status";
    final Integer RESPONSE_BODY_STATUS_OK = 1;
    final String RESPONSE_BODY_ID = "id";
    final int STATUS_OK = 200;

    @BeforeMethod
    public void beforeMethod(Method method) {
        Test test = method.getAnnotation(Test.class);
        System.out.println("Test name: " + method.getName());
        System.out.println("Test description: " + test.description());
    }

    @Test(description = "Get all products and check that they are valid")
    public void getAllProducts() throws Exception {
        Response response = given().get(BASE_URL + GET_ALL_ENDPOINT);
        assertEquals(STATUS_OK, response.getStatusCode());

        Gson gson = new Gson();
        ProductDTO[] products = gson.fromJson(response.asString(), ProductDTO[].class);
        assertThrows(Throwable.class, () -> {
            for (ProductDTO product: products) {
                ProductValidator.isProductValid(product);
            }
        });
    }

    @Test(description = "Add addable invalid products. Products with invalid range for status and hit can be added.")
    public void addAddableInvalidProducts() throws Exception {
        for (String fileName : INVALID_ADDABLE_PRODUCT_FILE_NAMES) {
            File productFromFile = new File(DATA_PRODUCTS_DIR + INVALID_ADDABLE_PRODUCTS_DIR + fileName);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(productFromFile)
                    .post(BASE_URL + ADD_ENDPOINT);

            assertEquals(response.getStatusCode(), STATUS_OK);

            JSONObject responseJson = new JSONObject(response.asString());
            assertEquals(RESPONSE_BODY_STATUS_OK, responseJson.get(RESPONSE_BODY_STATUS));

            Integer newProductId = (Integer) responseJson.get(RESPONSE_BODY_ID);
            ProductDTO productDTO = getProductById(newProductId);
            assertNotNull(productDTO, "Product by id " + newProductId + " not found");
        }
    }

    @Test(description = "Add not addable invalid products. Products with invalid category_id or invalid type of fields can be added.")
    public void addNotAddableInvalidProducts() throws Exception {
        for (String fileName : INVALID_NOT_ADDABLE_PRODUCT_FILE_NAMES) {
            File productFromFile = new File(DATA_PRODUCTS_DIR + INVALID_NOT_ADDABLE_PRODUCTS_DIR + fileName);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(productFromFile)
                    .post(BASE_URL + ADD_ENDPOINT);

            assertEquals(response.getStatusCode(), STATUS_OK);

            assertThrows(Throwable.class, () -> {
                JSONObject responseJson = new JSONObject(response.asString());
                Integer newProductId = null;
                if (responseJson.has(RESPONSE_BODY_ID)) {
                    newProductId = (Integer) responseJson.get(RESPONSE_BODY_ID);
                }

                ProductDTO productDTO = getProductById(newProductId);
                assertNotNull(productDTO, "Product by id " + newProductId + " not found");
            });
        }
    }

    @Test(description = "Add valid products. Check that product was added by getting product with received product id from response.")
    public void addValidProducts() {
        for (String fileName : VALID_PRODUCT_FILE_NAMES) {
            File productFromFile = new File(DATA_PRODUCTS_DIR + VALID_PRODUCTS_DIR + fileName);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(productFromFile)
                    .post(BASE_URL + ADD_ENDPOINT);

            assertEquals(response.getStatusCode(), STATUS_OK);

            JSONObject responseJson = new JSONObject(response.asString());
            assertEquals(RESPONSE_BODY_STATUS_OK, responseJson.get(RESPONSE_BODY_STATUS));

            Integer newProductId = (Integer) responseJson.get(RESPONSE_BODY_ID);
            ProductDTO productDTO = getProductById(newProductId);
            assertNotNull(productDTO, "Product by id " + newProductId + " not found.");
        }
    }

    @Test(description = "Update product. Product after update should have at least one different field value than before.")
    public void updateProductByValidValue() throws IOException {
        String jsonString = new String (Files.readAllBytes(Paths.get(DATA_PRODUCTS_DIR + VALID_PRODUCTS_DIR + "validProductForUpdate1.json")));
        ProductDTO productDTO = (new Gson()).fromJson(jsonString, ProductDTO.class);
        Integer productId = productDTO.getId();

        ProductDTO beforeUpdateProductDto = getProductById(productId);
        assertNotNull(beforeUpdateProductDto, "Product by id " + productId + " does not exist.");

        File productFromFile = new File(DATA_PRODUCTS_DIR + VALID_PRODUCTS_DIR + "validProductForUpdate1.json");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(productFromFile)
                .post(BASE_URL + UPDATE_ENDPOINT);

        assertEquals(response.getStatusCode(), STATUS_OK);

        JSONObject responseJson = new JSONObject(response.asString());
        assertEquals(RESPONSE_BODY_STATUS_OK, responseJson.get(RESPONSE_BODY_STATUS));

        ProductDTO afterUpdateProductDto = getProductById(productId);
        assertNotNull(afterUpdateProductDto, "Product by id " + productId + " was not updated.");

        assertNotEquals((new Gson()).toJson(beforeUpdateProductDto), (new Gson()).toJson(afterUpdateProductDto), "Product equals. Unexpected situation.");
    }

    @Test(description = "Send valid product twice. First product alias should be different from the second product alias.")
    public void checkAliasChanges() {
        File productFromFile = new File(DATA_PRODUCTS_DIR + VALID_PRODUCTS_DIR + "productForAlias.json");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(productFromFile)
                .post(BASE_URL + ADD_ENDPOINT);

        JSONObject responseJson = new JSONObject(response.asString());
        Integer firstProductId = (Integer) responseJson.get(RESPONSE_BODY_ID);
        ProductDTO firstProductDTO = getProductById(firstProductId);

        Response response2 = given()
                .contentType(ContentType.JSON)
                .body(productFromFile)
                .post(BASE_URL + ADD_ENDPOINT);

        JSONObject responseJson2 = new JSONObject(response2.asString());
        Integer secondProductId = (Integer) responseJson2.get(RESPONSE_BODY_ID);
        ProductDTO secondProductDTO = getProductById(secondProductId);

        assertNotEquals(firstProductDTO.getAlias(), secondProductDTO.getAlias(), "Aliases equals. Unexpected situation.");
    }

    @Test(
            description = "Delete product by id. After delete we check that this product does not exist in product list.",
            priority = 1
    )
    public void deleteProduct() throws Exception {
        List<Integer> uniquerProductIdsForDelete = productsForDelete.stream().distinct().collect(Collectors.toList());
        System.out.println(uniquerProductIdsForDelete);
        for (Integer productId : uniquerProductIdsForDelete) {
            if (getProductById(productId) == null) {
                throw new Exception("Product with id " + productId + " not exist.");
            }

            Response response = given().get(BASE_URL + DELETE_ENDPOINT + productId);

            assertNull(getProductById(productId), "Product with id " + productId + " was not removed.");
        }
    }

    public ProductDTO getProductById(Integer productId) {
        Response response = given().get(BASE_URL + GET_ALL_ENDPOINT);
        ProductDTO[] products = getProductFromJsonString(response.asString());

        ProductDTO receivedProduct = Arrays.stream(products).filter(product -> product.getId().equals(productId)).findFirst().orElse(null);
        if (receivedProduct != null) {
            productsForDelete.add(productId);
        }

        return receivedProduct;
    }

    public ProductDTO[] getProductFromJsonString(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, ProductDTO[].class);
    }
}
