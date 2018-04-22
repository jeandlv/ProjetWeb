<%-- 
    Document   : parent
    Created on : Apr 5, 2018, 10:35:39 AM
    Author     : bernnico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
         <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Parent</title>
    </head>
    <body>
        <h1>Vous êtes bien connectés sur la fiche de ${parent.getPrenom()} ${parent.getNom()}</h1>
        <div class="container">    
            <div class="row">
                <form>
                   <a  href="controleurParent?action=logout"><input id="logout" type="button" value="LogOut"></a>
                </form>
            </div>    
        </div>
        <div id="container_parent" class="container">
           <div id="parent" class="row theme_classique">
               <div class="col">
                  <c:if test="${modifOK == 1}">
                      <div style="color:green;">
                          Changement pris en compte
                      </div>
                  </c:if>
               <div class="row">
                   <h4>Modifier les informations parents</h4>
               </div>
               <div class="row">
                  <form method="post" action="controleurParent" accept-charset="UTF-8">
                    <input type="submit" value="Modifier" />
                    <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                    <input type="hidden" name="action" value="modifParent" />
                    <input type="hidden" name="currentLogin" value="${parent.getLogin()}" />
                  </form>
                </div>
               </div>
           </div>
           <div id="enfant" class="row theme_classique">
                <div class="col">
                    <div class="row">
                        Cliquez sur le nom de l'enfant pour accédez à sa fiche et aux 
                        choix des activités
                    </div>
                    <div class="row">
                        <table>
                            <c:forEach items="${parent.getEnfants()}" var="enfant">
                                <tr>
                                    <td><a href="controleurParent?action=enfantInfo&enfant=${enfant.getPrenom()}&loginParent=${parent.getLogin()}"><input type="button" value="${enfant.getPrenom()}"></a></td>
                                    <c:if test="${estEnCours == false}">
                                        <td><a href="controleurParent?action=enfantSupprimer&enfant=${enfant.getPrenom()}&loginParent=${parent.getLogin()}"><input type="button"value="supprimer"</a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>                   
                        </table>
                    </div>
                </div>
                <div id="ajout_enfant" class ="col" theme_classique">
                    <div class="row">
                        <h4>Ajouter un gosse</h4>
                    </div>
                    <div class="row">
                        <a href="controleurParent?action=ajoutEnfant&loginParent=${parent.getLogin()}"><input type="button" value="Ajouter un enfant"></a>
                    </div>
                </div>  
            </div>
            <div id="facture" class="row theme_classique">    
                <h4>Historique des factures<h4> <br>
                <!-- Afficher l'historique des factures + bouton
                        cliquable pour voir une certaine facture-->
                    <div class="row">
                        <table>
                            <c:forEach items="${factures}" var="facture">
                                <tr>
                                    <td>${facture.getPeriode().debutToString()} --> ${facture.getPeriode().finToString()}</td>
                                    <td><a href="controleurParent?action=voirFacture&loginParent=${parent.getLogin()}&factureDateDebut=${facture.getPeriode().debutToString()}&factureDateFin=${facture.getPeriode().finToString()}" target="_blank"><input type="button"value="Voir facture"</a></td>
                                </tr>
                            </c:forEach>                   
                        </table>
                    </div>
            </div>
        </div>
    </body>

</html>
