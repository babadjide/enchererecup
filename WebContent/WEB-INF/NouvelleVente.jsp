<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>vendreArticle</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
        <form method="post" action="nouvellevente">
            <c:if test="${!empty sessionScope.sessionUtilisateur}">
                <%-- Si l'utilisateur existe en session , alors on affiche son adresse email. --%>
                <p class="succes">Connecté(e) avec: ${sessionScope.sessionUtilisateur.email}
                	<a href="<c:url value="/deconnexion"/>">Déconnexion</a>
                </p>
            </c:if>
            
            <fieldset>
                <legend>NouvelleVente</legend>
                <p>Vous pouvez démarrer une nouvelle vente via ce formulaire.</p>
                
                <label for="nom">Nom Article <span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" value="<c:out value="${article.nom}"/>" size="20" maxlength="20" />
                <span class = "erreur">${form.erreurs['nom']}</span>
                <br />

                <label for="description">Description <span class="requis">*</span></label>
                <input type="text" id="description" name="description" value="<c:out value="${article.description}"/>" size="20" maxlength="100" />
                <span class = "erreur">${form.erreurs['description']}</span>
                <br />

                <input type="submit" value="Vendre" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
    </body>
</html>