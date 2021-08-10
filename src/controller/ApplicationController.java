package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class ApplicationController {
    @FXML
    private Label lbl_status;

    @FXML
    private TextField fx_username;

    @FXML
    private TextField fx_password;

    public void Login(ActionEvent event) throws IOException {
        if(fx_username.getText().equals("user") && fx_password.getText().equals("pass")){
            lbl_status.setText("Login Success");
            ChangeWindow(event, "/view/MainPane.fxml", "Covid Dashboard");
        }else {
            lbl_status.setText("Login Failed");
        }
    }

    public void Register(ActionEvent event) throws IOException{
        ChangeWindow(event, "/view/register.fxml", "Register");
    }

    private void ChangeWindow(ActionEvent event, String fxml_sheet, String title) throws IOException {
        Stage mainStage = new Stage();

        Parent parent = (Parent) FXMLLoader.load(getClass().getResource(
                fxml_sheet));
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("/assets/bootstrap.css");
        mainStage.setScene(scene);
        mainStage.setTitle(title);
        mainStage.show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
}
