/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author boussanl
 */
public class Periode {
    private GregorianCalendar dateDebut;
    private GregorianCalendar dateFin;
    private GregorianCalendar dateActuelle;

    public Periode(GregorianCalendar dateDebut, GregorianCalendar dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;    
        this.dateActuelle = new GregorianCalendar();     
    }
    
    public Periode(){
        this.dateActuelle = new GregorianCalendar(); 
    }
    
    public Periode(String dateDebut, String dateFin) {
        String[] debut = dateDebut.split("-");
        String[] fin = dateFin.split("-");
        this.dateDebut = new GregorianCalendar(Integer.parseInt(debut[0].trim()), Integer.parseInt(debut[1].trim())-1, Integer.parseInt(debut[2].trim()));
        this.dateFin = new GregorianCalendar(Integer.parseInt(fin[0].trim()), Integer.parseInt(fin[1].trim())-1, Integer.parseInt(fin[2].trim()));
        this.dateActuelle = new GregorianCalendar();
    }

    public GregorianCalendar getDateDebut() {
        return dateDebut;
    }
    
    public boolean estFini() {
        return dateActuelle.after(dateFin);
    }
    
    public boolean periodeIncorrecte() {
        return dateDebut.after(dateFin);
    }
    
    public boolean avantDateActuelle() {
        return dateActuelle.after(dateDebut);
    }
    
    public String getProchainCreneaux(String creneauxJour, String creneauxHeure) {
        int jourActuel = dateActuelle.get(Calendar.DAY_OF_WEEK);
        GregorianCalendar prochainCreneaux = new GregorianCalendar();
        int creneaux = parserJour(creneauxJour.trim());
        System.out.println("Creneaux :" + creneaux);
        System.out.println("JourActuel :" +jourActuel);
        if ((creneaux - jourActuel) <= 2) {
            return "";
        }
        prochainCreneaux.add(Calendar.DAY_OF_MONTH, (creneaux - jourActuel) % 7);
        return dateToString(prochainCreneaux);
    }
    
    public int parserJour(String creneauxJour) {
        switch (creneauxJour) {
            case "lundi":
                return 2;
            case "mardi":
                return 3;
            case "mercredi":
                return 4;
            case "jeudi":
                return 5;
            case "vendredi":
                return 6;
            case "samedi":
                return 7;
            default:            
                return 1;
        }
    }
    
    public boolean chevauchement(Periode periode) {
        if (periode.getDateDebut().after(dateDebut) && periode.getDateDebut().before(dateFin)) {
            return true;
        } else if (periode.getDateFin().after(dateDebut) && periode.getDateFin().before(dateFin)) {
            return true;
        }
        return false;
    }
    
    public boolean chevauchementList (List<Periode> periodes) {
        for (Periode periode : periodes) {
            if (chevauchement(periode)) {
                return true;
            }
        }
        return false;
    }
            
    public boolean estEnCours() {
        return dateActuelle.after(dateDebut) && dateActuelle.before(dateFin);
    }
    
    public int getNbSemaines() {
        long nbSemaines = (dateDebut.getTimeInMillis() - dateFin.getTimeInMillis()) / (86400000 * 7);
        return (int) Math.abs(nbSemaines);
    }
    
    public String dateToString(GregorianCalendar date) {
        String result = "";
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH)+1;
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        if (year < 1000) {
            result += "0";
        }
        result += year + "-";
        if (month < 10) {
            result += "0";
        }
        result += month + "-";
        if (day < 10) {
            result += "0";
        }
        result += day;
        return result;
    }
    
    public String debutToString() {
        return dateToString(dateDebut);
    }
    
    public String dateActuelleToString() {
        return dateToString(dateActuelle);
    }
    
    public String finToString() {
        return dateToString(dateFin);
    }

    public GregorianCalendar getDateFin() {
        return dateFin;
    }

    public GregorianCalendar getDateActuelle() {
        return dateActuelle;
    }
    
}
