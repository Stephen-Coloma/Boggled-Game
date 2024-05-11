package Server_Java.controller.mainmenu;

import Server_Java.model.ServerJDBC;
import Server_Java.model.mainmenu.PlayerListModel;
import Server_Java.view.mainmenu.DeletePlayerPopUpView;
import Server_Java.view.mainmenu.PlayerListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;

public class PlayerListController {
    private PlayerListModel model;
    private PlayerListView view;

    public PlayerListController(PlayerListModel model, PlayerListView view) {
        this.model = model;
        this.view = view;

        this.view.populateTable(model.getPlayerDataList());
        setComponentActions();
    }

    private void setComponentActions() {
        prepareColumn("edit", view.getEditClmn(), 1);
        prepareColumn("delete", view.getDeleteClmn(), 2);
    } // end of setComponentActions


    private Button createButton(String label, int buttonColumn) {
        Button button = new Button(label);
        if (buttonColumn == 1) {
            button.setOnAction(this::handleEditButtonClick);
        } else if (buttonColumn == 2) {
            button.setOnAction(this::handleDeleteButtonClick);
        }
        return button;
    } // end of createButton


    public void prepareColumn(String label, TableColumn<Object, Void> columnName, int buttonColumn) {
        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<TableColumn<Object, Void>, TableCell<Object, Void>>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                final TableCell<Object, Void> cell = new TableCell<Object, Void>() {
                    private final Button btn = createButton(label, buttonColumn);

                    {
                        btn.setStyle("-fx-background-color:#248946; -fx-text-fill: white;");
                        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #DDFFC8FF; -fx-text-fill: #248946;"));
                        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #248946; -fx-text-fill: white;"));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        columnName.setCellFactory(cellFactory);
    }
    private void handleEditButtonClick(ActionEvent event) {

    }

    private void handleDeleteButtonClick(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            TableCell<Object, Void> cell = (TableCell<Object, Void>) button.getParent();

            Object product = cell.getTableRow().getItem();

            int filteredIndex = cell.getTableRow().getIndex();
            int originalIndex = view.getFilteredList().getSourceIndex(filteredIndex);

            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Server_Java/res/fxmls/DeletePlayerPopUp.fxml").toURI().toURL());
            Parent root = loader.load();

            DeletePlayerPopUpView popUpView = loader.getController();

            popUpView.setUpActionNoButton(e -> {
                ((Node) e.getSource()).getScene().getWindow().hide();
            });

            popUpView.setUpActionYesButton(e -> {
                if (originalIndex >= 0 && originalIndex < view.getPlayersDataList().size()) {
                    ServerJDBC.PlayerData playerToBeDeleted = (ServerJDBC.PlayerData) view.getPlayersDataList().remove(originalIndex);
                    //deleting the player to the database
                    ServerJDBC.deletePlayer(playerToBeDeleted.getPid());

                }
                ((Node) e.getSource()).getScene().getWindow().hide();
            });

            Scene scene = new Scene(root);

            Stage popUpStage = new Stage();
            popUpStage.setScene(scene);

            popUpStage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
