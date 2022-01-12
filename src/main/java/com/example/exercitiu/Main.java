package com.example.exercitiu;

import com.example.exercitiu.Model.Network;
import com.example.exercitiu.Repo.Repository;
import com.example.exercitiu.Repo.RepositoryInterface;
import com.example.exercitiu.Service.FriendshipService;
import com.example.exercitiu.Service.UserService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//            Run.main(args);
        RepositoryInterface userRepo = new Repository();
        RepositoryInterface friendshipRepo = new Repository();
        UserService userService = new UserService(userRepo);
        Network network = new Network(userService.getLength(),userService.getAll());


        FriendshipService friendshipService = new FriendshipService(friendshipRepo,network);





          UI userInterface = new UI(userService,friendshipService);
          userInterface.runApp();

//        ArrayList<User> registerUsers = userService.getAll();
//
//        Network  network = new Network (3,registerUsers);
//
//        network.addEdge((User) registerUsers.get(0), (User) registerUsers.get(1));
//        network.addEdge((User) registerUsers.get(0), (User) registerUsers.get(2));
//        network.printGraph();


    }
}
