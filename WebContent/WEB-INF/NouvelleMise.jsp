<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Nouvelle Mise</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
        <form method="post" action="<c:url value="/nouvellemise"><c:param name="idVente" value="${ sessionScope.PARAM_ID_VENTE }" /></c:url>">
        	<c:if test="${!empty sessionScope.sessionUtilisateur}">
   				<%-- Si l'utilisateur existe en session , alors on affiche son adresse email. --%>
   				<p class="succes">Connecté(e) avec: ${sessionScope.sessionUtilisateur.email}
   					<a href="<c:url value="/deconnexion"/>">Déconnexion</a>
   				</p>
			</c:if>
			
            <fieldset>
                <legend>NouvelleMise</legend>
                <p>Vous pouvez faire une mise via ce formulaire.</p>

                <label for="email">Montant de la mise <span class="requis">*</span></label>
                <input type="text" id="mise" name="mise" value="" size="20" maxlength="60" />
                <span class = "erreur">${form.erreurs['mise']}</span>
                <br />

                <input type="submit" value="Soumettre" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
    </body>
</html>