package com.example.exercitiu;

import com.example.exercitiu.Model.Network;
import com.example.exercitiu.Model.User;
import com.example.exercitiu.Repo.Repository;
import com.example.exercitiu.Repo.RepositoryInterface;
import com.example.exercitiu.Service.FriendshipService;
import com.example.exercitiu.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private RepositoryInterface repo1 = new Repository();
    private RepositoryInterface repo2 = new Repository();
    private UserService service2= new UserService(repo1);
    private User user ;
    private Network network = new Network(service2.getLength(),service2.getAll());
    private FriendshipService service1= new FriendshipService(repo2, network);
    private Stage stage;
    private Scene scene;
    private Parent root;
    private User u;

    @FXML
    TextField textFieldName;
    @FXML
    Text textError;
    @FXML
    Button button;


    public void login(ActionEvent event) throws IOException {
        String user = textFieldName.getText();
        if(service2.getUserByName(user) != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("friendsView.fxml"));
            root = loader.load();

            friendsController ctrl = loader.getController();
            ctrl.setUser(textFieldName.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            textError.setText("Username incorect!");
        }
    }

}
