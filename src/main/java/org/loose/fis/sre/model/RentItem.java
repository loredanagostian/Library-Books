package org.loose.fis.sre.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RentItem {

    private String client;
    private String title;
    private String author;
    private Integer period;

    public RentItem(String client, String title, String author, Integer period) {
        this.client = client;
        this.title = title;
        this.author = author;
        this.period = period;
    }

    public RentItem() {

    }

    public String getClient() { return client; }

    public void setClient(String client) { this.client = client; }

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
        return Objects.equals(client, rentItem.client) && Objects.equals(title, rentItem.title) && Objects.equals(author, rentItem.author) && Objects.equals(period, rentItem.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, title, author, period);
    }

    public static void setRentBook (RentItem book, ResultSet resultSet) throws SQLException {

        book.setClient(resultSet.getString("username_client"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPeriod (resultSet.getInt("period_of_renting"));

    }
}
