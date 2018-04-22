/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import modele.Activite;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import modele.FicheEnfant;
import modele.Garderie;
import modele.Periode;

/**
 *
 * @author boussanl
 */
public class ActiviteDAO extends AbstractDataBaseDAO {

    public ActiviteDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Renvoie la liste des activites de la table activites.
     */
    public List<Activite> getListeActivite() {
        List<Activite> result = new ArrayList<Activite>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Activites ORDER BY nom");
            while (rs.next()) {
                String nom = rs.getString("nom");
                String classe = rs.getString("classe");
                List<String> listeClasse = new ArrayList<String>();
                if (classe.contains("/")) {
                    String[] classeDiviser = classe.split("/");
                    listeClasse = Arrays.asList(classeDiviser);
                }
                else {
                    listeClasse.add(classe);
                }
                String creneauxJour = rs.getString("creneauxJour");
                String creneauxHeure = rs.getString("creneauxHeure");
                Periode periode = new Periode(rs.getString("dateDebut"), rs.getString("dateFin"));
                result.add(new Activite(nom, creneauxJour, creneauxHeure, listeClasse, rs.getInt("prix"), 
                        rs.getInt("effectif"), rs.getString("mailaccompagnateur1"), rs.getString("mailaccompagnateur2"), periode));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public List<Activite> getListeActivite(Periode periode) {
        List<Activite> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Activites WHERE dateDebut='" +periode.debutToString()+ "' AND dateFin='" + periode.finToString() + "'");
            while (rs.next()) {
                String nom = rs.getString("nom");
                String classe = rs.getString("classe");
                List<String> listeClasse = new ArrayList<>();
                if (classe.contains("/")) {
                    String[] classeDiviser = classe.split("/");
                    listeClasse = Arrays.asList(classeDiviser);
                }
                else {
                    listeClasse.add(classe);
                }
                String creneauxJour = rs.getString("creneauxJour");
                String creneauxHeure = rs.getString("creneauxHeure");
                result.add(new Activite(nom, creneauxJour, creneauxHeure, listeClasse, rs.getInt("prix"), 
                        rs.getInt("effectif"), rs.getString("mailaccompagnateur1"), rs.getString("mailaccompagnateur2"), periode));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public List<Garderie> getGarderie(String prenomEnfant, String loginParent) {
        List<Garderie> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM ActiviteReserve WHERE prenomEnfant='" +prenomEnfant+ "' AND loginParent='" + loginParent + "' AND nomActivite='Garderie'");
            while (rs.next()) {
                String creneauxJour = rs.getString("creneauxJour");
                String creneauxHeure = rs.getString("creneauxHeure");
                String dateDebut = rs.getString("dateDebut");
                String dateFin = rs.getString("dateFin");
                result.add(new Garderie(creneauxJour, creneauxHeure, dateDebut, dateFin));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return result;
    }
    
     public List<Garderie> getGarderie(String prenomEnfant, String loginParent, Periode periode) {
        List<Garderie> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM ActiviteReserve WHERE prenomEnfant='" +prenomEnfant+ "' AND loginParent='" + loginParent + "' AND nomActivite='Garderie'"
                    + " AND dateDebut='" +periode.debutToString()+ "' AND dateFin='" +periode.finToString()+ "'");
            while (rs.next()) {
                String creneauxJour = rs.getString("creneauxJour");
                String creneauxHeure = rs.getString("creneauxHeure");
                String dateDebut = rs.getString("dateDebut");
                String dateFin = rs.getString("dateFin");
                result.add(new Garderie(creneauxJour, creneauxHeure, dateDebut, dateFin));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
        return result;
    }
    
    /**
     * Renvoie la liste des activites de la table activite dont l'enfant a le droit de selectionner.
     */
    public List<Activite> getListeActivite(FicheEnfant ficheEnfant, String loginParent) {
        List<Activite> listActivite = this.getListeActivite();
        List<Activite> reserved = this.getReserver(ficheEnfant, loginParent);
        List<Activite> result = new ArrayList<>();
        // On enleve toutes les activites qui ont déjà été reservé par l'enfant
        listActivite.removeAll(reserved);
        for (Activite activite : listActivite) {
            // Verifie que l'enfant est bien dans une classe autorisé pour cette activité
            // Verifie que l'enfant n'a pas déjà selectionner une activité lors de ce créneaux
            if (activite.testClasse(ficheEnfant.getClasse()) && activite.testCreneaux(reserved)) {
                result.add(activite);
            }
        }
        return result;
    }
    
    public Periode getPeriode(String nomActivite, String creneauxJour, String creneauxHeure) {
        Periode result = null;
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM activites WHERE nom='"+nomActivite.trim()+"' and creneauxJour='"+creneauxJour.trim()+"' and creneauxHeure='"+creneauxHeure.trim()+"'");
            if (rs.next()) {
                String dateDebut = rs.getString("dateDebut");
                String dateFin = rs.getString("dateFin");
                result = new Periode(dateDebut, dateFin);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public void miseAJour(int effectif, String nomActivite, String creneauxJour, String creneauxHeure) {
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
            ) {
            int resultat = 0;
            ResultSet rs = st.executeQuery("SELECT COUNT(nomActivite) AS total FROM "
                    + "activiteReserve WHERE nomActivite='"+nomActivite+"' and"
                    + " creneauxJour='"+creneauxJour+"' and creneauxHeure='"+creneauxHeure+"'");
            if (rs.next()) {
                resultat = rs.getInt("total");
            }
            if (resultat >= effectif) {
                st.executeQuery("DELETE TOP(" + (resultat-effectif) + ") FROM"
                        + " activiteReserve WHERE nomActivite='"+nomActivite+"'"
                        + " and creneauxJour='"+creneauxJour+"' and creneauxHeure='"+creneauxHeure+"'");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Renvoie la liste des activités reservé par l'enfant
     */
    public List<Activite> getReserver(FicheEnfant ficheEnfant, String loginParent) {
        List<Activite> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM ActiviteReserve AR JOIN Activites A ON "
                    + "nomActivite=nom AND AR.creneauxJour=A.creneauxJour "
                    + "AND AR.creneauxHeure=A.creneauxHeure WHERE AR.prenomEnfant='"
                    + ficheEnfant.getPrenom() + "' AND AR.loginParent='" + 
                    loginParent + "'");
            while (rs.next()) {
                String nom = rs.getString("nom");
                String classe = rs.getString("classe");
                List<String> listeClasse = new ArrayList<>();
                if (classe.contains("/")) {
                    String[] classeDiviser = classe.split("/");
                    listeClasse = Arrays.asList(classeDiviser);
                }
                else {
                    listeClasse.add(classe);
                }
                String creneauxJour = rs.getString("creneauxJour");
                String creneauxHeure = rs.getString("creneauxHeure");
                Periode periode = new Periode(rs.getString("dateDebut"), rs.getString("dateFin"));
                result.add(new Activite(nom, creneauxJour, creneauxHeure, listeClasse, rs.getInt("prix"), 
                        rs.getInt("effectif"), rs.getString("mailaccompagnateur1"), rs.getString("mailaccompagnateur2"), periode));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public List<Activite> getReserver(FicheEnfant ficheEnfant, String loginParent, Periode periode) {
        List<Activite> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM ActiviteReserve AR JOIN Activites A ON "
                    + "nomActivite=nom AND AR.creneauxJour=A.creneauxJour "
                    + "AND AR.creneauxHeure=A.creneauxHeure WHERE AR.prenomEnfant='"
                    + ficheEnfant.getPrenom() + "' AND AR.loginParent='" + 
                    loginParent + "' AND AR.dateDebut='" + periode.debutToString().trim() +
                    "' AND AR.dateFin='" + periode.finToString().trim() + "'");
            while (rs.next()) {
                String nom = rs.getString("nom");
                String classe = rs.getString("classe");
                List<String> listeClasse = new ArrayList<>();
                if (classe.contains("/")) {
                    String[] classeDiviser = classe.split("/");
                    listeClasse = Arrays.asList(classeDiviser);
                }
                else {
                    listeClasse.add(classe);
                }
                String creneauxJour = rs.getString("creneauxJour");
                String creneauxHeure = rs.getString("creneauxHeure");
                result.add(new Activite(nom, creneauxJour, creneauxHeure, listeClasse, rs.getInt("prix"), 
                        rs.getInt("effectif"), rs.getString("mailaccompagnateur1"), rs.getString("mailaccompagnateur2"), periode));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    /**
     * Ajoute l'activité dans la table activité
     */
    public void ajouterActivite(String nom, String creneauxJour, String creneauxHeure, String classe, int prix, int effectif, String mail1, String mail2, String dateDebut, String dateFin) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO activites (nom, creneauxJour, creneauxHeure, classe, prix, effectif, mailAccompagnateur1, mailAccompagnateur2, dateDebut, dateFin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	     ) {
            st.setString(1, nom);
            st.setString(2, creneauxJour);
            st.setString(3, creneauxHeure);
            st.setString(4, classe);
            st.setInt(5, prix);
            st.setInt(6, effectif);
            st.setString(7, mail1);
            st.setString(8, mail2);
            st.setString(9, dateDebut);
            st.setString(10, dateFin);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void supprimerActivite(String nom, String creneauxJour, String creneauxHeure) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM activites WHERE  nom='"+nom+"' and creneauxJour='"+creneauxJour+"' and creneauxHeure='"+creneauxHeure+"'");
	     ) {
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
     
    public boolean existeDeja(String nom, String jour, String heure) {
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM activites WHERE nom='"+nom+"' and creneauxJour='"+jour+"' and creneauxHeure='"+heure+"'");
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Reserver l'activité dans la table activité
     */
    public void reserverActivite(String prenomEnfant, String loginParent, String nomActivite, String creneauxJour, String creneauxHeure, String dateDebut, String dateFin) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO activitereserve (prenomEnfant, loginParent, nomActivite, creneauxJour, creneauxHeure, dateDebut, dateFin) VALUES (?, ?, ?, ?, ?, ?, ?)");
	     ) {
            st.setString(1, prenomEnfant.trim());
            st.setString(2, loginParent.trim());
            st.setString(3, nomActivite.trim());
            st.setString(4, creneauxJour.trim());
            st.setString(5, creneauxHeure.trim());
            st.setString(6, dateDebut.trim());
            st.setString(7, dateFin.trim());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void finPeriode(Periode periode) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM activitereserve WHERE dateDebut='" + periode.debutToString().trim()
                       + "' and dateFin='" + periode.finToString().trim() + "'");
	     PreparedStatement st2 = conn.prepareStatement
	       ("DELETE FROM activites WHERE dateDebut='" + periode.debutToString().trim()
                       + "' and dateFin='" + periode.finToString().trim() + "'");
                ) {
            st.executeUpdate();
            st2.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Supprimer l'activité dans la table activité
     */
    public void supprimerActivite(String prenomEnfant, String loginParent, String nomActivite, String creneauxJour, String creneauxHeure) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM activitereserve WHERE prenomEnfant='" + prenomEnfant.trim()
                       + "' and loginparent='" + loginParent.trim()
                       + "' and nomActivite='" + nomActivite.trim()
                       + "' and creneauxJour='" + creneauxJour.trim()
                       + "' and creneauxHeure='" + creneauxHeure.trim() + "'");
	     ) {
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
}
