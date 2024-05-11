package Server_Java.view.mainmenu;

import Server_Java.model.ServerJDBC;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerListView {

    @FXML
    private TableColumn<Object, Void> deleteClmn;

    @FXML
    private TableColumn<Object, Void> editClmn;

    @FXML
    private TableColumn<ServerJDBC.PlayerData, String> fullNameClmn;

    @FXML
    private TableColumn<ServerJDBC.PlayerData, Integer> totalPointsClmn;

    @FXML
    private TableColumn<ServerJDBC.PlayerData, Integer> totalGamesClmn;

    @FXML
    private TableColumn<ServerJDBC.PlayerData, String> passwordClmn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Object> playersTable;

    @FXML
    private TableColumn<ServerJDBC.PlayerData, String> usernameClmn;

    private FilteredList<Object> filteredList;
    private ObservableList<Object> playersDataList;

    public void populateTable(List<ServerJDBC.PlayerData> playerDataList) {
        this.playersDataList = FXCollections.observableArrayList();
        this.playersDataList.addAll(playerDataList);

        filteredList = new FilteredList<>(playersDataList, item -> true);

        playersTable.getItems().clear();
        playersTable.setItems(filteredList);

        // Set cell value factories for each column
        usernameClmn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        passwordClmn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        fullNameClmn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullname()));
        totalGamesClmn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumberOfGamesPlayed()).asObject());
        totalPointsClmn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPoints()).asObject());

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    }

    private void filterTable(String searchText) {
        filteredList.setPredicate(item -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // Show all items if search text is empty
            }

            // Convert search text to lower case for case-insensitive search
            String lowerCaseSearchText = searchText.toLowerCase();

            // Get PlayerData object from item if it is a PlayerData
            ServerJDBC.PlayerData playerData = (item instanceof ServerJDBC.PlayerData) ? (ServerJDBC.PlayerData) item : null;

            // Check if name or username contains the search text
            if (playerData != null) {
                String username = playerData.getUsername().toLowerCase();
                String fullName = playerData.getFullname().toLowerCase();
                return username.contains(lowerCaseSearchText) || fullName.contains(lowerCaseSearchText);
            }

            return false; // Return false if item is not a PlayerData
        });
        playersTable.setItems(filteredList);
    } // end of filterTable

    public TableColumn<Object, Void> getDeleteClmn() {
        return deleteClmn;
    }

    public void setDeleteClmn(TableColumn<Object, Void> deleteClmn) {
        this.deleteClmn = deleteClmn;
    }

    public TableColumn<Object, Void> getEditClmn() {
        return editClmn;
    }

    public void setEditClmn(TableColumn<Object, Void> editClmn) {
        this.editClmn = editClmn;
    }

    public TableColumn<ServerJDBC.PlayerData, String> getFullNameClmn() {
        return fullNameClmn;
    }

    public void setFullNameClmn(TableColumn<ServerJDBC.PlayerData, String> fullNameClmn) {
        this.fullNameClmn = fullNameClmn;
    }

    public TableColumn<ServerJDBC.PlayerData, Integer> getTotalPointsClmn() {
        return totalPointsClmn;
    }

    public void setTotalPointsClmn(TableColumn<ServerJDBC.PlayerData, Integer> totalPointsClmn) {
        this.totalPointsClmn = totalPointsClmn;
    }

    public TableColumn<ServerJDBC.PlayerData, Integer> getTotalGamesClmn() {
        return totalGamesClmn;
    }

    public void setTotalGamesClmn(TableColumn<ServerJDBC.PlayerData, Integer> totalGamesClmn) {
        this.totalGamesClmn = totalGamesClmn;
    }

    public TableColumn<ServerJDBC.PlayerData, String> getPasswordClmn() {
        return passwordClmn;
    }

    public void setPasswordClmn(TableColumn<ServerJDBC.PlayerData, String> passwordClmn) {
        this.passwordClmn = passwordClmn;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public void setSearchTextField(TextField searchTextField) {
        this.searchTextField = searchTextField;
    }

    public TableView<Object> getPlayersTable() {
        return playersTable;
    }

    public void setPlayersTable(TableView<Object> playersTable) {
        this.playersTable = playersTable;
    }

    public TableColumn<ServerJDBC.PlayerData, String> getUsernameClmn() {
        return usernameClmn;
    }

    public void setUsernameClmn(TableColumn<ServerJDBC.PlayerData, String> usernameClmn) {
        this.usernameClmn = usernameClmn;
    }

    public FilteredList<Object> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(FilteredList<Object> filteredList) {
        this.filteredList = filteredList;
    }

    public ObservableList<Object> getPlayersDataList() {
        return playersDataList;
    }

    public void setPlayersDataList(ObservableList<Object> playersDataList) {
        this.playersDataList = playersDataList;
    }
}