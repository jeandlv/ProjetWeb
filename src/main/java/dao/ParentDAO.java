/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Cantine;
import modele.FicheEnfant;
import modele.FicheParent;
import modele.Periode;

/**
 *
 * @author boussanl
 */
public class ParentDAO extends AbstractDataBaseDAO {
    
    public ParentDAO(DataSource ds) {
        super(ds);
    }
    
    public List<String> getParents() {
        List<String> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Parent");
            while (rs.next()) {
                result.add(rs.getString("login"));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return result;
    }
    
    /**
     * Renvoie la fiche parent de la table parents avec tout ses attributs.
     */
    public FicheParent getFicheParent(String login) {
        FicheParent result = null;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Parent WHERE login='" + login +"'");
            String nom = null, prenom = null, adresse = null;
            while (rs.next()) {
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                adresse = rs.getString("adresse");
            }
            
            List<FicheEnfant> enfants = new ArrayList<FicheEnfant>();    
            rs = st.executeQuery("SELECT * FROM Enfant WHERE loginParent='" + login +"'");
            while (rs.next()) {
                String nomEnfant = rs.getString("nom");
                String sexe = rs.getString("sexe");
                String prenomEnfant = rs.getString("prenom");
                String dateNaissance = rs.getString("datedeNaissance");
                String divers = rs.getString("divers");
                String regime = rs.getString("regime");
                String classe = rs.getString("classe");
                Cantine cantine = new Cantine(rs.getString("cantine"));
                enfants.add(new FicheEnfant(sexe, classe, dateNaissance, divers, regime, result, cantine, null, nomEnfant, prenomEnfant));
            }
            result = new FicheParent(adresse, login, enfants, nom, prenom);

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public FicheEnfant getFicheEnfant(String login, String nomEnfant, String prenomEnfant) {
        FicheEnfant result = null;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
  
            ResultSet rs = st.executeQuery("SELECT * FROM Enfant WHERE loginParent='" + login +"'"
                    + "AND nom='" + nomEnfant + "' AND prenom='" + prenomEnfant + "'");
            while (rs.next()) {
                String sexe = rs.getString("sexe");
                String dateNaissance = rs.getString("datedeNaissance");
                String divers = rs.getString("divers");
                String regime = rs.getString("regime");
                String classe = rs.getString("classe");
                Cantine cantine = new Cantine(rs.getString("cantine"));
                result = new FicheEnfant(sexe, classe, dateNaissance, divers, regime, null, cantine, null, nomEnfant, prenomEnfant);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public boolean verify(String login, String password) {
        boolean test = false;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Parent WHERE login='" + login +"' AND mdp='"+password+"'");
            if (rs.next()) {
                test = true;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return test;
    }
    
    public void supprimerEnfant(String loginParent, String prenomEnfant) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM enfant WHERE loginParent='" + loginParent +"' AND prenom='" + prenomEnfant + "'");
	     ) {
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void ajoutEnfant(FicheEnfant ficheEnfant, String loginParent) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement("INSERT INTO Enfant (sexe,"
                    + " nom, prenom, loginparent, classe, datedenaissance,"
                    + " cantine, regime, divers) values ('" + ficheEnfant.getSexe()
                     + "','" + ficheEnfant.getNom()
                     + "','" + ficheEnfant.getPrenom()
                     + "','" + loginParent
                     + "','" + ficheEnfant.getClasse()
                     + "','" + ficheEnfant.getDateNaissance()
                     + "','" + ficheEnfant.getCantine().toString()
                     + "','" + ficheEnfant.getRegime()
                     + "','" + ficheEnfant.getDivers()
                     + "')");
	     ) {
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }
    
    public void modifierInfo(String newAdresse, String newNom, String newPrenom, String login) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE PARENT "
                       + "SET NOM = '"+ newNom +"',"
                       + "PRENOM = '" + newPrenom +"',"
                       + "ADRESSE = '" + newAdresse + "'"
                       + "WHERE LOGIN = '"+login+"'");
	     ) {
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void creation(String login, String password, String nom, String prenom, String adresse) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO PARENT (nom, prenom, adresse, login, mdp)"
                       + "VALUES"
                       + "('"+nom+"','"+prenom+"','"+adresse+"','"+login+"','"+password+"')");
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
            ResultSet rs = st.executeQuery("SELECT * FROM Parent WHERE login='" + login +"'");
            if (rs.next()) {
                test = true;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return test;
    }
    
    
}
