package com.avanishbharati.chucknorrisjokessplitter.model;

import java.util.List;

public class JokeValues {

    private int id;
    private String joke;
    private List<String> categories;

    public JokeValues() {
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setJoke(String joke){
        this.joke = joke;
    }
    public String getJoke(){
        return this.joke;
    }
    public void setCategories(List<String> categories){
        this.categories = categories;
    }
    public List<String> getCategories(){
        return this.categories;
    }

    @Override
    public String toString() {
        return "JokeValues{" +
                "id=" + id +
                ", joke='" + joke + '\'' +
                ", categories=" + categories +
                '}';
    }
}
