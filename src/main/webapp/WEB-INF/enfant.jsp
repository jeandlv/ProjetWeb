<%-- 
    Document   : enfant
    Created on : Apr 10, 2018, 7:39:06 PM
    Author     : boussanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css" />
        <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

        <title>Enfant</title>
    </head>
    <body>
        <div id="container_enfant" class="container">
           <div id="enfant" class="row theme_classique">
               <div class="col">
                   <div class="row">
                       <h1>Informations de l'enfant - ${ficheEnfant.getPrenom()} ${ficheEnfant.getNom()}</h1>             
                   </div>
                   <div class="row">
                       <ul>
                            <li><b>Nom : </b>${ficheEnfant.getNom()}</li>
                            <li><b>Prenom : </b>${ficheEnfant.getPrenom()}</li>
                            <li><b>Sexe : </b>${ficheEnfant.getSexe()}</li>
                            <li><b>Classe : </b>${ficheEnfant.getClasse()}</li>
                            <li><b>Regime : </b>${ficheEnfant.getRegime()}</li>
                            <li><b>Cantine : </b>${ficheEnfant.getCantine().toString()}</li>
                            <li><b>Date de Naissance : </b>${ficheEnfant.getDateNaissance()}</li>
                            <li><b>Divers : </b>${ficheEnfant.getDivers()}</li>
                       </ul>
                   </div>
                   <div class="row">
                        <table class="table table-bordered table-hover">
                             <thead class="thead-light">
                                <tr>
                                  <th scope="col">Activité demandée pour la prochaine période</th>
                                </tr>
                             </thead>
                            <c:forEach items="${activites}" var="activite">
                                <c:if test="${activite.getPeriode().estEnCours() == false}">
                                    <tr>
                                        <td>${activite}</td>
                                        <td><a href="controleurParent?action=activiteSupprimer&prenomEnfant=${ficheEnfant.getPrenom().trim()}&loginParent=${loginParent.trim()}&nomActivite=${activite.getNom()}&creneauxJour=${activite.getCreneauxJour()}&creneauxHeure=${activite.getCreneauxHeure()}"><input type="button"value="supprimer"></a></td>
                                    </tr>                                    
                                </c:if>
                            </c:forEach>                   
                        </table>
                    </div>
                    <div class="row">
                        <table class="table table-bordered table-hover">
                             <thead class="thead-light">
                                <tr>
                                  <th scope="col">Activité reservée pour la période en cours</th>
                                  <th scope="col">Annulation pour la prochaine séance 48h en avance</th>
                                </tr>
                             </thead>
                            <c:forEach items="${activites}" var="activite">
                                <c:if test="${activite.getPeriode().estEnCours() == true}">
                                    <tr>
                                        <td>${activite}</td>
                                        <td><a href="controleurParent?action=activiteAbsent&prenomEnfant=${ficheEnfant.getPrenom().trim()}&loginParent=${loginParent.trim()}&nomActivite=${activite.getNom()}&creneauxJour=${activite.getCreneauxJour()}&creneauxHeure=${activite.getCreneauxHeure()}"><input type="button"value="Ne peux être présent"></a></td>
                                    </tr>     
                                     <c:if test="${delai48h == true}">
                                              <div style="color:red;">
                                                  Il est trop tard pour annuler cette séance 
                                              </div>
                                     </c:if>
                                     <c:if test="${annule == true}">
                                              <div style="color:red;">
                                                  La séance a été annuler correctement 
                                              </div>
                                     </c:if>
                                     <c:if test="${dejaAnnule == true}">
                                              <div style="color:red;">
                                                  Vous avez déjà annuler cette séance 
                                              </div>
                                     </c:if>                                    
                                </c:if>
                            </c:forEach>                   
                        </table>
                    </div>
                    <div class="row">
                        <form method="get" action="controleurParent">
                            <div class="form-group">
                                <input type="submit" value="Ajouter activité" />
                            </div>
                            <input type="hidden" name="action" value="ajoutActivite" />
                            <input type="hidden" name="nom" value="${ficheEnfant.getNom()}" />
                            <input type="hidden" name="prenom" value="${ficheEnfant.getPrenom()}" />
                            <input type="hidden" name="loginParent" value="${loginParent}" />
                        </form>
                    </div>
                        <div class="row">
                            <table class="table table-bordered table-hover">
                                <thead class="thead-light">
                                    <tr>
                                        <th scope="col">Garderie réservé pour la période en cours</th>
                                        <th scope="col">Annulation de la garderie</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${garderies}" var="garderie">
                                    <c:if test="true">
                                        <tr>
                                            <td>${garderie.getCreneauxJour()} à ${garderie.getCreneauxHeure()}</td>
                                            <td><a href="controleurParent?action=garderieSupprimer&prenomEnfant=${ficheEnfant.getPrenom().trim()}&loginParent=${loginParent.trim()}&creneauxJour=${garderie.getCreneauxJour()}&creneauxHeure=${garderie.getCreneauxHeure()}"><input type="button"value="supprimer"></a></td>
                                        </tr>                                    
                                    </c:if>
                                </c:forEach>         
                            </table>
                        </div>
                    <div class="row">
                        <form method="get" action="controleurParent">
                            <table border="1" class="table table-bordered">
                                <thead class="thead-light">
                                    <tr>
                                        <th>Lundi</th>
                                        <th>Mardi</th>
                                        <th>Jeudi</th>
                                        <th>Vendredi</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>7h <input type="checkbox" name="Lu7" value="Lundi à 7h"></td>
                                        <td>7h <input type="checkbox" name="Ma7" value="Mardi à 7h"></td>
                                        <td>7h <input type="checkbox" name="Je7" value="Jeudi à 7h"></td>
                                        <td>7h <input type="checkbox" name="Ve7" value="Vendredi à 7h"></td>
                                    </tr>
                                    <tr>
                                        <td>15h <input type="checkbox" name="Lu5" value="Lundi à 15h45"></td>
                                        <td>15h <input type="checkbox" name="Ma5" value="Mardi à 15h45"></td>
                                        <td>15h <input type="checkbox" name="Je5" value="Jeudi à 15h45"></td>
                                        <td>15h <input type="checkbox" name="Ve5" value="Vendredi à 15h45"></td>
                                    </tr>
                                    <tr>
                                        <td>16h <input type="checkbox" name="Lu6" value="Lundi à 16h30"></td>
                                        <td>16h <input type="checkbox" name="Ma6" value="Mardi à 16h30"></td>
                                        <td>16h <input type="checkbox" name="Je6" value="Jeudi à 16h30"></td>
                                        <td>16h <input type="checkbox" name="Ve6" value="Vendredi à 16h30"></td>
                                    </tr>
                                    <tr>
                                        <td>17h <input type="checkbox" name="Lu17" value="Lundi à 17h15"></td>
                                        <td>17h <input type="checkbox" name="Ma17" value="Mardi à 17h15"></td>
                                        <td>17h <input type="checkbox" name="Je17" value="Jeudi à 17h15"></td>
                                        <td>17h <input type="checkbox" name="Ve17" value="Vendredi à 17h15"></td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="form-group">
                                <input type="submit" value="Ajouter garderie" />
                            </div>
                            <input type="hidden" name="action" value="ajoutGarderie" />
                            <input type="hidden" name="prenomEnfant" value="${ficheEnfant.getPrenom()}" />
                            <input type="hidden" name="loginParent" value="${loginParent}" />
                        </form>
                    </div>
               </div>
           </div>
        </div>
        <div class="container">    
            <div class="row">
                <form>
                    <a  href="controleurParent?action=retourParent&loginParent=${loginParent}"><input id="retour" type="button" value="retour"></a>
                </form>
            </div>    
        </div>
    </body>
</html>