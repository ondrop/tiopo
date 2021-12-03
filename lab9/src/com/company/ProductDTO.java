package com.company;

import com.google.gson.annotations.SerializedName;

public class ProductDTO {

    @SerializedName("id")
    private Integer id;

    @SerializedName("category_id")
    private Integer categoryId;

    @SerializedName("title")
    private String title;

    @SerializedName("alias")
    private String alias;

    @SerializedName("content")
    private String content;

    @SerializedName("price")
    private Integer price;

    @SerializedName("old_price")
    private Integer oldPrice;

    @SerializedName("status")
    private Integer status;

    @SerializedName("keywords")
    private String keywords;

    @SerializedName("description")
    private String description;

    @SerializedName("hit")
    private Integer hit;

    public Integer getId() {
        return id;
    }

    public ProductDTO setId(Integer id) {
        this.id = id;

        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public ProductDTO setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;

        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductDTO setTitle(String title) {
        this.title = title;

        return this;
    }

    public String getAlias() {
        return alias;
    }

    public ProductDTO setAlias(String alias) {
        this.alias = alias;

        return this;
    }

    public String getContent() {
        return content;
    }

    public ProductDTO setContent(String content) {
        this.content = content;

        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductDTO setPrice(Integer price) {
        this.price = price;

        return this;
    }

    public Integer getOldPrice() {
        return oldPrice;
    }

    public ProductDTO setOldPrice(Integer oldPrice) {
        this.oldPrice = oldPrice;

        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ProductDTO setStatus(Integer status) {
        this.status = status;

        return this;
    }

    public String getKeywords() {
        return keywords;
    }

    public ProductDTO setKeywords(String keywords) {
        this.keywords = keywords;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDTO setDescription(String description) {
        this.description = description;

        return this;
    }

    public Integer getHit() {
        return hit;
    }

    public ProductDTO setHit(Integer hit) {
        this.hit = hit;

        return this;
    }
}
