package com.example.exercitiu.Model;

import java.time.LocalDate;

public class Friendship extends BaseEntity{
    private User userX;
    private User userY;
    private LocalDate date;

    public Friendship(User userX, User userY,LocalDate date){
        this.userX = userX;
        this.userY = userY;
        this.date = date;
    }

    public Friendship(User userX, User userY){
        this.userX = userX;
        this.userY = userY;

    }

    public User getUserX() {
        return userX;
    }

    public void setUserX(User userX) {
        this.userX = userX;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUserY() {
        return userY;
    }

    public void setUserY(User userY) {
        this.userY = userY;
    }

}
