package ru.job4j.retrofit_example.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private int id;
    @SerializedName("body")
    private String text;
    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }
}
