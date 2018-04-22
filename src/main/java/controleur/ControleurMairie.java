/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import dao.AbsenceDAO;
import dao.ActiviteDAO;
import dao.DAOException;
import dao.PeriodeDAO;
import dao.EmployeDAO;
import dao.RegimeDAO;
import dao.AccompagnateurDAO;
import dao.FactureDAO;
import dao.ParentDAO;
import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import modele.Activite;
import modele.FicheEnfant;
import modele.FicheParent;
import modele.Garderie;
import modele.Periode;

/**
 * Le contrôleur de l'application.
 */
@WebServlet(name = "ControleurMairie", urlPatterns = {"/controleurMairie"})
public class ControleurMairie extends HttpServlet {

    @Resource(name = "jdbc/bibliography")
    private DataSource ds;

    /**
     * Permet de gérer le cas si les paramètres donnés ne sont pas correctes
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void invalidParameters(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/controleurErreur.jsp").forward(request, response);        
    }

    /**
     * Permet de gérer le cas où il y a eu une erreur avec la base de données
     * @param request
     * @param response
     * @param e
     * @throws ServletException
     * @throws IOException 
     */
    private void erreurBD(HttpServletRequest request,
                HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        e.printStackTrace(); // permet d’avoir le détail de l’erreur dans catalina.out
        request.setAttribute("erreurMessage", e.getMessage());
        request.getRequestDispatcher("/WEB-INF/bdErreur.jsp").forward(request, response);
    }
  
    /**
     * Actions possibles en GET :
     *  Ajouter ou supprimer un regime
     *  Ajouter ou supprimer une activité
     *  Ajouter ou supprimer une période
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        RegimeDAO regimeDAO = new RegimeDAO(ds);
        ActiviteDAO activiteDAO = new ActiviteDAO(ds);
        AccompagnateurDAO accompagnateurDAO = new AccompagnateurDAO(ds);
        PeriodeDAO periodeDAO = new PeriodeDAO(ds);
        try {
            if (action.equals("regimeSupprimer")) {
                regimeDAO.supprimerRegime(request.getParameter("regime"));
            } else if (action.equals("regimeAjouter")) {
                actionAjouterRegime(request, regimeDAO);
            } else if (action.equals("activiteAjouter")) {
                actionAjouterActivite(request, activiteDAO);
            } else if (action.equals("activiteSupprimer")) {
                activiteDAO.supprimerActivite(request.getParameter("actiNom"), 
                        request.getParameter("actiJour"), request.getParameter("actiHeure"));
            } else if (action.equals("periodeSupprimer")) {
                periodeDAO.supprimerPeriode(request.getParameter("dateDebut"), request.getParameter("dateFin"));
            } else if (action.equals("periodeAjouter")) {
                actionAjouterPeriode(request, periodeDAO);
            } else if (action.equals("logout")) {
                request.logout();
                response.sendRedirect("index.jsp");
            }
            actualiserPage(request, response, regimeDAO, activiteDAO, accompagnateurDAO, periodeDAO);
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    public void testMiseAJourBDD() {
        PeriodeDAO periodeDAO = new PeriodeDAO(ds);
        ActiviteDAO activiteDAO = new ActiviteDAO(ds);
        AbsenceDAO absenceDAO = new AbsenceDAO(ds);
        List<Periode> periodes = periodeDAO.getPeriodes();
        for (Periode periode : periodes) {
            if (periode.estFini()) {
                // On calcule toutes les factures
                calculeFacture(periode);
                
                // On supprime toutes les activites de cette periode
                // On supprime toutes les reservations de cette periode
                activiteDAO.finPeriode(periode);

                // On supprime la periode
                periodeDAO.supprimerPeriode(periode.debutToString(), periode.finToString());
                
                // On supprime toutes les absences
                absenceDAO.finPeriode();
            } else if (periode.estEnCours()) {
                List<Activite> activites = activiteDAO.getListeActivite(periode);
                for (Activite activite : activites) {
                    int effectifMax = activite.getEffectif();
                    activiteDAO.miseAJour(effectifMax, activite.getNom(),activite.getCreneauxJour(), activite.getCreneauxHeure());
                }
            }
            
        }
    }
    
    public void calculeFacture(Periode periode) {
        ParentDAO parentDAO = new ParentDAO(ds);
        FactureDAO factureDAO = new FactureDAO(ds);
        ActiviteDAO activiteDAO = new ActiviteDAO(ds);
        AbsenceDAO absenceDAO = new AbsenceDAO(ds);
        List<String> listeParent = parentDAO.getParents();
        for (String loginParent : listeParent) {
            FicheParent ficheParent = parentDAO.getFicheParent(loginParent);
            int prixTotal = 0;
            int montantEnlever = 0;
            for (FicheEnfant ficheEnfant : ficheParent.getEnfants()) {
                List<Activite> activites = activiteDAO.getReserver(ficheEnfant, loginParent, periode);
                for (Activite activite : activites) {
                    prixTotal += activite.getPrix();
                    montantEnlever += activite.getPrixIndiv() * absenceDAO.getAbsences(activite.getNom(),
                            activite.getCreneauxJour(), activite.getCreneauxHeure(),
                            loginParent, ficheEnfant.getPrenom());
                }
                List<Garderie> garderies = activiteDAO.getGarderie(ficheEnfant.getPrenom(), loginParent, periode);
                for (Garderie garderie : garderies) {
                    prixTotal += garderie.getPrix();
                }
                prixTotal += ficheEnfant.getCantine().getPrix();
            }
            int montantFinal = prixTotal - montantEnlever;
            factureDAO.ajoutFacture(loginParent, periode.debutToString(), periode.finToString(), prixTotal, montantEnlever, montantFinal);
        }        
    }
    
    /**
     * Permet l'ajout de la période à la base de données tout en vérifiant que
     * les données sont correctes
     * @param request
     * @param periodeDAO 
     */
    public void actionAjouterPeriode(HttpServletRequest request, PeriodeDAO periodeDAO) {
        // Verifier que la date de début est avant la date de fin 
        // Verifier que la periode n'existe pas deja
        Periode periodeAjouter = new Periode(request.getParameter("dateDebut"), request.getParameter("dateFin"));
        List<Periode> periodes = periodeDAO.getPeriodes();
        if (periodeAjouter.avantDateActuelle()) {
            request.setAttribute("avantDateActuelle", true);
        } else if (periodeAjouter.chevauchementList(periodes)) {
            request.setAttribute("chevauchePeriode", true);
        } else if (periodeAjouter.periodeIncorrecte()) {
            request.setAttribute("periodeIncorrecte", true);
        } else {
            periodeDAO.ajouterPeriode(periodeAjouter.debutToString(), periodeAjouter.finToString());
        }
    }
    
