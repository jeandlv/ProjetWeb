/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import javax.sql.DataSource;
import modele.Activite;
/**
 *
 * @author delevoyj
 */
public class AbsenceDAO extends AbstractDataBaseDAO {
    
    public AbsenceDAO(DataSource ds) {
        super(ds);
    }
    
    public void ajoutAbsence(String nomActivite, String creneauxJour, String creneauxHeure, String dateAbsence, String loginParent, String prenomEnfant) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Absences (nomActivite, creneauxJour, creneauxHeure, dateAbsence, loginParent, nomEnfant) VALUES (?, ?, ?, ?, ?, ?)");
	     ) {
            st.setString(1, nomActivite);
            st.setString(2, creneauxJour);
            st.setString(3, creneauxHeure);
            st.setString(4, dateAbsence);
            st.setString(5, loginParent);
            st.setString(6, prenomEnfant);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public boolean verificationAbsence(String nomActivite, String creneauxJour, String creneauxHeure, String dateAbsence, String loginParent, String prenomEnfant) {
        boolean test = false;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Absences WHERE nomActivite='" + nomActivite +"' AND creneauxJour='"+creneauxJour+"' "
                    + "AND creneauxHeure='" + creneauxHeure + "' AND dateAbsence='" + dateAbsence + "' AND loginParent='" + loginParent + "'"
                            + " AND nomEnfant='" + prenomEnfant + "'");
            if (rs.next()) {
                test = true;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return test;
    }
    
    public void finPeriode() {
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            st.executeQuery("DELETE FROM Absences");
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }
    
    public int getAbsences(String nomActivite, String creneauxJour, String creneauxHeure, String loginParent, String prenomEnfant) {
        int result = 0;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Absences WHERE nomActivite='" + nomActivite.trim() +"' AND creneauxJour='"+creneauxJour.trim()+"' "
                    + "AND creneauxHeure='" + creneauxHeure.trim() + "' AND loginParent='" + loginParent.trim() + "'"
                            + " AND nomEnfant='" + prenomEnfant.trim() + "'");
            while (rs.next()) {
                result++;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return result;
    }
}
