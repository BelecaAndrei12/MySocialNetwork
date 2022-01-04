package com.example.exercitiu.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres" , "postgres", "123");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from users");


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
