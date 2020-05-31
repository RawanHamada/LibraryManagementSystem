/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginScene;

import Entity.Borrowers;
import Entity.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jit
 */
public class ManagementBorrowersController implements Initializable {

    @FXML
    private TextField txtFieldID;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtMobile;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtGender;
    @FXML
    private TextField searchTxt;
    @FXML
    private TableView<Borrowers> tableView;
    @FXML
    private TableColumn<Borrowers, Integer> tcID;
    @FXML
    private TableColumn<Borrowers, String> tcfFName;
    @FXML
    private TableColumn<Borrowers, String> tcLName;
    @FXML
    private TableColumn<Borrowers, Integer> tcMobile;
    @FXML
    private TableColumn<Borrowers, String> tcEmail;
    @FXML
    private TableColumn<Borrowers, String> tcAddress;
    @FXML
    private TableColumn<Borrowers, String> tcGender;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonClear;
    Statement stat;
    PrintWriter pw;
    Stage primaryStage;

    HashMap<Integer, Borrowers> borrowersHashM = new HashMap();
    @FXML
    private Button buttonbackbook;
    @FXML
    private Button buttongomanage;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           DBConnection db=new DBConnection();
        try {
          this.stat = db.connection.createStatement();
        } catch (Exception ex) {
           // ex.printStackTrace();
            System.out.println(" Connecting to DBs is not well");        
}
          tcID.setCellValueFactory(new PropertyValueFactory("id"));
        tcfFName.setCellValueFactory(new PropertyValueFactory("firstName"));
        tcLName.setCellValueFactory(new PropertyValueFactory("lastName"));
        tcAddress.setCellValueFactory(new PropertyValueFactory("address"));
        tcMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        tcEmail.setCellValueFactory(new PropertyValueFactory("email"));
        tcGender.setCellValueFactory(new PropertyValueFactory("gender"));
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedBorrowers() );
    }    

    @FXML
    private void buttonAddHandle(ActionEvent event) throws Exception {
        try{
        Integer id = Integer.parseInt(txtFieldID.getText());
        String fName = txtFirstName.getText();
        String lName = txtLastName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        Integer mobile = Integer.parseInt(txtMobile.getText());
        String gender = txtGender.getText();
        String sql = "Insert Into Books values(" + id + ",'" +fName + "','" 
                + lName + "','" +address + "','"+email + "','"+mobile + "','"+gender + ")";
        this.stat.executeUpdate(sql);
        new Alert(Alert.AlertType.CONFIRMATION, "The row is added sucssifully").show();
         } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, " Empty Fields OR miss match input data type . Try again ").show();
        }
    }

    @FXML
    private void buttonSearchHandle(ActionEvent event) {
        try{
         tableView.getItems().setAll();
        borrowersHashM.keySet().stream().forEach((Integer k) -> {
            Borrowers b = borrowersHashM.get(k);
            if (b.getId().toString().equals(searchTxt.getText()) || 
                    b.getFirstName().equals(searchTxt.getText())
                    || b.getLastName().equals(searchTxt.getText())
                    || b.getAddress().contains(searchTxt.getText())
                    || b.getEmail().contains(searchTxt.getText())
                    || b.getMobile().toString().equals(searchTxt.getText())
                    || b.getGender().equals(searchTxt.getText())) {
                System.out.println("found********** " + k);
                new Alert(Alert.AlertType.CONFIRMATION, "The word is found").show();

                tableView.getItems().add(b);
                pw.println("Search Operation {" + searchTxt.getText() + "}: Match result with Borrower [ " + b + " ]");

    }
                });
                }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "The word is not found ").show();
        }
    }

    @FXML
    private void buttonUpdateHandle(ActionEvent event) throws Exception {
        try{
        Integer id = Integer.parseInt(txtFieldID.getText());
        String fName = txtFirstName.getText();
        String lName = txtLastName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        Integer mobile = Integer.parseInt(txtMobile.getText());
        String gender = txtGender.getText();
        try{
        String sql = "Update Books Set name='" + fName + "','" 
                + lName + "','" +address + "','"+email + "','"+mobile + "','"+gender +  " Where id=" +id;
        this.stat.executeUpdate(sql);
        Borrowers newBorrow = new Borrowers(id, fName, lName, mobile, email, address, gender);
        borrowersHashM.put(id, newBorrow);
        pw.println("Updating Borrow From " + borrowersHashM.get(id) + " To " + newBorrow);
        pw.println("---------------------");
        pw.flush();
        tableView.getItems().setAll();
        borrowersHashM.keySet().stream().sorted().forEach(b -> tableView.getItems().add(borrowersHashM.get(b)));
        new Alert(Alert.AlertType.INFORMATION, " Done Operation :) ").show();
        } catch (SQLIntegrityConstraintViolationException e) {
            new Alert(Alert.AlertType.ERROR, "Borrow ID [ " + id + " ] is already exists ").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "ERROR SQL ! Borrow ID [ " + id + " ] is not exists to update").show();
        }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, " Empty Fields OR miss match input data type . Try again ").show();
        }
    }

    

    @FXML
    private void buttonDeleteHandle(ActionEvent event) throws Exception {
        try{
    Integer id = Integer.parseInt(txtFieldID.getText());
        String sql = "delete from Books where id = " + id + "";
        this.stat.executeUpdate(sql);
        new Alert(Alert.AlertType.CONFIRMATION, "Book ID [ " + id + " ] is deleted Successfully").show();        

        Borrowers br = borrowersHashM.remove(id);
        ShowBorrowrs();

        pw.println("Delete Borrower : " + br);
        pw.println("---------------------");
        pw.flush();
         } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "ERROR ").show();
        }
        
    }

    @FXML
    private void buttonClearHandle(ActionEvent event) {
         resetControls();
    }
    private void resetControls(){
        txtFieldID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtMobile.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtGender.setText("");
        tableView.getItems().clear();
    
    }
       private void ShowBorrowrs() throws Exception{
    ResultSet rs = this.stat.executeQuery("Select * From Books");
        tableView.getItems().clear();
        while(rs.next()){
            Borrowers borr = new Borrowers();
            borr.setId(rs.getInt("id"));
            borr.setFirstName(rs.getString("firstName"));
            borr.setLastName(rs.getString("lastName"));
            borr.setMobile(rs.getInt("mobile"));
            borr.setEmail(rs.getString("email"));
            borr.setAddress(rs.getString("address"));
            borr.setGender(rs.getString("gender"));

            tableView.getItems().add(borr);
        }
     }
     private void showSelectedBorrowers(){
        Borrowers borrow = tableView.getSelectionModel().getSelectedItem();
        if(borrow != null){
        txtFieldID.setText(String.valueOf(borrow.getId()));
        txtFirstName.setText(borrow.getFirstName());
        txtLastName.setText(borrow.getLastName());
        txtAddress.setText(borrow.getAddress());
        txtEmail.setText(borrow.getEmail());
        txtMobile.setText(String.valueOf(borrow.getMobile()));
        txtGender.setText(borrow.getGender());
        txtAddress.setText(borrow.getAddress());
        
         }
     }

    @FXML
    private void buttonbackbookHandle(ActionEvent event) throws Exception {
         Parent Book = FXMLLoader.load(getClass().getResource("ManageBooks.fxml"));   

        Scene scene2 = new Scene(Book);
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    @FXML
    private void buttongomanageHandle(ActionEvent event) throws Exception {
        Parent MBBook = FXMLLoader.load(getClass().getResource("ManagementBorrowerBooks.fxml"));   

        Scene scene4 = new Scene(MBBook);
        primaryStage.setScene(scene4);
        primaryStage.show();
    }
    
}
