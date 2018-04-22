<%-- 
    Document   : mairie
    Created on : Apr 3, 2018, 5:07:03 PM
    Author     : boussanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="modele.Periode" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mairie</title>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
  
    </head>
    <body>
        <h1>Bonjour ${login}, vous êtes bien connectés</h1>
        <div id="container" class="container">
            <form>
              <div class="form-group">
               <a href="controleurParent?action=logout"><input id="logout" type="button" value="LogOut"></a>
              </div>
            </form>
        </div>
        <h2>Activités</h2>
        <div id="container_activite" class="container">
            <div class="row theme_classique page_activité">
                <div class="col">
                    <div id = "activite" class="row">
                        <table border="1" class="table table-bordered table-hover">
                              <thead class="thead-light">
                                <tr>
                                  <th scope="col">Nom</th>
                                  <th scope="col">Jour</th>
                                  <th scope="col">Horaire</th>
                                  <th scope="col">Classe</th>
                                  <th scope="col">Prix</th>
                                  <th scope="col">Effectif</th>
                                  <th scope="col">Mail de l'accompagnateur1</th>
                                  <th scope="col">Mail de l'accompagnateur2</th>
                                </tr>
                              </thead>
                            <tbody>
                            <c:forEach items="${activites}" var="activite">
                                <tr>
                                    <td>${activite.getNom()}</td>
                                    <td>${activite.getCreneauxJour()}</td>
                                    <td>${activite.getCreneauxHeure()}</td>
                                    <td>${activite.getClasse()}</td>
                                    <td>${activite.getPrix()}</td>
                                    <td>${activite.getEffectif()}</td>
                                    <td>${activite.getAccompagnateur1()}</td>
                                    <td>${activite.getAccompagnateur2()}</td>
                                    <!-- Si periode en cours impossible d'afficher la colonne suppression -->
                                    <c:if test="${activite.getPeriode().estEnCours() == false}">
                                        <td><a href="controleurMairie?action=activiteSupprimer&actiNom=${activite.getNom()}&actiJour=${activite.getCreneauxJour()}&actiHeure=${activite.getCreneauxHeure()}">supprimer</a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div id = "activite" class="row">
                        <h3>Ajouter une activité</h3>
                    </div>
                    <div id = "activite" class="row">
                        <form method="get" action="controleurMairie" accept-charset="UTF-8">
                            <div class="form-group">
                            Nom de l'activité : <input type="text" name="nom" required/>
                            </div>
                            <div class="form-group">
                            Jour :
                                <select name="jour" required>
                                        <option value="lundi">lundi</option>
                                        <option value="mardi">mardi</option>
                                        <option value="mercredi">mercredi</option>
                                        <option value="jeudi">jeudi</option>
                                        <option value="vendredi">vendredi</option>
                                </select>
                            </div>
                            <div class="form-group">
                            Horaire :
                                <select name="horaire" required>
                                        <option value="10h">10h</option>
                                        <option value="11h">11h</option>
                                        <option value="12h">12h</option>
                                        <option value="13h">13h</option>
                                        <option value="14h">14h</option>
                                        <option value="15h">15h</option>
                                        <option value="16h">16h</option>
                                        <option value="17h">17h</option>
                                        <option value="18h">18h</option>
                                        <option value="19h">19h</option>
                                        <option value="20h">20h</option>
                                </select>
                            </div>
                            <div class="form-group">
                            Classe(s) concernées : 
                            <table border="1" class="table table-bordered table-hover">
                              <thead class="thead-light">
                                  <tr>
                                      <th>Petite Section</th>
                                      <th>Moyenne Section</th>
                                      <th>Grande Section</th>
                                      <th>CP</th>
                                      <th>CE1</th>
                                      <th>CE2</th>
                                      <th>CM1</th>
                                      <th>CM2</th>
                                  </tr>
                              </thead>
                              <tbody>
                                  <tr>
                                      <td><input type="checkbox" name="PS" value="PS"></td>
                                      <td><input type="checkbox" name="MS" value="MS"></td>
                                      <td><input type="checkbox" name="GS" value="GS"></td>
                                      <td><input type="checkbox" name="CP" value="CP"></td>
                                      <td><input type="checkbox" name="CE1" value="CE1"></td>
                                      <td><input type="checkbox" name="CE2" value="CE2"></td>
                                      <td><input type="checkbox" name="CM1" value="CM1"></td>
                                      <td><input type="checkbox" name="CM2" value="CM2"></td>
                                  </tr>
                              </tbody>
                            </table>
                            <div class="form-group">
                            Prix de l'activité : <input type='number' name="prix" min=0 required/>
                            </div>
                            <div class="form-group">
                            Effectif de l'activité : <input type='number' name="effectif" min=0 required/>
                            </div>
                            <div class="form-group">
                            Accompagnateur 1 :
                                <select name="mail1" required>
                                    <c:forEach items="${accompagnateurs}" var="accompagnateur">
                                        <option value="${accompagnateur}">${accompagnateur}</option>
                                    </c:forEach>   
                                </select>
                            </div>
                            <div class="form-group">
                            Accompagnateur 2 :
                                <select name="mail2" required>
                                    <c:forEach items="${accompagnateurs}" var="accompagnateur">
                                        <option value="${accompagnateur}">${accompagnateur}</option>
                                    </c:forEach>   
                                </select>
                            </div>
                            <c:if test="${SameAccompagnateur == 1}">
                                      <div style="color:red;">
                                          Saisissez des accompagnateurs différents
                                      </div>
                             </c:if>
                            <div class="form-group">
                                Periode de l'activité :
                                <select name="periode" required>
                                    <c:forEach items="${periodes}" var="periode">
                                        <c:if test="${periode.estEnCours() == false}">
                                            <option value="${periode.debutToString()}-->${periode.finToString()}">${periode.debutToString()} --> ${periode.finToString()}</option>
                                        </c:if>
                                    </c:forEach>   
                                </select>                                
                            </div>
                           
                            <input type="submit" value="Ajouter"/>
                            <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                            <input type="hidden" name="action" value="activiteAjouter" />
                        </form>
                    </div>
                </div>
            </div>
    </div>
        <!-- Afficher les regimes -->

        <div id="container_regime" class="container">
            <h2>Régime</h2>
            <div class="row theme_classique page_activité">
                <div class="col">
                    <table border="1" class="table table-bordered table-hover">
                        <thead class="thead-light">
                                <tr>
                                  <th scope="col">Type de régime</th>
                                </tr>
                              </thead>
                        <tbody>
                            <c:forEach items="${regimes}" var="regime">
                                <tr>
                                    <td>${regime}</td>
                                    <c:if test="${estEnCours == false}">
                                        <td><a href="controleurMairie?action=regimeSupprimer&regime=${regime}">supprimer</a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                 <div class="col">
                    <h3>Ajouter un régime</h3>
                    <form method="get" action="controleurMairie" accept-charset="UTF-8">
                      <div class="form-group">                        
                       Régime à ajouter : <input type="text" name="regime" required/>
                      </div>
                        <c:if test="${SameRegime == 1}">
                            <div style="color:red;">
                              Ce régime existe déja
                            </div>
                        </c:if>
                        <input type="submit" value="Ajouter" <c:if test="${estEnCours == true}">disabled</c:if>/>
                      <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                      <input type="hidden" name="action" value="regimeAjouter" />
                      </div>
                    </form>
                 </div>
            </div>
        </div>
        <div id="container_periode" class="container">
            <%! Periode periode = new Periode();%>
            <h2>Période - Jour : <%=periode.dateActuelleToString()%> </h2>
            <div class="row theme_classique page_activité">
                <div class="col">
                 <table border="1" class="table table-bordered table-hover">
                     <thead class="thead-light">
                                <tr>
                                  <th scope="col">Date de Début</th>
                                  <th scope="col">Date de Fin</th>
                                </tr>
                     </thead>
                     <tbody>
                        <c:forEach items="${periodes}" var="periode">
                            <tr>
                                <td>${periode.debutToString()}</td>
                                <td>${periode.finToString()}</td>
                                <c:if test="${periode.estEnCours() == true}">
                                    <td style="color: green;">Est en cours</td>
                                </c:if>
                                <c:if test="${periode.estEnCours() == false}">
                                    <td><a href="controleurMairie?action=periodeSupprimer&dateDebut=${periode.debutToString()}&dateFin=${periode.finToString()}">supprimer</a></td>
                                </c:if>
                            </tr>
                        </c:forEach> 

                     </tbody>
                 </table>
                        
                </div>
                <div class="col">
                    <h3>Ajouter une période</h3>
                      <form method="get" action="controleurMairie" accept-charset="UTF-8">
                        <div class="form-group">
                        Date début (format AAAA-MM-JJ) : <input type="date" name="dateDebut"/>
                        </div>
                        <div class="form-group">
                        Date fin (format AAAA-MM-JJ) : <input type="date" name="dateFin"/>
                        </div>
                        <input type="submit" value="Ajouter" />
                         <c:if test="${avantDateActuelle == true}">
                            <div style="color:red;">
                                Saisissez une période qui commence après la date d'aujourd'hui
                            </div>
                         </c:if>
                         <c:if test="${chevauchePeriode == true}">
                            <div style="color:red;">
                                Saisissez une période qui ne chevauche pas une autre période
                            </div>
                         </c:if>
                         <c:if test="${periodeIncorrecte == true}">
                            <div style="color:red;">
                                Saisissez une date de début avant la date de fin
                            </div>
                         </c:if>                        <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                        <input type="hidden" name="action" value="periodeAjouter" />
                      </form>
                </div>
            </div>
        </div>
        <div id="container_employe" class="container">
            <h2> Rajouter un employé de la mairie: </h2>
            <div class="row theme_classique page_activité">
                  <form method="post" action="controleurMairie" accept-charset="UTF-8">
                      <div class="form-group">
                           Créez un Login : <input type="text" name="login" required/>
                      </div>
                      <div class="form-group">
                          Créez un Mot de passe : <input type="password" name="password1" required/>
                      </div>
                       <div class="form-group">
                      Confirmez le Mot de passe : <input type="password" name="password2" required/>
                       </div>
                   <c:if test="${differentPassword == 1}">
                      <div style="color:red;">
                          Saisissez le même mot de passe
                      </div>
                   </c:if>
                   <c:if test="${loginUsed == 1}">
                      <div style="color:red;">
                          Ce login est déjà pris merci d'en choisir un autre !
                      </div>
                   </c:if>
                    <input type="submit" value="Création" />
                    <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                    <input type="hidden" name="action" value="creationCompteMairie" />
                  </form>
            </div>
        </div>
    </body>
</html>
