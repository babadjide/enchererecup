package com.tpcloud.forms;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tpcloud.beans.Article;
import com.tpcloud.beans.Mise;
import com.tpcloud.beans.Utilisateur;
import com.tpcloud.beans.Vente;
import com.tpcloud.database.BaseNouvelleMise;
import com.tpcloud.database.DbConnexion;

public final class NouvelleMiseForm {
	public static final String ATT_USER = "utilisateur";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
	public static final String PARAM_ID_VENTE = "idVente";
	public static final String MISE = "mise";
	public static final String LISTE_VENTES = "listeventes";
	public static final String RESULTAT = "resultat";
	private String resultat = "";
	
	
	/*Création d'une instance de connexion*/
	DbConnexion nouvelleConnexion = new DbConnexion();
	
	BaseNouvelleMise basenouvellemise = new BaseNouvelleMise();
	
	HashMap<String, String> erreurs = new HashMap<String, String>();
	
	int idarticle;
	String montantmise = "";
	int idvente;
	Utilisateur utilisateur;
	
	public void miser(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		idvente = Integer.valueOf(getValeurChampSession(session, PARAM_ID_VENTE));
		System.out.println("idVente: "+idvente);
		utilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_USER);
		System.out.println("idUtilisateur: "+utilisateur.getId());
		
		Mise mise = new Mise();
		
		try{			
			montantmise = getValeurChamp(request, MISE);
			if(montantmise.equalsIgnoreCase("null")){				
				setErreur(MISE, "Entrez le montant de la mise");
				System.out.println("je suis dedans");
			}else{
				validationMontant(montantmise);
				if(erreurs.isEmpty()){
					mise.setMontant(montantmise);
					Vente vente = getVente(idvente);
					System.out.println(montantmise);
					System.out.println(idvente);
					System.out.println(utilisateur.getId());
					erreurs = basenouvellemise.enregistrerMise(Integer.valueOf(montantmise), idvente, utilisateur);
				}
			}
			if(erreurs.isEmpty()){
				resultat = "Mise effectuée avec succès";
				//setErreur(RESULTAT, resultat);
			}else{
				resultat = "Mise non effectuée";
				//resultat = erreurs.get(RESULTAT);
				//setErreur(RESULTAT, resultat);
			}
		}catch(Exception e){
		}		
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
	
	private static String getValeurChampSession( HttpSession session, String nomChamp ) {
	    String valeur = (String)session.getAttribute(nomChamp);
	    if ( valeur == null || valeur.trim().length() == 0 ) {
	    	return null;
	    } else {
	    	return valeur.trim();
	    }
	}
	
	/*Validation à la saisie*/
	private void validationMontant(String montant){
			for(int i = 0; i < montant.length(); i++){
				if(Integer.valueOf(montant.substring(i, i+1)) != 0 && Integer.valueOf(montant.substring(i, i+1)) != 1 && Integer.valueOf(montant.substring(i, i+1)) != 2 && Integer.valueOf(montant.substring(i, i+1)) != 3 && Integer.valueOf(montant.substring(i, i+1)) != 4 && Integer.valueOf(montant.substring(i, i+1)) != 5 && Integer.valueOf(montant.substring(i, i+1)) != 6 && Integer.valueOf(montant.substring(i, i+1)) != 7 && Integer.valueOf(montant.substring(i, i+1)) != 8 && Integer.valueOf(montant.substring(i, i+1)) != 9){
					//throw new Exception ("Entrez un montant valide");
					setErreur(MISE, "Entrez un montant valide");
				}
			}
		
	}
	
	private Vente getVente(int idVente){
		Vente vente = new Vente();
		try{
			Statement statement = nouvelleConnexion.dbconnexion().createStatement();
			ResultSet resultatVente = statement.executeQuery("select * from Vente where idVente="+idVente);
			while(resultatVente.next()){
				vente.setIdVente(idVente);
				vente.setStatut(resultatVente.getString("statut"));
				vente.setDebut(resultatVente.getString("date_debut"));
				//vente.setMiseSup(montantmise);
				int idarticle = resultatVente.getInt("idArticle");
			}
		}catch(Exception e){
			
		}		
		return vente;
	}
	
	private void setErreur(String erreur, String message){
		erreurs.put(erreur, message);
	}
	
	public HashMap<String, String> getErreurs(){
		return erreurs;
	}
	
	public String getResultat(){
		return resultat;
	}
	

}
