package com.example.exercitiu;

import com.example.exercitiu.Model.Friendship;
import com.example.exercitiu.Model.Network;
import com.example.exercitiu.Model.User;
import com.example.exercitiu.Repo.Repository;
import com.example.exercitiu.Repo.RepositoryInterface;
import com.example.exercitiu.Service.FriendshipService;
import com.example.exercitiu.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class friendsController {


    ObservableList<User> modelGrade = FXCollections.observableArrayList();
    private RepositoryInterface repo1 = new Repository();
    private RepositoryInterface repo2 = new Repository();
    private UserService service2= new UserService(repo1);
    private User user ;
    private Network network = new Network(service2.getLength(),service2.getAll());
    private FriendshipService service1= new FriendshipService(repo2, network);
    private Parent root;
    private Stage stage;
    private Scene scene;
    String string ;


    @FXML
    TableColumn<User, String> tableColumnId;
    @FXML
    TableColumn<User, String> tableColumnName;
    @FXML
    TableColumn<User, String> tableColumnEmail;
    @FXML
    TableView<User> tableViewFriends;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldName;
    @FXML
    Text text2;
    @FXML
    Button button;
    @FXML
    Button buttonAdd;

    @FXML
    private void deleteFriend(ActionEvent event){
        User u = tableViewFriends.getSelectionModel().getSelectedItem();
        tableViewFriends.getItems().removeAll(tableViewFriends.getSelectionModel().getSelectedItem());
        Friendship f = new Friendship(u, user);
        service1.delete(f);
        user.removeFriend(u);
        u.removeFriend(user);
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("selectUserView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addFriends(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFriendView.fxml"));
//        root = loader.load();
//
//        addFriendController ctrl = loader.getController();
//        ctrl.setUser(user.getName());
//
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        int check = 0;
        System.out.println("Enter friend`s name:");
        String friend = textFieldName.getText();
        User friend2 = service2.getUserByName(friend);
        if(friend2 ==  null || friend2 == user){
            text2.setText("Userul nu exista!");
            check = 1;
        }
        if(user.getAllFriends().contains(friend2)){
            text2.setText("Sunteti deja prieteni!");
            check=1;
        }
        if(check == 0)
            friend2.addFriendRequest(user, "Pending", false);
    }





    @FXML
    public void initialize() {

        tableColumnId.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String >("email"));

        tableViewFriends.setItems(modelGrade);


    }

    private List<User> getNotaDTOList() {

        return service1.getFriendshipsByUser(user);
    }

    private void handleFilter() {

        ArrayList<User> friends = service1.getFriendshipsByUser(service2.getUserByName(textFieldName.getText()));

//        Predicate<NotaDto> p4 = n -> n.getTemaId().equals(comboBoxTeme.getSelectionModel().getSelectedItem());

        modelGrade.setAll(friends);
    }

    public void setUser(String s){
        user = service2.getUserByName(s);
        modelGrade.setAll(getNotaDTOList());
    }
}
