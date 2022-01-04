package com.example.exercitiu.Model;

import java.util.ArrayList;

public class User extends BaseEntity {

    private String id;
    private String name;
    private String email;
    private String password;
    private ArrayList<User> friendsList;

    public User() {
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email=email;
        this.password = password;
        this.friendsList = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<User> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(ArrayList<User> friendsList) {
        this.friendsList = friendsList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}' + '\'';
    }

    public void addFriend(User user) {friendsList.add(user); }

    public void removeFriend(User user){ friendsList.remove(user); }

    public ArrayList<User> getAllFriends(){ return friendsList; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof User))
            return false;
        User user = (User) obj;
        if (this.id.equals(user.getId()))
            return true;
        return false;
    }
}
