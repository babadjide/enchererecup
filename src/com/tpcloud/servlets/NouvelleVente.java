package com.tpcloud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tpcloud.beans.Utilisateur;
import com.tpcloud.beans.Vente;
import com.tpcloud.forms.InscriptionForm;
import com.tpcloud.forms.NouvelleVenteForm;

public class NouvelleVente extends HttpServlet {
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
	public static final String URL_REDIRECTION = "espaceachatvente";
	public static final String ATT_FORM = "form";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher( "/WEB-INF/NouvelleVente.jsp" ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		NouvelleVenteForm form = new NouvelleVenteForm();
		Vente vente = form.creerVente(request);
		
		request.setAttribute(ATT_FORM, form);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/NouvelleVente.jsp" ).forward( request, response );
	}
}
