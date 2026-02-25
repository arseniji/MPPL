package org.example.editionsInterfase;

import java.util.Objects;

public class Book extends BasePrintEdition implements Calculatable {
    private String author;
    public Book(Integer printingNum, String language, Integer numPages, String author){
        super(printingNum,language,numPages);
        this.author = author;
    }
    @Override
    public Double calculateCost(Object... additionalParams){
        Integer baseSum = (Integer) additionalParams[0];
        Integer circulation = getNumPages();
        return (double) (baseSum + 500 * circulation);
    }

    @Override
    public String toString(){
        return super.toString() + String.format(", Автор = %s", author);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof org.example.editionsInterfase.Book)) return false;
        org.example.editionsInterfase.Book b = (org.example.editionsInterfase.Book) o;
        if (!super.equals(o)) return false;
        return Objects.equals(author,b.author);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),author);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
