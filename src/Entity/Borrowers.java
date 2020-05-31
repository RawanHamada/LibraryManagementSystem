/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author jit
 */
public class Borrowers {
     private Integer id;
    private  String FirstName;
    private  String LastName;
    private  Integer Mobile;
    private  String Email;
    private  String Address;
    private  String Gender;

    public Borrowers(Integer id, String FirstName, String LastName, Integer Mobile, String Email, String Address, String Gender) {
        this.id = id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Mobile = Mobile;
        this.Email = Email;
        this.Address = Address;
        this.Gender = Gender;
    }

    public Borrowers() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public Integer getMobile() {
        return Mobile;
    }

    public void setMobile(Integer Mobile) {
        this.Mobile = Mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    @Override
    public String toString() {
        return "Borrowers{" + "id=" + id + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Mobile=" + Mobile + ", Email=" + Email + ", Address=" + Address + ", Gender=" + Gender + '}';
    }
        
    
    
}
