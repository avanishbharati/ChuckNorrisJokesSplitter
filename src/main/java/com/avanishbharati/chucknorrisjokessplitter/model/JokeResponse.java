package com.avanishbharati.chucknorrisjokessplitter.model;

public class JokeResponse {

    private String type;
    private JokeValues value;

    public JokeResponse() {
    }

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setValue(JokeValues value){
        this.value = value;
    }
    public JokeValues getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return "JokeResponse{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}