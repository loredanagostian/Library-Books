package org.loose.fis.sre.model;

import java.util.Objects;

public class RentItem {
    private String title;
    private String author;
    private Integer period;

    public RentItem(String title, String author, Integer period) {
        this.title = title;
        this.author = author;
        this.period = period;
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentItem)) return false;
        RentItem rentItem = (RentItem) o;
        return Objects.equals(title, rentItem.title) && Objects.equals(author, rentItem.author) && Objects.equals(period, rentItem.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, period);
    }
}
