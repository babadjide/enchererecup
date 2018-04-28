package com.tpcloud.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tpcloud.beans.Utilisateur;
import com.tpcloud.beans.Vente;
import com.tpcloud.forms.InscriptionForm;
import com.tpcloud.forms.NouvelleMiseForm;

public class NouvelleMise extends HttpServlet {
	public static final String ATT_USER = "utilisateur";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
	public static final String ATT_FORM = "form";
	public static final String PARAM_ID_VENTE = "idVente";
	public static final String LISTE_VENTES = "listeventes";
	public static final String TABLEAU_FORM = "tableau";
	public static final String CHAMP_ERREUR = "erreurs";
	public static final String URL_REDIRECTION = "espaceachatvente";
	public static final String URL_REQUEST = "urlrequete";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		HttpSession session = request.getSession();
		
		/* Récupération du paramètre */
		String idvente = getValeurParametre( request, PARAM_ID_VENTE );
		
		/*Récupération de l'utilisateur connecté*/
		Utilisateur utilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_USER);
		request.setAttribute(ATT_USER, utilisateur);
		
		session.setAttribute(PARAM_ID_VENTE, idvente);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/NouvelleMise.jsp" ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{		
		HttpSession session = request.getSession();
		
		/* Récupération du paramètre */
		String idvente = getValeurParametre( request, PARAM_ID_VENTE );
		
		/*Récupération de l'utilisateur connecté*/
		Utilisateur utilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_USER);
		//request.setAttribute(ATT_USER, utilisateur);
		
		/*Récupération de la liste des vente*/
		HashMap<String, Vente> listeVente = (HashMap<String, Vente>)request.getAttribute(LISTE_VENTES);		
		
		/* Préparation de l'objet formulaire */
		NouvelleMiseForm form = new NouvelleMiseForm();
		
		/* Appel au traitement et à la validation de la requête, et récupération de la liste d'erreur en résultant */
		form.miser(request);
		
		/* Enregistrement de la liste des erreurs et de l'objet form dans la requete*/
		request.setAttribute(ATT_FORM, form);
		
		if(form.getErreurs().isEmpty()){
			/*Ajout de l'utilisateur, de la vente et de la liste des ventes à l'objet request*/
			request.setAttribute(ATT_USER, utilisateur);
			request.setAttribute(PARAM_ID_VENTE, idvente);
			request.setAttribute(LISTE_VENTES, listeVente);
			
    		/*Redirection vers le tableau de bord*/
    		//response.sendRedirect(URL_REDIRECTION);
    		this.getServletContext().getRequestDispatcher( "/WEB-INF/NouvelleMise.jsp" ).forward( request, response );
		}else{
			this.getServletContext().getRequestDispatcher( "/WEB-INF/NouvelleMise.jsp" ).forward( request, response );
		}
		
	}
	
	public String getValeurParametre(HttpServletRequest request, String param){
		String valeur = request.getParameter(param);
		if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
	}
}
