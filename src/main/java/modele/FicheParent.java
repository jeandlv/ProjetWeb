/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.List;

/**
 *
 * @author bernnico
 */
public class FicheParent extends Fiche{
    private String adresse;
    private String login;
    private List<FicheEnfant> enfants;

    public FicheParent(String adresse, String login, List<FicheEnfant> enfants, String nom, String prenom) {
        super(nom, prenom);
        this.adresse = adresse;
        this.login = login;
        this.enfants = enfants;
    }

    public List<FicheEnfant> getEnfants() {
        return enfants;
    }

    public String getLogin() {
        return login;
    }

    public String getAdresse() {
        return adresse;
    }
  
}
