package org.loose.fis.sre.model;

import java.util.Objects;

public class CartItems {
    private String title;
    private String author;
    private Integer price;
    private String client;

    public CartItems(String title, String author, Integer price, String client) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.client = client;
    }

    public CartItems() {

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getClient() { return client; }

    public void setClient(String client) { this.client = client; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItems)) return false;
        CartItems cartItems = (CartItems) o;
        return title.equals(cartItems.title) && author.equals(cartItems.author) && price.equals(cartItems.price) && client.equals(cartItems.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, price, client);
    }
}
