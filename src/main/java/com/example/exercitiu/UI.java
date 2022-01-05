package com.example.exercitiu;

import com.example.exercitiu.Model.BaseEntity;
import com.example.exercitiu.Model.Friendship;
import com.example.exercitiu.Model.Network;
import com.example.exercitiu.Model.User;
import com.example.exercitiu.Service.FriendshipService;
import com.example.exercitiu.Service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private UserService userService;
    private FriendshipService friendshipService;

    public UI(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;


    }

    public void handleShowUsers() {
        ArrayList<User> usersList = this.userService.getAll();
        for (BaseEntity item : usersList) {
            System.out.println(((User) item).getName() + " " + ((User) item).getEmail());
        }

    }

    public void handleLoggedIn(User loggedUser) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("a.Add friend");
            System.out.println("r.Remove friend");
            System.out.println("s.Friend list");
            System.out.println("f.Filter by month");
            System.out.println("x.Exit\n");
            System.out.println("Option: ");
            String option = scanner.nextLine();

            switch (option){



                case"S":
                    //handleShowAll();
                    break;

                case "a":
                    System.out.println("Enter friend`s name:");
                    String friend = scanner.nextLine();
                    User user = userService.getUserByName(friend);
                    if(user ==  null || user == loggedUser){
                        System.out.println("User not found!");
                        break;
                    }
                    handleAddFriend(loggedUser,user);
                        break;

                case "r":
                    System.out.println("Enter friend`s name:");
                    String friendRemove = scanner.nextLine();
                    User userRemove = userService.getUserByName(friendRemove);
                    if(userRemove ==  null || userRemove == loggedUser){
                        System.out.println("User not found!");
                        break;
                    }
                    handleRemoveFriend(loggedUser,userRemove);
                    break;

                case "s":
                    handleShowFriends(loggedUser);
                    break;
                case "f":
                    System.out.println("Enter month:");
                    String month = scanner.nextLine();
                    handleFilteredFriendshipsByMonth(month,loggedUser);
                    break;

                case "x":
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }

    }

    public void handleShowFriends(User user){
//            ArrayList<Friendship> friends =  this.friendshipService.getFriendshipsByUser(user);
//            for(Friendship friendship:friends){
//                if(user.equals(friendship.getUserX()))
//                    System.out.println(friendship.getUserY().getName() + ' ' + friendship.getDate());
//                else
//                    System.out.println(friendship.getUserX().getName() + ' ' + friendship.getDate());
//            }
    }

    public void handleFilteredFriendshipsByMonth(String month,User user){
        ArrayList<User> friendships =  this.friendshipService.getFriendshipsByUser(user);
        for(User friendship:friendships){
            System.out.println(friendship.getName());

        }
    }

    public void handleAddFriend(User user,User friend){
        Friendship friendship = new Friendship(user,friend,LocalDate.now());
        user.addFriend(friend);
        friend.addFriend(user);
        friendshipService.insert(friendship);
       // this.friendshipService.getNetwork().addEdge(user,friend);
    }

    public void handleRemoveFriend(User userRemove, User friendRemove){
        Friendship friendship = new Friendship(userRemove,friendRemove);
        userRemove.removeFriend(friendRemove);
        friendRemove.removeFriend(userRemove);
        friendshipService.delete(friendship);
        //this.friendshipService.getNetwork().removeEdge(userRemove,friendRemove);

    }

    public void handleAddUser(String id, String name, String email,String password) throws IOException {
        User user = new User(id,name,email,password);
        this.userService.insert(user);
        this.friendshipService.getNetwork().updateNetwork(user,true);
    }

    public void handleDeleteUser(User userDelete){
//        this.friendshipService.getNetwork().removeUser(userDelete);
        this.userService.delete(userDelete);
        this.friendshipService.deleteFriendshipForDeletedUser(userDelete);

//        Network network = new Network(userService.getLength(),userService.getAll());
//        for(BaseEntity entity:friendshipService.getAll()){
//            Friendship friendship = (Friendship) entity;
//            network.addEdge(friendship.getUserX(),friendship.getUserY());
//
//        }
//        friendshipService.setNetwork(network);


    }

    public void handle_Communities(){
        System.out.println(this.friendshipService.getNetwork().getNumberOfConnComponents());
    }

    public void handle_getBiggestCommunity(){
        this.friendshipService.getNetwork().getLongestComm();
    }

    public void handleAdmin() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("\n");
            System.out.println("add.Add User");
            System.out.println("del.Delete User");
            System.out.println("show.Show All Users\n");
            String option = scanner.nextLine();

            switch (option){
                case"add":
                    System.out.println("Id:");
                    String id = scanner.nextLine();
                    System.out.println("Name:");
                    String name = scanner.nextLine();
                    System.out.println("Email:");
                    String email = scanner.nextLine();
                    System.out.println("Password:");
                    String password = scanner.nextLine();
                    handleAddUser(id,name,email,password);
                    break;
                case"del":
                    System.out.println("Enter user`s name:");
                    String userName = scanner.nextLine();
                    User userDelete = userService.getUserByName(userName);
                    if(userDelete == null)
                        System.out.println("Wrong user name\n");
                    handleDeleteUser(userDelete);
                    break;
                case"show":
                    handleShowUsers();
                    System.out.println("\n");
                    break;
                case"!network":
                    this.friendshipService.getNetwork().printGraph();
                    break;
                case"!communities":
                    handle_Communities();
                    break;
                case "!BgComm":
                    handle_getBiggestCommunity();
                    break;
                case"!exit":
                    return;
                default:
                    System.out.println("Invalid command!\n");
            }

        }
        //Test commit

    }

//    public void handleShowAll()
//    {
//        System.out.println(this.userService.getAll());
//    }



    public void runApp() throws IOException {
        //handleShowUsers();
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> userList = userService.getAll();

        while (true) {
            System.out.println("Enter name: ");
            String user = scanner.nextLine();
            if(user.equals("!admin"))
                handleAdmin();
            if(user.equals("!q"))
                return;
            User loggedUser = userService.getUserByName(user);

            if (loggedUser == null) {
                System.out.println("Wrong user name");
                continue;
            }
            this.handleLoggedIn(loggedUser);


        }


    }

}

