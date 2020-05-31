/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginScene;

import Entity.DBConnection;
import Entity.User;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.bind.DatatypeConverter;

/**
 * FXML Controller class
 *
 * @author jit
 */
public class LoginController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameText;
    @FXML
    private Label passLabel;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button loginbutton;
    
    Statement statement;
    Stage primaryStage;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBConnection db=new DBConnection();
        try {
          this.statement = db.connection.createStatement();
        } catch (Exception ex) {
           // ex.printStackTrace();
            System.out.println(" Connecting to DBs is not well");        
}       
    }    

    @FXML
    private void loginbuttonHandle(ActionEvent event) {
          User u =new User();
          String checkUsername = usernameText.getText();
          String checkpass = passwordText.getText();
          getUsers();
          
         try {
            if (checkUsername.equals(u.getUsername()) && (passwordUsersInHash(checkpass)).equals(u.getPass()) ) {
                FXMLLoader.load(getClass().getResource("ManageBooks.fxml"));
//                    la.Stage.setScene(la.scene2);
//                   la.Stage.show();
               Parent book = FXMLLoader.load(getClass().getResource("ManageBooks.fxml"));   

                Scene scene2 = new Scene(book);
                primaryStage.setTitle("Manage Book");
                primaryStage.setScene(scene2);
                primaryStage.show();  
                } else {
                     Alert a =new Alert(Alert.AlertType.INFORMATION);
                     a.setTitle("Warning");
                     a.setHeaderText("Invalid username or password.");
                    a.showAndWait();
                }
                }catch (NoSuchAlgorithmException ex) {
                         Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }catch (Exception ex) {   
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }   
                
             }
             
         
     public String passwordUsersInHash(String password) throws Exception {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest);
        }
        public void getUsers() {
        try {
            ResultSet rs = this.statement.executeQuery("Select * From Users");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPass(rs.getString("password"));
            }
        } catch (Exception ex) {
            System.out.println("Error in Getting users");
        }
    }
         
    
}