    /**
     * Permet l'ajout d'une activité à la base de données
     * @param request
     * @param activiteDAO 
     */
    public void actionAjouterActivite(HttpServletRequest request, ActiviteDAO activiteDAO) {
        if (request.getParameter("mail1").equals(request.getParameter("mail2"))) {
            request.setAttribute("SameAccompagnateur", "1");
        }
        else if (activiteDAO.existeDeja(request.getParameter("nom"), request.getParameter("jour"), request.getParameter("horaire"))) {
            request.setAttribute("SameActivity", "1");
        } else  {
            String classes = parseClass(request);
            String periode = request.getParameter("periode");
            activiteDAO.ajouterActivite(request.getParameter("nom"), 
                    request.getParameter("jour"), request.getParameter("horaire"),
                    classes, Integer.parseInt(request.getParameter("prix")),
                    Integer.parseInt(request.getParameter("effectif")), request.getParameter("mail1"),
                    request.getParameter("mail2"), periode.split("-->")[0], periode.split("-->")[1]);
        }        
    }
    
    /**
     * Permet l'ajout d'un régime à la base données
     * @param request
     * @param regimeDAO 
     */
    public void actionAjouterRegime(HttpServletRequest request, RegimeDAO regimeDAO) {
        if (regimeDAO.existeDeja(request.getParameter("regime"))) {
            request.setAttribute("SameRegime", "1");
        } else {
            regimeDAO.ajouterRegime(request.getParameter("regime"));
        }
    }
    
