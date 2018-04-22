/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Periode;

/**
 *
 * @author boussanl
 */
public class PeriodeDAO extends AbstractDataBaseDAO {

    public PeriodeDAO(DataSource ds) {
        super(ds);
    }

    public List<Periode> getPeriodes() {
        List<Periode> result = new ArrayList<Periode>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Periode");
            while (rs.next()) {
                String dateDebut = rs.getString("dateDebut");
                String dateFin = rs.getString("dateFin");
                result.add(new Periode(dateDebut, dateFin));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return result;
    }
    
    public void supprimerPeriode(String dateDebut, String dateFin) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM Periode WHERE dateDebut='" + dateDebut +
                       "' AND dateFin='" + dateFin +"'");
	     ) {
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Ajoute la periode donné dans la table periode
     */
    public void ajouterPeriode(String debut, String fin) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Periode (dateDebut, dateFin) VALUES (?, ?)");
	     ) {
            st.setString(1, debut);
            st.setString(2, fin);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
