package com.tpcloud.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.tpcloud.beans.Mise;
import com.tpcloud.beans.Utilisateur;
import com.tpcloud.beans.Vente;

public final class BaseNouvelleMise {
	public static final String RESULTAT = "resultat";
	HashMap<String, String> erreurValidation = new HashMap<String, String>();
	String resultat = "";
	/*Création d'une instance de connexion*/
	DbConnexion nouvelleConnexion = new DbConnexion();
	java.sql.Connection connexion = null;
	
	public HashMap<String, String> enregistrerMise(int montantmise, int idvente, Utilisateur utilisateur){
				
		try{
			resultat = validationMise(montantmise, idvente);
			if("".equalsIgnoreCase(resultat)){
				/* Création de l'objet gérant les requêtes */						
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource ds = (DataSource)envCtx.lookup("jdbc/bt6gmrwbm");
				connexion = ds.getConnection();
				
				Statement statement = connexion.createStatement();
				String montantmiseStr = String.valueOf(montantmise);
				statement.executeUpdate("INSERT INTO Mise (montant, idVente, idUtilisateur) VALUES ('"+montantmiseStr+"', "+idvente+", "+utilisateur.getId()+")");
				statement.executeUpdate("UPDATE Vente SET miseSup='"+montantmiseStr+"' WHERE idVente="+idvente);
			}else{
				setErreur(RESULTAT, resultat);
			}
		}catch(Exception e){
			setErreur(RESULTAT, e.getMessage());
		}
		return erreurValidation;
	}
	
	/*Validation liée à la base*/
	private String validationMise(int montantmise, int idvente) throws Exception {
		int miseSup = 0;
		String resultatValidation = "";
		try{
			/* Création de l'objet gérant les requêtes */						
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/bt6gmrwbm");
			connexion = ds.getConnection();
			
			Statement statement = connexion.createStatement();
			ResultSet resultatMisesup = statement.executeQuery("select miseSup from vente where idVente="+idvente);
			while(resultatMisesup.next()){
				if("NULL".equalsIgnoreCase(resultatMisesup.getString("miseSup"))){
					miseSup = 0;
				}else{
					miseSup = Integer.valueOf(resultatMisesup.getString("miseSup"));
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		System.out.println("misesup: "+miseSup);
			if(montantmise <= miseSup){
				resultatValidation = "Veuillez saisir une mise supérieure à la mise actuelle";
			}
		return resultatValidation;
	}

	
	private void setErreur(String erreur, String message){
		erreurValidation.put(erreur, message);
	}
}
