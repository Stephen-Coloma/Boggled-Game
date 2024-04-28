package Server_Java.view.mainmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditGameConfigView {
    @FXML
    private Label noticeLabel;

    @FXML
    private Button rDecrementBtn;

    @FXML
    private Button rIncrementBtn;

    @FXML
    private TextField rLabel;

    @FXML
    private Label roundLengthNoticeLabel;

    @FXML
    private Button saveBtn;

    @FXML
    private Button wDecrementBtn;

    @FXML
    private Button wIncrementBtn;

    @FXML
    private TextField wLabel;

    @FXML
    private Label waitingTimeNoticeLabel;
    //set up action button
    public void setUpActionWDecrementBtn(EventHandler<ActionEvent> event){
        this.wDecrementBtn.setOnAction(event);
    }

    public void setUpActionWIncrementBtn(EventHandler<ActionEvent> event){
        this.wIncrementBtn.setOnAction(event);
    }
    public void setUpActionRDecrementBtn(EventHandler<ActionEvent> event){
        this.rDecrementBtn.setOnAction(event);
    }

    public void setUpActionRIncrementBtn(EventHandler<ActionEvent> event){
        this.rIncrementBtn.setOnAction(event);
    }
    public void setUpActionSaveBtn(EventHandler<ActionEvent> event){
        this.saveBtn.setOnAction(event);
    }

    public Button getrDecrementBtn() {
        return rDecrementBtn;
    }

    public void setrDecrementBtn(Button rDecrementBtn) {
        this.rDecrementBtn = rDecrementBtn;
    }

    public Button getrIncrementBtn() {
        return rIncrementBtn;
    }

    public void setrIncrementBtn(Button rIncrementBtn) {
        this.rIncrementBtn = rIncrementBtn;
    }

    public TextField getrLabel() {
        return rLabel;
    }

    public void setrLabel(TextField rLabel) {
        this.rLabel = rLabel;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public void setSaveBtn(Button saveBtn) {
        this.saveBtn = saveBtn;
    }

    public Button getwDecrementBtn() {
        return wDecrementBtn;
    }

    public void setwDecrementBtn(Button wDecrementBtn) {
        this.wDecrementBtn = wDecrementBtn;
    }

    public Button getwIncrementBtn() {
        return wIncrementBtn;
    }

    public void setwIncrementBtn(Button wIncrementBtn) {
        this.wIncrementBtn = wIncrementBtn;
    }

    public TextField getwLabel() {
        return wLabel;
    }

    public void setwLabel(TextField wLabel) {
        this.wLabel = wLabel;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(Label noticeLabel) {
        this.noticeLabel = noticeLabel;
    }

    public Label getRoundLengthNoticeLabel() {
        return roundLengthNoticeLabel;
    }

    public void setRoundLengthNoticeLabel(Label roundLengthNoticeLabel) {
        this.roundLengthNoticeLabel = roundLengthNoticeLabel;
    }

    public Label getWaitingTimeNoticeLabel() {
        return waitingTimeNoticeLabel;
    }

    public void setWaitingTimeNoticeLabel(Label waitingTimeNoticeLabel) {
        this.waitingTimeNoticeLabel = waitingTimeNoticeLabel;
    }
}
