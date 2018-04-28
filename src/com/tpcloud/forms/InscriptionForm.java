package com.tpcloud.forms;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.tpcloud.beans.Utilisateur;
import com.tpcloud.database.BaseUtilisateur;

public final class InscriptionForm {
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_PASS = "motdepasse";
	private static final String CHAMP_CONF = "confirmation";
	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_INSERTION = "insertion";
    private String resultat = "";
    private HashMap<String, String> erreurs = new HashMap<String, String>();
    //private HashMap<String, String> erreursInsertion = new HashMap<String, String>();
    BaseUtilisateur baseutilisateur = new BaseUtilisateur();
    
    public String getResultat(){
    	return this.resultat;
    }
    
    public HashMap<String, String> getErreurs(){
    	return this.erreurs;
    }
    
    public Utilisateur inscrireUtilisateur(HttpServletRequest request){
    	String email = getValeurChamp(request, CHAMP_EMAIL);
    	String motDePasse = getValeurChamp(request, CHAMP_PASS);
    	String confirmation = getValeurChamp(request, CHAMP_CONF);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	
    	Utilisateur utilisateur = new Utilisateur();
    	
    	try{
    		validationEmail(email);
    	}catch(Exception e){
    		setErreur(CHAMP_EMAIL, e.getMessage());
    	}
    	utilisateur.setEmail(email);
    	
    	try{
    		validationMotDePasse(motDePasse, confirmation);
    	}catch(Exception e){
    		setErreur(CHAMP_PASS, e.getMessage());
    		setErreur(CHAMP_CONF, null);
    	}
    	utilisateur.setPassword(motDePasse);
    	
    	try{
    		validationNom(nom);
    	}catch(Exception e){
    		setErreur(CHAMP_NOM, e.getMessage());
    	}
    	utilisateur.setNom(nom);
    	
    	if(erreurs.isEmpty()){
    		/*Si aucune erreur dans la saisie des informations du formulaire*/
    		resultat = baseutilisateur.enregistrerUtilisateur(utilisateur);
    		/*Vérification de la réussite de l'insertion en base*/
    		if("".equalsIgnoreCase(resultat)){
    			resultat = "Succès de l'inscription";
    		}else{
    			setErreur(CHAMP_INSERTION, resultat);
    		}
    	}else{
    		resultat = "Echec de l'inscription";
    	}    	
    	return utilisateur;
    }
    
	private void validationEmail(String email) throws Exception{
	    if ( email != null && email.trim().length() != 0 ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    } else {
	        throw new Exception( "Merci de saisir une adresse mail." );
	    }
	}
	
	private void validationMotDePasse(String motDePasse, String confirmation) throws Exception{
	    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
	        if (!motDePasse.equals(confirmation)) {
	            throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
	        } else if (motDePasse.trim().length() < 3) {
	            throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
	        }
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}
	
	private void validationNom(String nom) throws Exception{
	    if ( nom != null && nom.trim().length() < 3 ) {
	        throw new Exception( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
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
}
