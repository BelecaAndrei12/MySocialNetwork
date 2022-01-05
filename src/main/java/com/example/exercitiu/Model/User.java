package com.example.exercitiu.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User extends BaseEntity {

    private String id;
    private String name;
    private String email;
    private String password;
    private ArrayList<User> friendsList;
    private Map<User, String> friendRequests;

    public User() {
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email=email;
        this.password = password;
        this.friendsList = new ArrayList<>();
        this.friendRequests = new HashMap<>();
    }

    public Map<User, String> getFriendRequests() {
        return friendRequests;
    }

    public void addFriendRequest(User u, String status, boolean readData){
        int click = 0;
        for(Map.Entry<User, String> x:friendRequests.entrySet()){
            if(x.getKey().equals(u))
                click = 1;
        }
        if(click == 0){
            if(!friendsList.contains(u) || status.equals("Accepted"))
                friendRequests.put(u, status);
            if(!readData)
                updateAddRequest(this, u, status);
        }
    }

    public void deleteFriendRequest(User u){

        friendRequests.remove(u);
        deleteRequestDB(this, u);

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

    void updateAddRequest(User x, User y, String status) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO friendRequest (userx,usery,status) VALUES (?, ?, ?)");
            statement.setString(1,x.getId());
            statement.setString(2,y.getId());
            statement.setString(3,status);

            statement.execute();
            statement.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void deleteRequestDB(User x, User y) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM friendRequest WHERE userx = ? and usery = ?");
            statement.setString(1, x.getId());
            statement.setString(2, y.getId());
            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
