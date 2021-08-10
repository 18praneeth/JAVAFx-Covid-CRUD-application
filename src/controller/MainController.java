package controller;

import email.mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ResourceBundle;




public class MainController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private TextField idField;
    
    @FXML
    private TextField nameField;

    @FXML
    private TextField patient_idField;

    @FXML
    private TextField email_idField;

    @FXML
    private TextField phone_numberField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField aadhar_numberField;

    @FXML
    private TextArea remarksField;

    @FXML
    private ChoiceBox<String> is_vaccinatedField;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Patient> TableView;
    
    @FXML
    private TableColumn<Patient, Integer> idColumn;
    
    @FXML
    private TableColumn<Patient, String> nameColumn;

    @FXML
    private TableColumn<Patient, String> patient_idColumn;

    @FXML
    private TableColumn<Patient, String> email_idColumn;

    @FXML
    private TableColumn<Patient, Integer> phone_numberColumn;

    @FXML
    private TableColumn<Patient, Integer> ageColumn;

    @FXML
    private TableColumn<Patient, Integer> aadhar_numberColumn;

    @FXML
    private TableColumn<Patient, String> is_vaccinatedColumn;

    @FXML
    private TableColumn<Patient, String> remarksColumn;


    @FXML
    private void insertButton() {
        mail m = new mail(email_idField.getText(), nameField.getText(), is_vaccinatedField.getValue(), remarksField.getText());
    	String query = "insert into patient values("+idField.getText()+",'"+nameField.getText()+"','"+patient_idField.getText()+"', '"+email_idField.getText()+"',"+phone_numberField.getText()+","+ageField.getText()+","+aadhar_numberField.getText()+",'"+is_vaccinatedField.getValue()+"','"+remarksField.getText()+"')";
    	executeQuery(query);
    	showBooks();
    }
    
    
    @FXML 
    private void updateButton() {
    String query = "UPDATE patient SET name='"+nameField.getText()+"',patient_id='"+patient_idField.getText()+"',email_id='"+email_idField.getText()+"',phone_number="+phone_numberField.getText()+",age="+ageField.getText()+",aadhar_number="+aadhar_numberField.getText()+",is_vaccinated='"+is_vaccinatedField.getValue()+"',remarks='"+remarksField.getText()+"' WHERE ID="+idField.getText()+"";
    executeQuery(query);
	showBooks();
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM patient WHERE ID="+idField.getText()+"";
    	executeQuery(query);
    	showBooks();
    }

    private void loadData(){
        list.removeAll(list);
        list.addAll("Yes", "No");
        is_vaccinatedField.getItems().addAll(list);
    }
    
    public void executeQuery(String query) {
    	Connection conn = getConnection();
    	Statement st;
    	try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    	showBooks();
    }
    
    public Connection getConnection() {
    	Connection conn;
    	try {
    		conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/Praneeth/Desktop/javafx-mysql-crud/database.db");
    		return conn;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public ObservableList<Patient> getPatientList(){
    	ObservableList<Patient> patientList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query;
        query = "SELECT * FROM patient ";
        Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Patient patient;
			while(rs.next()) {
				patient = new Patient(rs.getInt("id"),rs.getString("name"),rs.getString("patient_id"),rs.getInt("age"),rs.getInt("aadhar_number"),rs.getString("is_vaccinated"),rs.getString("remarks"),rs.getString("email_id"),rs.getInt("phone_number"));
				patientList.add(patient);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return patientList;
    }
    
    public void showBooks() {
    	ObservableList<Patient> list = getPatientList();
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<Patient,Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Patient,String>("name"));
        patient_idColumn.setCellValueFactory(new PropertyValueFactory<Patient,String>("patient_id"));
        email_idColumn.setCellValueFactory(new PropertyValueFactory<Patient,String>("email_id"));
        phone_numberColumn.setCellValueFactory(new PropertyValueFactory<Patient,Integer>("phone_number"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Patient,Integer>("age"));
        aadhar_numberColumn.setCellValueFactory(new PropertyValueFactory<Patient,Integer>("aadhar_number"));
        is_vaccinatedColumn.setCellValueFactory(new PropertyValueFactory<Patient,String>("is_vaccinated"));
        remarksColumn.setCellValueFactory(new PropertyValueFactory<Patient,String>("remarks"));

    	
    	TableView.setItems(list);
    }

    public void popup(ActionEvent event) throws IOException, ParseException, java.text.ParseException {

        Chart chart = new Chart();

        FlowGridPane pane = new FlowGridPane(2, 1, chart.chartCreate()[0], chart.chartCreate()[1], chart.chartCreate()[2], chart.chartCreate()[3]);
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setAlignment(Pos.CENTER);
        pane.setCenterShape(true);
        pane.setPadding(new Insets(5));
        pane.setPrefSize(800, 600);
        pane.setBackground(new Background(new BackgroundFill(Color.web("#101214"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane, 1000, 500);
        Stage newWindow = new Stage();
        newWindow.setTitle("Covid Analytics");
        newWindow.setScene(scene);
        newWindow.show();
    }

}
