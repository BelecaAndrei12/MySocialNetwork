package com.example.exercitiu.Service;

import com.example.exercitiu.Model.Friendship;
import com.example.exercitiu.Model.Network;
import com.example.exercitiu.Model.User;
import com.example.exercitiu.Repo.Repository;
import com.example.exercitiu.Repo.RepositoryInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FriendshipService {
    RepositoryInterface repo;
    Network network;

    public FriendshipService(RepositoryInterface repo){
        this.repo=repo;
        this.readFriendshipsDb();
        this.network = null;
    }

    public FriendshipService(RepositoryInterface repo, Network network){
        this.repo=repo;
        this.network=network;
        this.readFriendshipsDb();

    }
    public void insert(Friendship friendship){
        this.repo.insert(friendship);
        network.addEdge(friendship.getUserX(),friendship.getUserY());
        this.updateAddFriendship(friendship);

    }

    public void delete(Friendship friendship){
        this.repo.delete(friendship);
        network.removeEdge(friendship.getUserX(),friendship.getUserY());
        this.updateDelFriendship(friendship);
    }

    public ArrayList<Friendship> getAll(){
        return ((ArrayList<Friendship>) (ArrayList<?>) this.repo.getAll());
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void readFriendshipsDb() {
        try {
            Class.forName("org.postgresql.Driver");
            this.repo = new Repository();

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            Statement statement = connection.createStatement();
            ResultSet users = statement.executeQuery("select * from users");
            ArrayList<User> usersList = new ArrayList<>();
            while(users.next()){


                User user = new User(users.getString("user_id"),users.getString("username")
                        ,users.getString("email"),users.getString("password"));
                usersList.add(user);
            }
            this.network =  new Network(usersList.size(),usersList);


            Statement statement1  = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery("select * from friendships");

            while(resultSet.next()){
                String idUserX = resultSet.getString("userx").toString();
                String idUserY = resultSet.getString("usery").toString();
                LocalDate date = LocalDate.parse(resultSet.getString("date"));
                User userX = new User();
                User userY = new User();
                for (User item:usersList){
                    if(item.getId().equals(idUserX))
                         userX  = item;
                    else if(item.getId().equals(idUserY))
                        userY  = item;
                }
                Friendship friendship = new Friendship(userX,userY,date);

                this.repo.insert(friendship);
                network.addEdge(friendship.getUserX(),friendship.getUserY());

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateAddFriendship(Friendship friendship) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO friendships (userx, usery,date ) VALUES (?, ?, ?)");
            preparedStatement.setString(1, friendship.getUserX().getId());
            preparedStatement.setString(2, friendship.getUserY().getId());
            preparedStatement.setString(3,friendship.getDate().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateDelFriendship(Friendship friendship){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM friendships WHERE (userx =? and usery =?) OR (userx =? and usery =?)");
            preparedStatement.setString(1,friendship.getUserX().getId());
            preparedStatement.setString(2,friendship.getUserY().getId());
            preparedStatement.setString(3,friendship.getUserY().getId());
            preparedStatement.setString(4,friendship.getUserX().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.readFriendshipsDb();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteFriendshipForDeletedUser(User user) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM friendships where userx = ? OR usery=?");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.readFriendshipsDb();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<User> getFriendshipsByUser(User user){
        ArrayList<User> friends =  new ArrayList<User>();
        for(Friendship friendship: this.getAll()){
            if(friendship.getUserX().equals(user))
                friends.add(friendship.getUserY());
            else if(friendship.getUserY().equals(user)){
                friends.add(friendship.getUserX());
            }
        }
        return friends;
    }


    }





