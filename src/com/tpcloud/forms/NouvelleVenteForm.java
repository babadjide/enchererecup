package com.tpcloud.forms;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.tpcloud.beans.Article;
import com.tpcloud.beans.Utilisateur;
import com.tpcloud.beans.Vente;
import com.tpcloud.database.DbConnexion;

public final class NouvelleVenteForm {
	
	/*Création d'une instance de connexion*/
	DbConnexion nouvelleConnexion = new DbConnexion();
	
	private static final String ATT_SESSION_USER = "sessionUtilisateur";
	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_DESCRIPTION = "description";
	private static final String CHAMP_INSERTION = "insertion";
	private static final String RESULTAT_INSERTION_ARTICLE = "erreur_article";
	private static final String RESULTAT_INSERTION_VENTE = "erreur_vente";
	private String resultat = "";
	Utilisateur utilisateur;
	private HashMap<String, String> erreurs = new HashMap<String, String>();
	java.sql.Connection connexion = null;
		
	Article article = new Article();
    public String getResultat(){
    	return this.resultat;
    }
    
    public HashMap<String, String> getErreurs(){
    	return this.erreurs;
    }
	
	
	public Vente creerVente(HttpServletRequest request){
		HttpSession session = request.getSession();
		Vente vente = new Vente();
		
		String nomArticle = getValeurChamp(request, CHAMP_NOM);
		try{
			validationNom(nomArticle);
		}catch(Exception e){
			setErreur(CHAMP_NOM, e.getMessage());
		}
		article.setNom(nomArticle);
		
		String description = getValeurChamp(request, CHAMP_DESCRIPTION);
		try{
			validationDescription(description);
		}catch(Exception e){
			setErreur(CHAMP_DESCRIPTION, e.getMessage());
		}
		article.setDescription(description);
		article.setStatut("Disponible");
		
		utilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_USER);
		article.setUtilisateur(utilisateur);
		
		if(erreurs.isEmpty()){
			resultat = engistrerArticle(article);
			if(resultat.isEmpty()){
				article.setIdArticle(getIdArticle());
				vente.setStatut("en cours");				
				resultat = enregistrerVente(article, vente);
				if(resultat.isEmpty()){
					resultat = "Vente créée avec succès";
				}else{
					setErreur(RESULTAT_INSERTION_VENTE, resultat);
				}
			}else{
				setErreur(RESULTAT_INSERTION_ARTICLE, resultat);
			}
		}else{
			resultat = "Echec de création de la vente";
		}
		return vente;
	}
	
	
	private void validationDescription(String description) throws Exception{
	    if ( description == null) {
	        throw new Exception( "Entrez une description pour le produit" );
	    }else{
	    	if(description.trim().length() < 3 ){
	    		throw new Exception( "Plus d'informations à propos du produit" );
	    	}
	    }
	}
	
	private void validationNom(String nom) throws Exception{
	    if ( nom == null ) {
	        throw new Exception( "Saisissez le nom du produit" );
	    }
	}
	
	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur( String champ, String message ) {
	    erreurs.put( champ, message );
	}
	
	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	    String valeur = request.getParameter( nomChamp );
	    if ( valeur == null || valeur.trim().length() == 0 ) {
	        return null;
	    } else {
	        return valeur.trim();
	    }
	}
	
	/*Création d'un article en base*/
	public String engistrerArticle(Article article){
		String RESULTAT_INSERTION_ARTICLE = "";
		try{
			/* Création de l'objet gérant les requêtes */						
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/bt6gmrwbm");
			connexion = ds.getConnection();
			
			Statement statement = connexion.createStatement();
			statement.executeUpdate("INSERT INTO Article (nom, description, statut, date_ajout, idUtilisateur) VALUES ('"+article.getNom()+"','"+article.getDescription()+"','"+article.getStatut()+"', NOW(), "+utilisateur.getId()+")");
		}catch(Exception e){
			//setErreur("RESULTAT_INSERTION_ARTICLE", e.getMessage());
			RESULTAT_INSERTION_ARTICLE = "insertion article: "+e.getMessage();
		}		
		return RESULTAT_INSERTION_ARTICLE;		
	}
	
	/*Création d'une vente en base*/
	public String enregistrerVente(Article article, Vente vente){
		String RESULTAT_INSERTION_VENTE = "";		
		try{
			/* Création de l'objet gérant les requêtes */						
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/bt6gmrwbm");
			connexion = ds.getConnection();
			
			Statement statement = connexion.createStatement();
			statement.executeUpdate("INSERT INTO Vente (statut, date_debut, idArticle) VALUES ('"+vente.getStatut()+"', NOW(), "+article.getIdArticle()+")");
		}catch(Exception e){
			//setErreur("RESULTAT_INSERTION_VENTE", e.getMessage());
			RESULTAT_INSERTION_VENTE = "insertion vente: "+e.getMessage();
		}		
		return RESULTAT_INSERTION_VENTE;
	}	
	
	/*Récupération de l'ID de l'article nouvellement créé*/
	public int getIdArticle(){
		int idArticle = 0;
		try{
			/* Création de l'objet gérant les requêtes */						
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/bt6gmrwbm");
			connexion = ds.getConnection();
			
			Statement statement = connexion.createStatement();
			ResultSet resultatIdArticle = statement.executeQuery("SELECT idArticle FROM Article ORDER BY date_ajout");
			while(resultatIdArticle.next()){
				idArticle = resultatIdArticle.getInt("idArticle");
			}
		}catch(Exception e){
			System.out.println("Message idArticle: "+e.getMessage());
		}
		return idArticle;
	}
}
