<%-- 
    Document   : ajoutActivite
    Created on : Apr 11, 2018, 2:26:44 PM
    Author     : boussanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" type="text/css" href="style.css" />
        <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <title>Ajout d'une activité</title>
    </head>
    <body> 
        <div id="container_enfant" class="container">
           <div id="enfant" class="row theme_classique">
               <div class="col">
                   <div class="row">
                       <h1>Ajout d'une activité</h1>
                   </div>
                   <div class="row">
                        <form method="get" action="controleurParent" accept-charset="UTF-8">
                            <div class="form-group">
                              <select name="activiteChoisi" required>
                                  <c:choose>
                                      <c:when test="${fn:length(activites) > 0}">
                                          <c:forEach items="${activites}" var="activite">
                                              <option value="${activite}">${activite}</option>
                                          </c:forEach>
                                      </c:when>
                                      <c:otherwise>
                                          <option value="Impossible">Aucune activité ne peut être choisi</option>
                                      </c:otherwise>
                                  </c:choose>
                              </select>
                            </div>
                           <div class="form-group">
                              <c:choose>
                                  <c:when test="${fn:length(activites) > 0}">
                                      <input type="submit" value="Ajouter activité"/>
                                  </c:when>
                                  <c:otherwise>
                                      <input type="submit" value="Ajouter activité" disabled/>
                                  </c:otherwise>
                              </c:choose>
                           </div>
                          <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                          <input type="hidden" name="action" value="activiteAjouter" />
                          <input type="hidden" name="prenomEnfant" value="${prenomEnfant}" />
                          <input type="hidden" name="loginParent" value="${loginParent}" />
                        </form>
                   </div>
        <div id="container_retour" class="container">
            <form>
                <input type="button" value="Retour" onclick="history.go(-1)">
            </form>
        </div>
    </body>
</html>
