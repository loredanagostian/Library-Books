package org.loose.fis.sre.model;

import java.util.Objects;

public class HistoryBook {
    private String title;
    private String author;
    private Integer bought;
    private Integer rented;
    private Integer price;
    private Integer period;

    public HistoryBook(String title, String author, Integer bought, Integer rented, Integer price, Integer period) {
        this.title = title;
        this.author = author;
        this.bought = bought;
        this.rented = rented;
        this.price = price;
        this.period = period;
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

    public Integer getBought() {
        return bought;
    }

    public void setBought(Integer bought) {
        this.bought = bought;
    }

    public Integer getRented() {
        return rented;
    }

    public void setRented(Integer rented) {
        this.rented = rented;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryBook)) return false;
        HistoryBook that = (HistoryBook) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(bought, that.bought) && Objects.equals(rented, that.rented) && Objects.equals(price, that.price) && Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, bought, rented, price, period);
    }
}
