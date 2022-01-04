package com.example.exercitiu.Controller;

import com.example.exercitiu.Model.User;
import com.example.exercitiu.Service.FriendshipService;
import com.example.exercitiu.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class friendsController {

    ObservableList<User> modelGrade = FXCollections.observableArrayList();
    private FriendshipService service1;
    private UserService service2;


    @FXML
    TableColumn<User, String> tableColumnName;
    @FXML
    TableColumn<User, String> tableColumnTema;
    @FXML
    TableColumn<User, String> tableColumnNota;
    @FXML
    TableView<User> tableViewNote;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldName;
    @FXML
    TextField textFieldTema;
    @FXML
    TextField textFieldNota;

//    @FXML
//    ComboBox<String> comboBoxTeme;

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<User, String >("email"));

        tableViewNote.setItems(modelGrade);

        textFieldName.textProperty().addListener(o -> handleFilter());
        textFieldTema.textProperty().addListener(o -> handleFilter());
        textFieldNota.textProperty().addListener(o -> handleFilter());

//        comboBoxTeme.getSelectionModel().selectedItemProperty().addListener(
//                (x,y,z)->handleFilter()
//        );


    }

    private List<User> getNotaDTOList() {
        return service2.getAll();
    }

    private void handleFilter() {
        Predicate<User> p1 = n -> n.getName().startsWith(textFieldName.getText());
        Predicate<User> p2 = n -> n.getId().startsWith(textFieldTema.getText());

//        Predicate<NotaDto> p4 = n -> n.getTemaId().equals(comboBoxTeme.getSelectionModel().getSelectedItem());

        modelGrade.setAll(getNotaDTOList()
                .stream()
                .filter(p1.and(p2))
                .collect(Collectors.toList()));
    }


    public void setService(UserService service) {
        this.service2 = service;
        modelGrade.setAll(getNotaDTOList());
//        comboBoxTeme.getItems().setAll(modelTema);
//        comboBoxTeme.getSelectionModel().selectFirst();
    }
}
