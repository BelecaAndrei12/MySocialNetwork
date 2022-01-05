package com.example.exercitiu.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Message {
    private String id;
    private User from;
    private ArrayList<User> to;
    private String message;
    private LocalDate date;

    public Message(Message m){
        this.id = m.getId();
        this.from = m.getFrom();
        this.to = m.getTo();
        this.message = m.getMessage();
        this.date = m.getDate();
    }

    public Message(String id, User from, ArrayList<User> to, String message, LocalDate date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public ArrayList<User> getTo() {
        return to;
    }

    public void setTo(ArrayList<User> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
