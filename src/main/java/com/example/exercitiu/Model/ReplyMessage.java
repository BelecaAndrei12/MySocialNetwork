package com.example.exercitiu.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReplyMessage extends Message{
    private Message message;

    public ReplyMessage(String id, User from, ArrayList<User> to, String s, LocalDate date, Message m) {
        super(id, from, to, s, date);
        this.message = m;
    }
}
