<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>.::Espace Achat Vente::.</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
		<c:if test="${!empty sessionScope.sessionUtilisateur}">
   			<%-- Si l'utilisateur existe en session , alors on affiche son adresse email. --%>
   			<p class="succes">Connecté(e) avec: ${sessionScope.sessionUtilisateur.email}
   				<a href="<c:url value="/deconnexion"/>">Déconnexion</a>
   			</p>
		</c:if>

		<c:import url="/inc/CreationArticle.jsp" />	
	    <div id="corps">
        <c:choose>
            <%-- Si aucune commande n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty sessionScope.listeventes }">
                <p class="erreur">Aucun article en vente.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
			<c:otherwise>
            <table>
                <tr>
                    <th>idVente</th>
                    <th>   Statut   </th>
                    <th>Date de mise en vente</th>
                    <th>     Article     </th>
                    <th>     Description     </th>
                    <th>     Mise actuelle     </th>
                    <th class="action">Faire une mise</th>                    
                </tr>
                <%-- Parcours de la Map des commandes en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.listeventes }" var="mapVentes" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Vente, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ mapVentes.value.idVente }"/></td>
                    <td><c:out value="${ mapVentes.value.statut }"/></td>
                    <td><c:out value="${ mapVentes.value.debut }"/></td>
                    <td><c:out value="${ mapVentes.value.article.nom }"/></td>
                    <td><c:out value="${ mapVentes.value.article.description }"/></td>
                    <td><c:out value="${ mapVentes.value.miseSup }"/></td>
                    <%-- Lien vers la servlet de suppression, avec passage de la date de la commande - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                    	<a href="<c:url value="/nouvellemise"><c:param name="idVente" value="${ mapVentes.key }" /></c:url>">
                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Miser" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
</body>
</html>