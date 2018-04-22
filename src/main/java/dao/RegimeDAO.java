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


/**
 *
 * @author boussanl
 */
public class RegimeDAO extends AbstractDataBaseDAO {
    
    public RegimeDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Renvoie la liste des regimes de la table regimes.
     */
    public List<String> getListeRegime() {
        List<String> result = new ArrayList<String>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Regimes ORDER BY regime");
            while (rs.next()) {
                result.add(rs.getString("regime"));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    /**
     * Supprime le regime
     */
    public void supprimerRegime(String regime) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM regimes WHERE regime='" + regime+"'");
	     ) {
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Ajoute le regime donné dans la table regimes
     */
    public void ajouterRegime(String regime) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO regimes (regime) VALUES (?)");
	     ) {
            st.setString(1, regime);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public boolean existeDeja(String nom) {
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM regimes WHERE regime='"+nom+"'");
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
