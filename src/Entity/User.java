/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;

/**
 *
 * @author jit
 */
public class User implements Serializable{
    private String username;
    private String pass;
    private  Integer id;
    
    public User(){
        
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public User(Integer id,String username, String pass) {
        this.id=id;
        this.username = username;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }
    

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", pass=" + pass + '}';
    }
    
    
}
