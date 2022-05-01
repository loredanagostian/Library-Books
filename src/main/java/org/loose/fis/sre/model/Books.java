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

    public Books (Integer id, String title, String author, boolean forBuy, Integer stock, boolean forRent, boolean availability){
        this.id = id;
        this.title = title;
        this.author = author;
        this.forBuy = forBuy;
        this.stock = stock;
        this.forRent = forRent;
        this.availability = availability;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Books book = (Books) o;

        if (!Objects.equals(id, book.id)) return false;
        if (!Objects.equals(title, book.title)) return false;
        if (author != null ? author.equals(book.author) : book.author != null) return false;
        if (book.forBuy) return false;
        if (stock != null ? stock.equals(book.stock) : book.stock != null) return false;
        if (book.forRent) return false;

        return book.availability;
    }
}
