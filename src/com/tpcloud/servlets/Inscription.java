package com.tpcloud.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.collections.MappingChange.Map;
import com.tpcloud.beans.Utilisateur;
import com.tpcloud.forms.InscriptionForm;

public class Inscription extends HttpServlet {
	
	public static final String ATT_USER = "utilisateur";
	public static final String ATT_FORM = "form";
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		/* Préparation de l'objet formulaire */
		InscriptionForm form = new InscriptionForm();
		
		 /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
		Utilisateur utilisateur = new Utilisateur();
		utilisateur = form.inscrireUtilisateur(request);
		
		/* Stockage du formulaire et du bean dans l'objet request */
		request.setAttribute( ATT_FORM, form );
		request.setAttribute( ATT_USER, utilisateur );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );
	}
}
