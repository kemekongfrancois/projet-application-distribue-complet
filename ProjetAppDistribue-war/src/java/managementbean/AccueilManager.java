/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementbean;

import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import session.ManageBean;
 
@ManagedBean
public class AccueilManager {

    @EJB
    private ManageBean manageBean;
     
    private String login;
    private String pass;
 
    
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}