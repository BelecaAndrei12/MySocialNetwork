package com.example.exercitiu;
import com.example.exercitiu.Controller.selectUserController;
import com.example.exercitiu.Model.Network;
import com.example.exercitiu.Model.User;
import com.example.exercitiu.Repo.Repository;
import com.example.exercitiu.Repo.RepositoryInterface;
import com.example.exercitiu.Service.FriendshipService;
import com.example.exercitiu.Service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Run extends Application{

        @Override
        public void start(Stage primaryStage) throws Exception{

            Parent root = FXMLLoader.load(getClass().getResource("selectUserView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();


//            FXMLLoader loader = new FXMLLoader(Run.class.getResource("selectUserView.fxml"));
////            loader.setLocation(getClass().getResource("friendsView.fxml"));
//
//            //FXMLLoader loader = new FXMLLoader(TestSem8.class.getResource("notaView.fxml"));
//            AnchorPane root;
//            root = loader.load();

//
//            primaryStage.setScene(new Scene(root, 700, 500));
//            primaryStage.setTitle("Hello World");
//            primaryStage.show();

        }

        public static void main(String[] args) {
            launch(args);
        }

    }

