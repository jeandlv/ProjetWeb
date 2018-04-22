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
public class FicheAccompagnateur extends Fiche{
    private String mail;
    private String numeroTel; //format XX-XX-XX-XX-XX

    public FicheAccompagnateur(String mail, String numeroTel, String nom, String prenom) {
        super(nom, prenom);
        this.mail = mail;
        this.numeroTel = numeroTel;
    }
    
    
}
