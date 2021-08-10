package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import controller.PasswordValidate;

public class RegisterController {
    @FXML
    private Label lbl_status;

    @FXML
    private TextField fx_username;

    @FXML
    private TextField fx_password;

    @FXML
    private TextField fx_password1;

    public void RegisterUser(ActionEvent event) throws IOException {
        PasswordValidate validate = new PasswordValidate(fx_password.getText());
        if(validate.isValidPassword() && fx_password.getText().equals(fx_password1.getText())){
            lbl_status.setText("Registration Successful");
        }else {
            lbl_status.setText("Please check the password requirements");
        }
    }

}
