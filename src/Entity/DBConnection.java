/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jit
 */
public class DBConnection {
    public Connection connection;
    public DBConnection() {
          try {
        FileWriter fw = new FileWriter(new File("src/trans.txt"), true);
        PrintWriter pw = new PrintWriter(fw);
         Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection =
               DriverManager.
                getConnection("jdbc:mysql://127.0.0.1:3306/librarymanagement?serverTimezone=UTC",
                        "root", "");
           
        } catch (Exception ex) {
           // ex.printStackTrace();
            System.out.println(" Connecting to DBs is not well");
    
}
}
}