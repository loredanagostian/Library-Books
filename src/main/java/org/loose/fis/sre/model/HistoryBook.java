package org.loose.fis.sre.model;

import java.util.Objects;

public class HistoryBook {
    private String title;
    private String author;
    private String bought;
    private String rented;
    private Integer price;
    private Integer period;
    private String client;


    public HistoryBook(String title, String author, Integer bought, Integer rented, Integer price, Integer period, String client) {
        this.title = title;
        this.author = author;
        this.bought = String.valueOf(bought);
        this.rented = String.valueOf(rented);
        this.price = price;
        this.period = period;
        this.client = client;
    }

    public HistoryBook() {

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

    public String getBought() { return bought; }

    public void setBought(String bought) { this.bought = bought; }

    public String getRented() { return rented; }

    public void setRented(String rented) { this.rented = rented; }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getClient() { return client; }

    public void setClient(String client) { this.client = client; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryBook)) return false;
        HistoryBook that = (HistoryBook) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(bought, that.bought) && Objects.equals(rented, that.rented) && Objects.equals(price, that.price) && Objects.equals(period, that.period) && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, bought, rented, price, period, client);
    }
}
