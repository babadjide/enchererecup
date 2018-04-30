<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>.::Acceuil Enchere::.</title>
	<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<body>
        <form method="post" action="<c:url value="/accueil" />">
            <fieldset>
                <legend>Connexion</legend>
                <p>Vous pouvez vous connecter via ce formulaire</p>

                <label for="email">Adresse email</label>
                <input type="email" id="email" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['email']}</span>
                <br />

                <label for="motdepasse">Mot de passe</label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>
                <br />

                <input type="submit" value="Connexion" class="sansLabel" />
                <br />
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                 
                <c:if test="${!empty sessionScope.sessionUtilisateur}">
                	<%-- Si l'utilisateur existe en session , alors on affiche son adresse email. --%>
                	<p class="succes">Vous êtes connecté(e) avec l'adresse: ${sessionScope.sessionUtilisateur.email}</p>
                </c:if>
                
                <legend>Nouveau utilisateur?</legend>
                <p><a href="<c:url value="/inscription"/>">Créer un compte</a></p>
                <!-- <input type="submit" value="Inscription" class="sansLabel" /> -->
                <br />
                

            </fieldset>
        </form>
</body>
</html>