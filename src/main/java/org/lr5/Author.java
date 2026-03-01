package org.lr5;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Author {
    private final String name;
    private final boolean isGroup;
    private final LocalDate foundingDate;
    private final String country;
    private final List<String> members;
    private final String spotifyUrl;
    private final String yandexMusixUrl;

    private Author(AuthorBuilder builder){
        this.name = builder.name;
        this.isGroup = builder.isGroup;
        this.foundingDate = builder.foundingDate;
        this.country = builder.country;
        this.members = builder.members != null ? List.copyOf(builder.members) : List.of();
        this.spotifyUrl = builder.spotifyUrl;
        this.yandexMusixUrl = builder.yandexMusixUrl;
    }

    public String getName() {
        return name;
    }
    public boolean isGroup() {
        return isGroup;
    }
    public LocalDate getFoundingDate() {
        return foundingDate;
    }
    public String getCountry() {
        return country;
    }
    public List<String> getMembers() {
        return members;
    }
    public int getMembersCount() {
        return members.size();
    }
    public String getSpotifyUrl() {
        return spotifyUrl;
    }
    public String getYandexMusixUrl() {
        return yandexMusixUrl;
    }

    @Override
    public String toString(){
        return "Автор: " +
                "Название - " + name +
                "Группа - " + isGroup;
    }

    public static class AuthorBuilder{
        private final String name;
        private final boolean isGroup;
        private LocalDate foundingDate;
        private String country;
        private List<String> members;
        private String spotifyUrl;
        private String yandexMusixUrl;

        public AuthorBuilder(String name, boolean isGroup){
            this.name = name;
            this.isGroup = isGroup;
            this.members = new ArrayList<>();
        }

        public AuthorBuilder setDate(LocalDate date){
            this.foundingDate = date;
            return this;
        }

        public AuthorBuilder setCountry(String country){
            this.country = country;
            return this;
        }

        public AuthorBuilder addMember(String member){
            if (members == null) members = new ArrayList<>();
            members.add(member);
            return this;
        }

        public AuthorBuilder setSpotify(String url){
            this.spotifyUrl = url;
            return this;
        }

        public AuthorBuilder setYandex(String url){
            this.yandexMusixUrl = url;
            return this;
        }

        public Author build(){
            return new Author(this);
        }
    }

}
