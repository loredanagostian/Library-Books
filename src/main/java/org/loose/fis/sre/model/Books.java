package org.loose.fis.sre.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Books {
    private String id;
    private String title;
    private String author;
    private Integer forBuy;
    private Integer stock;
    private Integer forRent;
    private String availability;
    private Integer price;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getForBuy() {
        return forBuy;
    }

    public void setForBuy(Integer forBuy) {
        this.forBuy = forBuy;
    }

    public Integer getForRent() {
        return forRent;
    }

    public void setForRent(Integer forRent) {
        this.forRent = forRent;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public static void setBook(Books book, ResultSet resultSet) throws SQLException {
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPrice(resultSet.getInt("price"));

        if(resultSet.getInt("availability") == 1)
            book.setAvailability("Available");
        else
            book.setAvailability("NOT available");

        book.setStock(resultSet.getInt("stock"));
        book.setForRent(resultSet.getInt("for_rent"));
        book.setForBuy(resultSet.getInt("for_buy"));
        book.setDescription(resultSet.getString("description"));
    }
}
