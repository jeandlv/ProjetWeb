<%-- 
    Document   : vueFacture
    Created on : 19 avr. 2018, 16:49:52
    Author     : LucBR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page Facture</title>
    </head>
    <body>
        <h1>Facture sur la periode ${facture.getPeriode().debutToString()} --> ${facture.getPeriode().finToString()}</h1> <br>
        ${facture.getPrixTotal()} <br>
        -${facture.getMontantEnleve()} <br>
        ----------------------------- <br>
        ${facture.getMontantFinal()}
    </body>
</html>
