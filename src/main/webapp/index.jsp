<%-- 
    Document   : index
    Created on : Apr 10, 2018, 12:00:16 PM
    Author     : boussanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" type="text/css" href="style.css" />
  <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
  <title>Connexion</title>
</head>
<body>
  <div id="main_title">
  <h1>Bienvenue sur le portail des activités de la Mairie</h1>
  <h2>Connectez-vous ci-dessous</h2>
  </div>
  <div class="container">
        <div id = "index" class="row">
            <div id="log" class="col-sm">
                <h4>Je suis employé de la mairie : </h4><br/>
                  <c:if test="${erreurLoginMairie == 1}">
                      <div style="color:red;">
                          Mauvais login/mot de passe
                      </div>
                  </c:if>
                  <form method="post" action="controleurMairie" accept-charset="UTF-8">
                    <ul>
                      <li> Login : <br/><input type="text" name="login"/></li>
                      <li> Mot de passe : <br/><input type="password" name="password"/></li>
                    </ul>
                    <input type="submit" value="Login" />
                    <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                    <input type="hidden" name="action" value="connexion" />
                  </form>
            </div>
            <div id="log" class="col-sm">
                <h4> Je suis un parent d'élève : </h4> <br/>
                  <c:if test="${erreurLoginParent == 1}">
                      <div style="color:red;">
                          Mauvais login/mot de passe
                      </div>
                  </c:if>
                  <form method="post" action="controleurParent" accept-charset="UTF-8">
                    <ul>
                      <li> Login : <br/><input type="text" name="login"/></li>
                      <li> Mot de passe : <br/><input type="password" name="password"/></li>
                    </ul>
                    <input type="submit" value="Login" />
                    <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                    <input type="hidden" name="action" value="connexion" />
                  </form>
            </div>
            <div id="log" class="col-sm"> 
                    <h4>Créer un compte parent : </h4> <br/>
                  <form method="post" action="controleurParent" accept-charset="UTF-8">
                    <ul>
                      <li> Créez un Login : <br/><input type="text" name="login"/></li>
                      <li> Créez un Mot de passe : <br/><input type="password" name="password1"/></li>
                      <li> Confirmez le Mot de passe : <br/><input type="password" name="password2"/></li>
                    </ul>
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
                    <input type="hidden" name="action" value="creationCompteParent" />
                  </form>
              </div>
        </div>
  </div>
</body>
</html>
