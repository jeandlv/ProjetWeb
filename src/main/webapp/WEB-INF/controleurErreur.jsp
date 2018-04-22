<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="UTF-8"/>
        <title>Erreur Contrôleur</title>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    </head>
    <body>
        <div id="container_erreur" class="container">
           <div id="erreur" class="row theme_classique">
                <h1 style="text-align: center">Erreur de Contrôleur</h1>
                <img src="https://media.giphy.com/media/3oriOeqBvDmQAcbp2U/giphy.gif" alt="Oups"/>
                <br/><br/>
                Paramètres invalides : action <span style="color: red">${param.action}</span>
           </div>
        </div>
    </body>
</html>
