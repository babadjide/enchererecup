package com.tpcloud.forms;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.tpcloud.beans.Utilisateur;
import com.tpcloud.database.BaseUtilisateur;

public final class ConnexionForm {
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_PASS = "motdepasse";	
	private static final String CHAMP_CONNEXION = "connexion";
	private String resultat;
	private HashMap<String, String> erreurs = new HashMap<String, String>();
	
	BaseUtilisateur baseutilisateur = new BaseUtilisateur();
	
	public String getResultat(){
		return resultat;
	}
	
	public HashMap<String, String> getErreurs(){
		return erreurs;
	}
	
	public Utilisateur connecterUtilisateur(HttpServletRequest request){
		/*Récupération des champs du formulaire*/
		String email = getValeurChamp(request, CHAMP_EMAIL);
		String motDePasse = getValeurChamp(request, CHAMP_PASS);
		
		Utilisateur utilisateur = new Utilisateur();
		
		/*Validation des champs du formulaire*/
		try{
			validationEmail(email);
		}catch(Exception e){
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
		utilisateur.setEmail(email);
		
		try{
			validationMotDePasse(motDePasse);
		}catch(Exception e){
			setErreur(CHAMP_PASS, e.getMessage());
		}
		utilisateur.setPassword(motDePasse);
		
		/*Initialisation du résultat global de la validation*/
		if(erreurs.isEmpty()){
			resultat = baseutilisateur.connecterUtilisateur(utilisateur);
			if(resultat.equalsIgnoreCase("succès")){
				utilisateur = baseutilisateur.getUtilisateurLog();
				resultat = "Succès de la connexion";
			}else{
				setErreur(CHAMP_CONNEXION, resultat);
				if(resultat.equalsIgnoreCase("échec")){
					resultat = "Adresse email ou mot de passe incorrect";
				}
			}
		}else{
			resultat = "Echec de la connexion";
		}		
		return utilisateur;
	}
	
    /**
     * Valide l'adresse email saisie.
     */
    private void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse( String motDePasse ) throws Exception {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new Exception( "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir votre mot de passe." );
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
            return valeur;
        }
    }
}
