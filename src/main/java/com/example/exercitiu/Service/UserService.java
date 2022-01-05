package com.example.exercitiu.Service;

import com.example.exercitiu.Model.User;
import com.example.exercitiu.Repo.RepositoryInterface;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private RepositoryInterface repo;

    public UserService(RepositoryInterface repo) {
        this.repo=repo;
//        this.readUsers();
        this.readUserDb();
        this.readFriendRequests();
    }

    public void insert(User user) throws IOException {
        this.repo.insert(user);
        this.updateAddDb(user);
    }

    public void delete(User user){
        this.repo.delete(user);
        this.updateDelDb(user);
    }

    public int getLength(){
      return this.repo.getLength();
    }

    public ArrayList<User> getAll(){

        return ((ArrayList<User>) (ArrayList<?>) this.repo.getAll());
    }

    public void readUsers()  {
        String path ="C:\\Users\\RR56\\Desktop\\Facultate\\MAP\\tema_lab3\\src\\Users.csv";
        String line ="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while((line= br.readLine())!=null){
                String[] userData = line.split(",");
                User user = new User(userData[0], userData[1],userData[2],userData[3]);
                this.repo.insert(user);
                //Arrays.stream(userData).forEach(userData[i] ->);

            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public void readFriendRequests() {
        try {
            Connection connection;
            Statement statement;
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from friendrequest");

            while (resultSet.next()){
                User user = getUserById(resultSet.getString("userx"));
                user.addFriendRequest(getUserById(resultSet.getString("usery")), resultSet.getString("status"), true);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void readUserDb() {
        try {
            Connection connection;
            Statement statement;
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from users");


            while (resultSet.next()){
                User user = new User(resultSet.getString("user_id"),resultSet.getString("username")
                ,resultSet.getString("email"),resultSet.getString("password"));
                this.repo.insert(user);
            }

            ResultSet friends = statement.executeQuery("select * from friendships");
             while(friends.next()){
                User userX = getUserById(friends.getString("userx"));
                User userY = getUserById(friends.getString("usery"));
                userX.addFriend(userY);
                userY.addFriend(userX);
//                 System.out.println(userX.toString());
//                 System.out.println(userY.toString());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void updateAddDb(User user ) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (user_id, username, email, password) VALUES (?, ?, ?, ?)");
            statement.setString(1,user.getId());
            statement.setString(2,user.getName());
            statement.setString(3,user.getEmail());
            statement.setString(4,user.getPassword());
            statement.executeUpdate();
            statement.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateDelDb(User user) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            statement.setString(1, user.getId());
            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void updateFile()  {
        try {
            String path = "C:\\Users\\RR56\\Desktop\\Facultate\\MAP\\tema_lab3\\src\\Users.csv";
            FileWriter fw = new FileWriter(path);
            PrintWriter pw = new PrintWriter(fw);
            ArrayList<User> userList = this.getAll();
            for (User Item : userList) {
                String UserFile = Item.getId()+"," + Item.getName()+"," + Item.getEmail()+"," + Item.getPassword()+"\n";
                pw.print(UserFile);
            }
            pw.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();} catch (IOException e) {
            e.printStackTrace();
        }
    }



    public User getUserByName(String user){

         return this.getAll().stream()
                .filter(item -> item.getName().equals(user))
                .findAny()
                .orElse(null);

    }

    public User getUserById(String user){

        return this.getAll().stream()
                .filter(item -> item.getId().equals(user))
                .findAny()
                .orElse(null);

    }


}
