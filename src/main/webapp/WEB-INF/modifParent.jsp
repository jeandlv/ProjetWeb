<%-- 
    Document   : modifParent
    Created on : Apr 10, 2018, 12:15:58 PM
    Author     : bernnico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modification Fiche Parent</title>
        <link rel="stylesheet" type="text/css" href="style.css" />
         <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    </head>
    <body>
        <h1>Modifier les informations des parents</h1>
        <div id="container_modif_parent" class="container">
           <div id="parent" class="row theme_classique">
               <div class="col">
                   <div class="row">
                       <h4>Informations modifiables</h4>
                   </div>
                 <div class="row">
                  <form method="post" action="controleurParent" accept-charset="UTF-8">
                    <div class="form-group">
                        Entrez un Nouveau Nom :<input type="text" name="nom" value="${ficheParent.getNom()}"/>
                    </div>
                    <div class="form-group">
                        Entrez un Nouveau Prénom : <input type="text" name="prenom" value="${ficheParent.getPrenom()}"/>
                    </div>
                      <!-- On fait un champ caché pour transmettre le login mais il n'est pas modifiable-->
                    <input type="hidden" name="login" value="${ficheParent.getLogin()}"/></li>
                      <!--  A VOIR SI ON LE LAISSE
                      <li> Entrez un Nouveau Mot de passe: <input type="password" name="motdepasse"/></li>-->
                    <div class="form-group">Entrez une Nouvelle Adresse : <input type="text" name="adresse" value="${ficheParent.getAdresse()}"/>
                    </div>
                    <input type="submit" value="Appliquer les modifications" />
                    <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
                    <input type="hidden" name="action" value="modifInfo" />
                  </form>
               </div>
           </div>
        </div>
      </div>
    <div id="container_retour" class="container">
        <form>
            <input type="button" value="Retour" onclick="history.go(-1)">
        </form>
    </div>
    </body>
</html>
