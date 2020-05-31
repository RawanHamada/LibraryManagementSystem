/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginScene;

import Entity.Books;
import Entity.BorrowerBooks;
import Entity.Borrowers;
import Entity.DBConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jit
 */
public class ManagementBorrowerBooksController implements Initializable {

    @FXML
    private TableColumn<Books, Integer> tcID;
    @FXML
    private TableColumn<Books, String> tcName;
    @FXML
    private TableColumn<Books, Books> tcDescription;
    @FXML
    private TableColumn<Borrowers, Integer> tcID2;
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
    private TableColumn<BorrowerBooks, Integer> tcbookid;
    @FXML
    private TableColumn<BorrowerBooks, Integer> tcborrowerid;
    @FXML
    private TableColumn<BorrowerBooks, Date> tcborrowerdate;
    @FXML
    private TableColumn<BorrowerBooks, Date > tcreturndate;
    @FXML
    private Button buttonBorrow;
    @FXML
    private TextField txtBookid;
    @FXML
    private TextField txtBorrowid;
    @FXML
    private TextField txtBorrowdate;
    @FXML
    private TextField txtReturndate;
    @FXML
    private TableView<Books> TvBook;
    @FXML
    private TableView<Borrowers> TvBorrow;
    @FXML
    private TableView<BorrowerBooks> TvRelation;
    
    Statement statment;
    PrintWriter pw;
    HashMap<Integer, Books> booksHM = new HashMap();
    @FXML
    private Button buttonReturn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //  try {
//           FileWriter fw = new FileWriter(new File("src/trans.txt"), true);
//            pw = new PrintWriter(fw);
            DBConnection db=new DBConnection();
        try {
          this.statment = db.connection.createStatement();
        } catch (Exception ex) {
           // ex.printStackTrace();
            System.out.println(" Connecting to DBs is not well");        
}
          tcbookid.setCellValueFactory(new PropertyValueFactory("id"));
        tcborrowerid.setCellValueFactory(new PropertyValueFactory("name"));
        tcborrowerdate.setCellValueFactory(new PropertyValueFactory("description"));
        tcreturndate.setCellValueFactory(new PropertyValueFactory("description"));

        TvRelation.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedBookBorrow());
        
    }    

    @FXML
    private void buttonBorrowHandle(ActionEvent event) {
         try {
                int d1 = Integer.parseInt(txtBookid.getText());
                int d2 = Integer.parseInt(txtBorrowid.getText());
                Date bd = Date.valueOf(txtBorrowdate.getText());
                Date rd = Date.valueOf(txtReturndate.getText());
                this.statment.executeUpdate("insert into borrower_books values(" + d1 + ","
                        + d2 + ",'" + bd + "','" + rd + "')");
                BorrowerBooks bb = new BorrowerBooks(d1, d2, bd, rd);
                TvRelation.getItems().add(bb);
                TvBook.getItems().remove(booksHM.get(Integer.parseInt(txtBookid.getText())));
                pw.println("The Borrower " + d2 + " borrow A Book ID " + d1 + " from " + bd + " To " + rd);
                pw.println("-----------------");
                pw.flush();
                
            } catch (SQLException ex) {
                System.out.println("Error in inserting borrower_book ");
            }
    }

    @FXML
    private void buttonReturnHandle(ActionEvent event) {
         BorrowerBooks x = TvRelation.getSelectionModel().getSelectedItem();
            try {
                Integer id = x.getBook_id();
                this.statment.executeUpdate("delete from borrower_books where Book_id =" + id);
                TvBook.getItems().add(booksHM.get(x.getBook_id()));
                TvRelation.getItems().remove(x);
                pw.println("The  Book ID " + id + " is Returned and Now available to borrow agaiin");
                pw.println("------------------");
                pw.flush();
            } catch (SQLException ex) {
                System.out.println("Error in delete from borrower_book ");
            }
        
    }
    private void showSelectedBookBorrow(){
        BorrowerBooks bbook = TvRelation.getSelectionModel().getSelectedItem();
        if(bbook != null){
        txtBookid.setText(String.valueOf(bbook.getBook_id()));
        txtBorrowid.setText(String.valueOf(bbook.getBorrower_id()) );
        txtBorrowdate.setText(String.valueOf(bbook.getBorrowers_date()));
        txtReturndate.setText(String.valueOf(bbook.getReturn_date()));

         }
    
}
}