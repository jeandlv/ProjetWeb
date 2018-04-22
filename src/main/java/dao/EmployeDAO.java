package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bernnico
 */
public class EmployeDAO extends AbstractDataBaseDAO {
    
    public EmployeDAO(DataSource ds) {
        super(ds);
    }
    
    public boolean verify(String login, String password) {
        boolean test = false;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYE WHERE login='" + login +"' AND mdp='"+password+"'");
            if (rs.next()) {
                test = true;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return test;
    }
    
    
    public void creation(String login, String password) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO EMPLOYE (login,mdp)"
                       + "VALUES"
                       + "('"+login+"','"+password+"')");
                ){
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public boolean verifyLogin(String login) {
        boolean test = false;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYE WHERE login='" + login +"'");
            if (rs.next()) {
                test = true;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return test;
    }
}
