<%-- 
    Document   : ajoutEnfant
    Created on : Apr 10, 2018, 3:22:41 PM
    Author     : boussanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajout Fiche Enfant</title>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    </head>
    <body>
        <h1>Ajouter les informations de l'enfant</h1>
      <div id="container_modif_parent" class="container">
           <div id="parent" class="row theme_classique">
                  <form method="get" action="controleurParent" accept-charset="UTF-8">
                    <div class="form-group">
                        Entrez un Nom: <input type="text" name="nom" required/>
                    </div>
                    <div class="form-group">
                        Entrez un Prénom: <input type="text" name="prenom" required/>
                    </div>
                    <div class="form-group">
                        Entrez le sexe de l'enfant:
                        <select name="sexe">
                            <option value="M" selected>Masculin</option>
                            <option value="F">Féminin</option>
                            <option value="A">Autres</option>
                        </select>
                    </div>
                    <div class="form-group">
                        Entrez une date de Naissance (format JJ/MM/AAAA): <input type="date" name="dateNaissance" required/>
                    </div>
                    <div class="form-group">
                        Entrez la classe de l'enfant:                       
                          <select name="classe">
                            <option value="PS" selected>Petite Section</option> 
                            <option value="MS">Moyenne Section</option> 
                            <option value="GS">Grande Section</option> 
                            <option value="CP">CP</option>
                            <option value="CE1">CE1</option>
                            <option value="CE2">CE2</option> 
                            <option value="CM1">CM1</option> 
                            <option value="CM2">CM2</option> 
                          </select>
                      </div>
                      <div class="form-group">
                      Entrez le regime de l'enfant
                          <select name="regimeChoisi" required>
                            <c:forEach items="${regimes}" var="regime">
                                <option value="${regime}">${regime}</option>
                            </c:forEach>   
                          </select>
                      </div>
                      <div class="form-group">
                      Mangera à la cantine :
                          <table border="1" class="table table-bordered">
                              <thead class="thead-light">
                                  <tr>
                                      <th>Lundi</th>
                                      <th>Mardi</th>
                                      <th>Mercredi</th>
                                      <th>Jeudi</th>
                                      <th>Vendredi</th>
                                  </tr>
                              </thead>
                              <tbody>
                                  <tr>
                                      <td><input type="checkbox" name="Lu" value="Lu"></td>
                                      <td><input type="checkbox" name="Ma" value="Ma"></td>
                                      <td><input type="checkbox" name="Me" value="Me"></td>
                                      <td><input type="checkbox" name="Je" value="Je"></td>
                                      <td><input type="checkbox" name="Ve" value="Ve"></td>
                                  </tr>
                              </tbody>
                          </table>
                      </div>
                      <div class="form-group">Informations supplémentaires : <br/>
                          <textarea type="text" rows="5" cols="50" name="divers" required> Rien à signaler</textarea>
                          </div>
                    <input type="submit" value="Ajouter enfant" />
                    <input type="hidden" name="action" value="enfantAjouter" />
                    <input type="hidden" name="loginParent" value="${loginParent}" />
           </div>
      </div>
    <div id="container_retour" class="container">
        <form>
            <input type="button" value="Retour" onclick="history.go(-1)">
        </form>
    </div>
    </body>
</html>
