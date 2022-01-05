package com.example.exercitiu.Controller;

import com.example.exercitiu.Model.User;
import com.example.exercitiu.Service.FriendshipService;
import com.example.exercitiu.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class selectUserController {
    ObservableList<User> modelGrade = FXCollections.observableArrayList();
    private FriendshipService service1;
    private UserService service2;
    private User user;

    @FXML
    TextField textFieldName;
    @FXML
    Button button;
    @FXML
    private void changeSceneWithUser(ActionEvent event) throws Exception{
        this.user = service2.getUserByName(textFieldName.getText());
    }

    public void setService(UserService service, FriendshipService service1, User u) {
        this.service1 = service1;
        this.service2 = service;
        this.user = u;

//        comboBoxTeme.getItems().setAll(modelTema);
//        comboBoxTeme.getSelectionModel().selectFirst();
    }
}
