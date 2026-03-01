package org.lr5;

import java.time.LocalDate;

public class Composition {
    private final String name;
    private final Author author;
    private final Genres genre;
    private final LocalDate releaseDate;

    public Composition(String name, Author author, Genres genre, LocalDate date){
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.releaseDate = date;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Genres getGenre() {
        return genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}