    /**
     * Actions possibles en POST : Connexion à son espace personelle mairie,
     *  Création d'un compte mairie
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        RegimeDAO regimeDAO = new RegimeDAO(ds);
        ActiviteDAO activiteDAO = new ActiviteDAO(ds);
        AccompagnateurDAO accompagnateurDAO = new AccompagnateurDAO(ds);
        PeriodeDAO periodeDAO = new PeriodeDAO(ds);
        EmployeDAO employeDAO = new EmployeDAO(ds);
        try {
            if (action == null) {
                invalidParameters(request, response);
                return;
            } else if (action.equals("connexion")) {
                testMiseAJourBDD();
                actionConnexion(request, response, employeDAO);
            }else if (action.equals("creationCompteMairie")) {
                actionCreationMairie(request, response, employeDAO);
            }
            actualiserPage(request, response, regimeDAO, activiteDAO, accompagnateurDAO, periodeDAO);
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    
    /**
     * Verifie que les données rentrés correspondent à un compte mairie
     * puis affiche l'espace personnel si le login, mot de passe sont bons
     * @param request
     * @param response
     * @param employeDAO
     * @throws IOException
     * @throws ServletException 
     */
    public void actionConnexion(HttpServletRequest request, HttpServletResponse response,
            EmployeDAO employeDAO) throws IOException, ServletException{
        String login = request.getParameter("login");
        String mdp = request.getParameter("password");
        if (employeDAO.verify(login, mdp)) {
            request.setAttribute("login",login);
        } else {
            request.setAttribute("erreurLoginMairie", "1");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
    
    /**
     * Crée un compte mairie en plus et le garde en mémoire dans la base de données
     * @param request
     * @param response
     * @param employeDAO
     * @throws IOException
     * @throws ServletException 
     */
    public void actionCreationMairie(HttpServletRequest request, HttpServletResponse response,
            EmployeDAO employeDAO) throws IOException, ServletException{
        if(!request.getParameter("password1").equals(request.getParameter("password2"))){
            request.setAttribute("differentPassword", "1");
            request.getRequestDispatcher("/WEB-INF/mairie.jsp").forward(request, response);
        }
        else if(employeDAO.verifyLogin(request.getParameter("login"))){
            request.setAttribute("loginUsed", "1");
            request.getRequestDispatcher("/WEB-INF/mairie.jsp").forward(request, response);
        }
        employeDAO.creation(request.getParameter("login"), request.getParameter("password1"));
    }
    
    /**
     * Permet de récuperer toutes les classes choisies par l'utilisateur 
     * @param request
     * @return Un string avec les classes autorisés
     */
    public String parseClass(HttpServletRequest request) {
        String classes = "";
        if (request.getParameter("PS") != null) {
            classes += "PS/";
        }
        if (request.getParameter("MS") != null) {
            classes += "MS/";
        }
        if (request.getParameter("GS") != null) {
            classes += "GS/";
        }
        if (request.getParameter("CP") != null) {
            classes += "CP/";
        }
        if (request.getParameter("CE1") != null) {
            classes += "CE1/";
        }
        if (request.getParameter("CE2") != null) {
            classes += "CE2/";
        }
        if (request.getParameter("CM1") != null) {
            classes += "CM1/";
        }
        if (request.getParameter("CM2") != null) {
            classes += "CM2/";
        }
        if (classes.equals("")) {
            classes = "0";
        }
        else {
            classes = classes.substring(0, classes.length()-1);
        }
        return classes;
    }
    
    /**
     * Permet d'actualiser la page avec toutes les informations mises dans les paramètres
     * @param request
     * @param response
     * @param regimeDAO
     * @param activiteDAO
     * @param accompagnateurDAO
     * @param periodeDAO
     * @throws IOException
     * @throws ServletException 
     */
    public void actualiserPage(HttpServletRequest request,
            HttpServletResponse response, RegimeDAO regimeDAO, ActiviteDAO activiteDAO, 
            AccompagnateurDAO accompagnateurDAO, PeriodeDAO periodeDAO)
            throws IOException, ServletException {
        List<String> regimes = regimeDAO.getListeRegime();
        List<Activite> activites = activiteDAO.getListeActivite();
        List<String> accompagnateurs = accompagnateurDAO.getListEmail();
        List<Periode> periodes = periodeDAO.getPeriodes();
        request.setAttribute("estEnCours", periodeEncours(periodes));
        request.setAttribute("regimes", regimes);
        request.setAttribute("activites", activites);
        request.setAttribute("accompagnateurs", accompagnateurs);     
        request.setAttribute("periodes", periodes);
        request.getRequestDispatcher("/WEB-INF/mairie.jsp").forward(request, response);
    }
    
    public boolean periodeEncours(List<Periode> periodes) {
        for (Periode periode : periodes) {
            if (periode.estEnCours()) {
                return true;
            }
        }
        return false;
    }

}

