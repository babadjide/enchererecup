package com.tpcloud.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tpcloud.beans.Utilisateur;

public class BaseUtilisateur {
	/*Création d'une instance de connexion*/
	DbConnexion nouvelleConnexion = new DbConnexion();
	/* La liste qui contiendra tous les résultats de nos essais */
	private HashMap<String, String> messages = new HashMap<String, String>();
	private static final String INSERT_ERROR = "insertion";
	private static final String DOUBLON_ERROR = "doublon";
	private String MESSAGE = "";
	
	Utilisateur utilisateurlog = new Utilisateur();
	
	public String enregistrerUtilisateur( Utilisateur utilisateur ) {
        /* Ici, nous placerons le code de nos manipulations */
        /* ... */		
		try{
			/* Création de l'objet gérant les requêtes */
			Statement statement = nouvelleConnexion.dbconnexion().createStatement();
			
			/*Vérification de l'existence d'un compte avec ce nom*/
			ResultSet resultat = statement.executeQuery("select * from Utilisateur where nom='"+utilisateur.getNom()+"'");
			if(resultat.next()){
				MESSAGE = "nom déjà utilisé";
				System.out.println("nom déjà utilisé");
			}else{
				statement.executeUpdate( "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES ('"+utilisateur.getEmail()+"','"+utilisateur.getMotdepasse()+"','"+utilisateur.getNom()+"', NOW());" );
			}			
		}catch(Exception e){
			System.out.println(e.getMessage());
			MESSAGE = e.getMessage();
		}		
        return MESSAGE;
    }
	
	public String connecterUtilisateur(Utilisateur utilisateur){
		try{
			/* Création de l'objet gérant les requêtes */
			Statement statement = nouvelleConnexion.dbconnexion().createStatement();
			
			ResultSet resultat = statement.executeQuery("select * from Utilisateur where email='"+utilisateur.getEmail()+"' and mot_de_passe='"+utilisateur.getMotdepasse()+"'");
			if(resultat.next()){
				utilisateurlog.setId(resultat.getInt("idUtilisateur"));
				utilisateurlog.setEmail(resultat.getString("email"));
				utilisateurlog.setPassword(resultat.getString("mot_de_passe"));
				utilisateurlog.setNom(resultat.getString("nom"));
				MESSAGE = "succès";
			}else{
				MESSAGE = "échec";
			}
		}catch(Exception e){
			MESSAGE = e.getMessage();
		}
		return MESSAGE;
	}
	
	public Utilisateur getUtilisateurLog(){
		return utilisateurlog;
	}

}
