

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Erreur BD</title><link rel="stylesheet" type="text/css" href="style.css" />
        <link rel="stylesheet" type="text/css" href="style.css" />
        <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    </head>
    <body>
         <div id="container_erreur" class="container">
           <div id="erreur" class="row theme_classique">
               <div class="col">
                   <div class="row">
                        <h1 style="text-align: center">Erreur</h1>
                   </div>
                   <div class="row">
                        <p>Une erreur d’accès à la base de données vient de se produire.</p>
                   </div>
                   <div class="row">
                    <p>Message : ${erreurMessage}</pre>
                   </div>
               </div>
           </div>
         </div>
    </body>
    </html>
