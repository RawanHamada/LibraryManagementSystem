/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.sql.Date;

/**
 *
 * @author jit
 */
public class BorrowerBooks {
    private Integer Book_id;
    private Integer Borrower_id ;
    private Date borrowers_date;
    private Date Return_date;

    public BorrowerBooks() {
    }

    public BorrowerBooks(Integer Book_id, Integer Borrower_id, Date borrowers_date, Date Return_date) {
        this.Book_id = Book_id;
        this.Borrower_id = Borrower_id;
        this.borrowers_date = borrowers_date;
        this.Return_date = Return_date;
    }

    public Integer getBook_id() {
        return Book_id;
    }

    public void setBook_id(Integer Book_id) {
        this.Book_id = Book_id;
    }

    public Integer getBorrower_id() {
        return Borrower_id;
    }

    public void setBorrower_id(Integer Borrower_id) {
        this.Borrower_id = Borrower_id;
    }

    public Date getBorrowers_date() {
        return borrowers_date;
    }

    public void setBorrowers_date(Date borrowers_date) {
        this.borrowers_date = borrowers_date;
    }

    public Date getReturn_date() {
        return Return_date;
    }

    public void setReturn_date(Date Return_date) {
        this.Return_date = Return_date;
    }
    
}
