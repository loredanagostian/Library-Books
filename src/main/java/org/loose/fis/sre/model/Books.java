package org.loose.fis.sre.model;

import java.util.Objects;

public class Books {
    private Integer id;
    private String title;
    private String author;
    private Integer forBuy;
    private Integer stock;
    private Integer forRent;
    private Integer availability;
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getForBuy() {
        return forBuy;
    }

    public void setForBuy(Integer forBuy) {
        this.forBuy = forBuy;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getForRent() {
        return forRent;
    }

    public void setForRent(Integer forRent) {
        this.forRent = forRent;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Books)) return false;
        Books books = (Books) o;
        return Objects.equals(id, books.id) && Objects.equals(title, books.title) && Objects.equals(author, books.author) && Objects.equals(forBuy, books.forBuy) && Objects.equals(stock, books.stock) && Objects.equals(forRent, books.forRent) && Objects.equals(availability, books.availability) && Objects.equals(price, books.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, forBuy, stock, forRent, availability, price);
    }

    public Books() {
    }
}
