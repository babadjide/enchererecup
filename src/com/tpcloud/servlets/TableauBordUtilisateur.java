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
import com.tpcloud.forms.TableauDeBordUtilisateurForm;

public class TableauBordUtilisateur extends HttpServlet {
	public static final String CHAMP_ERREUR = "erreurs";
	public static final String LISTE_VENTES = "listeventes";
	public static final String TABLEAU_FORM = "tableau";
	public static final String ATT_USER = "utilisateur";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		TableauDeBordUtilisateurForm tableau = new TableauDeBordUtilisateurForm();
		HashMap<String, Vente> lesventes = null;
		try {
			lesventes = tableau.listeDesVentes(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		
		/*Récupération de l'utilisateur connecté*/
		Utilisateur utilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_USER);
		
		/*Ajout du tableau et de la liste à l'objet request*/
		request.setAttribute(LISTE_VENTES, lesventes);
		request.setAttribute(TABLEAU_FORM, tableau);
		request.setAttribute(ATT_USER, utilisateur);
		
		session.setAttribute(LISTE_VENTES, lesventes);
		session.setAttribute(TABLEAU_FORM, tableau);
		session.setAttribute(ATT_SESSION_USER, utilisateur);
            
            this.getServletContext().getRequestDispatcher("/WEB-INF/AchatVente.jsp").forward(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

	}
}
