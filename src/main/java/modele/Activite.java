/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author boussanl
 */
public class Activite extends Prestation{
    private String nom;
    private String creneauxJour;
    private String creneauxHeure;
    private List<String> classe;
    private int prix;
    private int effectif;
    private String accompagnateur1;
    private String accompagnateur2;
    private Periode periode;

    public Activite(String nom, String creneauxJour, String creneauxHeure, List<String> classe, int prix, int effectif, String accompagnateur1, String accompagnateur2, Periode periode) {
        this.nom = nom;
        this.creneauxJour = creneauxJour;
        this.creneauxHeure = creneauxHeure;
        this.classe = classe;
        this.prix = prix;
        this.effectif = effectif;
        this.accompagnateur1 = accompagnateur1;
        this.accompagnateur2 = accompagnateur2;
        this.periode = periode;
    }

    public Periode getPeriode() {
        return periode;
    }
    
    public boolean testClasse(String element) {
        for (String string : classe) {
            if (string.trim().equalsIgnoreCase(element.trim())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean testCreneaux(List<Activite> reserved) {
        for (Activite activite : reserved) {
            if (activite.getCreneauxHeure().equals(creneauxHeure) && activite.getCreneauxJour().equals(creneauxJour)) {
                return false;
            }
        }
        return true;
    }

    public String getCreneauxHeure() {
        return creneauxHeure;
    }

    public String getCreneauxJour() {
        return creneauxJour;
    }

    public int getEffectif() {
        return effectif;
    }

    public int getPrix() {
        return prix;
    }

    public String getClasse() {
        return classe.toString();
    }

    public String getNom() {
        return nom;
    }
        
    public String getAccompagnateur1() {
        return accompagnateur1;
    }

    public String getAccompagnateur2() {
        return accompagnateur2;
    }

    @Override
    public String toString() {
        String result = nom + " le " + creneauxJour + " à " + creneauxHeure;
        return result;
    }
    
    public int getPrixIndiv() {
        int nbSemaines = periode.getNbSemaines();
        return prix/nbSemaines;
    }
}
