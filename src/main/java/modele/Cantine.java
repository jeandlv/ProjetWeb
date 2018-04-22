/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author bernnico
 */
public class Cantine {
    private boolean lundi = false;
    private boolean mardi = false;
    private boolean mercredi = false;
    private boolean jeudi = false;
    private boolean vendredi = false;
    private int prix = 3;

    public Cantine(String nom) {
        String[] jourDiviser = nom.split("/");
        for (String jour : jourDiviser) {
            if (jour.equals("lu")) {
                lundi = true;
            } else if (jour.equals("ma")) {
                mardi = true;
            } else if (jour.equals("me")) {
                mercredi = true;
            } else if (jour.equals("je")) {
                jeudi = true;
            } else if (jour.equals("ve")) {
                vendredi = true;
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        if (lundi) {
            result += "lu/";
        }
        if (mardi) {
            result += "ma/";
        }
        if (mercredi) {
            result += "me/";
        }
        if (jeudi) {
            result += "je/";
        }
        if (vendredi) {
            result += "ve/";
        }
        if (result.equals("")) {
            return "0";
        }
       return result;
    }

    public int getPrix() {
        int jour = 0;
        if (lundi) {
            jour ++;
        }
        if (mardi) {
            jour ++;
        }
        if (mercredi) {
            jour ++;
        }
        if (jeudi) {
            jour ++;
        }
        if (vendredi) {
            jour ++;
        }
        return prix * jour;
    }
}
