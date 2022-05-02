package org.loose.fis.sre.model;

import java.util.Objects;

public class Books {
    private Integer id;
    private String title;
    private String author;
    private boolean forBuy;
    private Integer stock;
    private boolean forRent;
    private boolean availability;
    private Integer price;

    public Books (Integer id, String title, String author, boolean forBuy, Integer stock, boolean forRent, boolean availability, Integer price){
        this.id = id;
        this.title = title;
        this.author = author;
        this.forBuy = forBuy;
        this.stock = stock;
        this.forRent = forRent;
        this.availability = availability;
        this.price = price;
    }

    public Books() {

    }

    //GETTER
    public Integer getId (){
        return this.id;
    }

    public String getTitle (){
        return this.title;
    }

    public String getAuthor (){
        return this.author;
    }

    public Boolean getForBuy (){
        return this.forBuy;
    }

    public Boolean getForRent (){
        return this.forRent;
    }

    public Integer getStock (){
        return this.stock;
    }

    public Boolean getAvailability (){
        return this.availability;
    }

    public Integer getPrice() { return this.price; }

    //SETTER
    public void setId (Integer id){
        this.id = id;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public void setAuthor (String author){
        this.author = author;
    }

    public void setForBuy (boolean forBuy){
        this.forBuy = forBuy;
    }

    public void setForRent (boolean forRent){
        this.forRent = forRent;
    }

    public void setStock (Integer stock){
        this.stock = stock;
    }

    public void setAvailability (boolean availability){
        this.availability = availability;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Books)) return false;
        Books books = (Books) o;
        return forBuy == books.forBuy && forRent == books.forRent && availability == books.availability && Objects.equals(id, books.id) && Objects.equals(title, books.title) && Objects.equals(author, books.author) && Objects.equals(stock, books.stock) && Objects.equals(price, books.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, forBuy, stock, forRent, availability, price);
    }
}
