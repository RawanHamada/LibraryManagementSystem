/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginScene;

import Entity.Books;
import Entity.DBConnection;
import java.io.File;
import java.io.FileWriter;
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
public class ManageBooksController implements Initializable {

    @FXML
    private TextField txtFieldID;
    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldDescription;
     
    @FXML
    private TableView<Books> tableView;
    @FXML
    private TableColumn<Books, Integer> tcID;
    @FXML
    private TableColumn<Books, String> tcName;
    @FXML
    private TableColumn<Books, String> tcDescription;
 
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
    
    Statement statment;
    Stage primaryStage;
   
    HashMap<Integer, Books> booksHashM = new HashMap();
    @FXML
    private TextField searchTxt;
    @FXML
    private Button buttonBorrower;
    
      PrintWriter pw;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          DBConnection db=new DBConnection();
        try {
          this.statment = db.connection.createStatement();
        } catch (Exception ex) {
           // ex.printStackTrace();
            System.out.println(" Connecting to DBs is not well");        
}
          tcID.setCellValueFactory(new PropertyValueFactory("id"));
        tcName.setCellValueFactory(new PropertyValueFactory("name"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("description"));
        tableView.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedBooks() );
        
    }    

    @FXML
    private void buttonAddHandle(ActionEvent event) throws Exception {
         try {
        Integer id = Integer.parseInt(txtFieldID.getText());
        String name = txtFieldName.getText();
        String description = txtFieldDescription.getText();
        
        String sql = "Insert Into Books values(" + id + ",'" +name + "','" 
                + description  + ")";
        this.statment.executeUpdate(sql);
        
        
        new Alert(Alert.AlertType.CONFIRMATION, "The row is added sucssifully").show();
        pw.println("");

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, " Empty Fields OR miss match input data type . Try again ").show();
        }
    }

    @FXML
    private void buttonSearchHandle(ActionEvent event) {
        try{
        tableView.getItems().setAll();
        booksHashM.keySet().stream().forEach((Integer k) -> {
            Books b = booksHashM.get(k);
            if (b.getId().toString().equals(searchTxt.getText()) || b.getName().equals(searchTxt.getText())
                    || b.getDescription().contains(searchTxt.getText())) {
                System.out.println("found********** " + k);
                new Alert(Alert.AlertType.CONFIRMATION, "The word is found").show();

                tableView.getItems().add(b);
                pw.println("Search Operation {"
                        + searchTxt.getText() + "}:  result matching with Book [ " + b + " ]");

            }
        }
        );
        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "The word is not found ").show();
        }
    }

    @FXML
    private void buttonUpdateHandle(ActionEvent event) throws Exception {
        try{
         Integer id = Integer.parseInt(txtFieldID.getText());
        String name = txtFieldName.getText();
        String description = txtFieldDescription.getText();
        try{
        String sql = "Update Books Set name='" + name + "', department='" + 
                description +  " Where id=" +id;
        this.statment.executeUpdate(sql);
        Books newBook = new Books(id, name, description);
        booksHashM.put(id, newBook);
        pw.println("Updating Book From " + booksHashM.get(id) + " To " + newBook);

        pw.println("---------------------");
        pw.flush();
        tableView.getItems().setAll();
        booksHashM.keySet().stream().sorted().forEach(b -> tableView.getItems().add(booksHashM.get(b)));
                new Alert(Alert.AlertType.INFORMATION, " Done Operation :) ").show();
        } catch (SQLIntegrityConstraintViolationException e) {
                new Alert(Alert.AlertType.ERROR, "Book ID [ " + id + " ] is already exists ").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "ERROR SQL ! Book ID [ " + id + " ] is not exists to update").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, " Empty Fields OR miss match input data type . Try again ").show();
        } 
           new Alert(Alert.AlertType.CONFIRMATION, "The row is added sucssifully").show();
        pw.println("");
    }

    @FXML
    private void buttonDeleteHandle(ActionEvent event) throws Exception {
        Integer id = Integer.parseInt(txtFieldID.getText());
        String sql = "delete from Books where id = " + id + "";
        this.statment.executeUpdate(sql);
        Books k = booksHashM.remove(id);
        ShowBooks();
        new Alert(Alert.AlertType.CONFIRMATION, "Book ID [ " + id + " ] is deleted Successfully").show();        
        pw.println("Delete Book : " + k);
        pw.println("---------------------");
        pw.flush();
        
    }

    @FXML
    private void buttonClearHandle(ActionEvent event) {
        resetControls();
    }
    private void resetControls(){
        txtFieldID.setText("");
        txtFieldName.setText("");
        txtFieldDescription.setText("");
        tableView.getItems().clear();
    }
     private void ShowBooks() throws Exception{
    ResultSet rs = this.statment.executeQuery("Select * From Books");
        tableView.getItems().clear();
        while(rs.next()){
            Books book = new Books();
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setDescription(rs.getString("description"));
           
            tableView.getItems().add(book);
        }
     }

    private void showSelectedBooks(){
        Books book = tableView.getSelectionModel().getSelectedItem();
        if(book != null){
        txtFieldID.setText(String.valueOf(book.getId()));
        txtFieldName.setText(book.getName());
        txtFieldDescription.setText(book.getDescription());
         }
    
}

    @FXML
    private void buttonBorrowerHandle(ActionEvent event) throws Exception {
           // FXMLLoader.load(getClass().getResource("ManagementBorrowers.fxml"));
        Parent borrowBook = FXMLLoader.load(getClass().getResource("ManagementBorrowers.fxml"));   

        Scene scene3 = new Scene(borrowBook);
        //scene2=new Scene (book);
        //primaryStage.setTitle("Manage Book");
        primaryStage.setScene(scene3);
        primaryStage.show();
    }
}
