/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginScene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author jit
 */
public class Loginapp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
         FileWriter fw = new FileWriter(new File("src/trans.txt"), true);
         PrintWriter pw = new PrintWriter(fw);
       // Parent log = FXMLLoader.load(getClass().getResource("Login.fxml")); 
     //   Parent book = FXMLLoader.load(getClass().getResource("ManageBooks.fxml"));   
        Parent borrowBook = FXMLLoader.load(getClass().getResource("ManagementBorrowers.fxml"));   
        pw.flush();
        pw.close();
        Scene scene = new Scene(borrowBook);
        //scene2=new Scene (book);
        primaryStage.setTitle("Library Management");
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException e) {
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
