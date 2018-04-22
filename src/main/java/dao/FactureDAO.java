/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Facture;
import modele.Periode;
/**
 *
 * @author delevoyj
 */
public class FactureDAO extends AbstractDataBaseDAO {
    
    public FactureDAO(DataSource ds) {
        super(ds);
    }
    
    public List<Facture> getAllFacture(String loginParent) {
        List<Facture> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Factures WHERE loginParent='"+loginParent.trim()+"'");
            while (rs.next()) {
                Periode periode = new Periode(rs.getString("dateDebut").trim(), rs.getString("dateFin").trim());
                result.add(new Facture(rs.getInt("prixTotal"), rs.getInt("montantEnleve"), rs.getInt("montantFinal"), periode));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public Facture getFacture(String loginParent, String dateDebut, String dateFin) {
        Facture result = null;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Factures WHERE loginParent='"+loginParent.trim()+"' and dateDebut='"+dateDebut.trim()+"' and dateFin='"+dateFin.trim()+"'");
            if (rs.next()) {
                Periode periode = new Periode(dateDebut, dateFin);
                result = new Facture(rs.getInt("prixTotal"), rs.getInt("montantEnleve"), rs.getInt("montantFinal"), periode);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public void ajoutFacture(String loginParent, String dateDebut, String dateFin, int prixTotal, int montantEnlever, int montantFinal) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Factures (loginParent, dateDebut, dateFin, prixTotal, montantEnleve, montantFinal) VALUES (?, ?, ?, ?, ?, ?)");
	     ) {
            st.setString(1, loginParent);
            st.setString(2, dateDebut);
            st.setString(3, dateFin);
            st.setInt(4, prixTotal);
            st.setInt(5, montantEnlever);
            st.setInt(6, montantFinal);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
