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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class addFriendController {
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
    TextField textFieldTema;
    @FXML
    TextField textFieldNota;
    @FXML
    Button button;

    @FXML
    private void addFriend(ActionEvent event){
        User u = tableViewFriends.getSelectionModel().getSelectedItem();
        tableViewFriends.getItems().removeAll(tableViewFriends.getSelectionModel().getSelectedItem());
        Friendship f = new Friendship(u, user, LocalDate.now());
        service1.insert(f);
        user.addFriend(u);
        u.addFriend(user);
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("friendsView.fxml"));
        root = loader.load();

        friendsController ctrl = loader.getController();
        ctrl.setUser(user.getName());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





    @FXML
    public void initialize() {

        tableColumnId.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String >("email"));

        tableViewFriends.setItems(modelGrade);


    }

    private List<User> getNotaDTOList() {
        ArrayList<User> result = new ArrayList<>();
        ArrayList<User> list = service2.getAll();
        for(User u : list){
            if (!user.getAllFriends().contains(u) && !user.equals(u))
                result.add(u);
        }
        return result;
    }

    private void handleFilter() {

        ArrayList<User> friends = service1.getFriendshipsByUser(service2.getUserByName(textFieldName.getText()));
        Predicate<User> p2 = n -> n.getId().startsWith(textFieldTema.getText());

//        Predicate<NotaDto> p4 = n -> n.getTemaId().equals(comboBoxTeme.getSelectionModel().getSelectedItem());

        modelGrade.setAll(friends);
    }

    public void setUser(String s){
        user = service2.getUserByName(s);
        modelGrade.setAll(getNotaDTOList());
    }
}
