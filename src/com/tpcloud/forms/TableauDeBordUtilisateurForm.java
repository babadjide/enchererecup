package com.tpcloud.forms;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.tpcloud.beans.Article;
import com.tpcloud.beans.Mise;
import com.tpcloud.beans.Utilisateur;
import com.tpcloud.beans.Vente;
import com.tpcloud.database.BaseUtilisateur;
import com.tpcloud.database.DbConnexion;

public final class TableauDeBordUtilisateurForm {
	private static final String QUERY_ERROR = "erreurquery";
	public static final String ATT_USER = "utilisateur";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
	private HashMap<String, String> erreurs = new HashMap<String, String>();
	HashMap<String, Vente> lesventes = new HashMap<String, Vente>();
	String[] lesmises;
	private static final String FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";
	private Calendar calendrier = Calendar.getInstance();
	java.sql.Connection connexion = null;
	
	
	/*Création d'une instance de connexion*/
	DbConnexion nouvelleConnexion = new DbConnexion();
	
	
	public HashMap<String, Vente> listeDesVentes(HttpServletRequest request) throws Exception{
		java.util.Date[] dateDesVentes = ecartDate();
		HttpSession session = request.getSession();
		Utilisateur utilisateurConnecte = (Utilisateur)session.getAttribute(ATT_USER);
    	int idVente = 0;
    	String datedebutVente = "";
    	String statut = "";
    	String miseSup = "";
    	
    	Article articleVendu = null;
    	int idArticle = 0;
    	String nomArticle = "";
    	String dateAjoutArticle = "";
    	String descriptionArticle = "";
    	String statutArticle = "";
    	
    	Utilisateur utilisateurArticle = null;
    	int idUtilisateur = 0;
    	String nomUtilisateur = "";
    	String emailUtilisateur = "";
    	String motdepasseUtilisateur = "";
    	
    	/*Création d'un objet Vente*/
    	Vente vente = new Vente();
    	
    	try{
			/* Création de l'objet gérant les requêtes */						
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/bt6gmrwbm");
			connexion = ds.getConnection();
			
			Statement statement = connexion.createStatement();			
			ResultSet resultatVente = statement.executeQuery("select idVente, statut, date_debut, miseSup, idArticle, TIME(date_debut) from vente");
			
			/*récupérer le nombre de vente*/
			resultatVente.last();
			int nombreLigne = resultatVente.getRow();
			
			/*sauvegarder les ventes dans un tableau*/
			resultatVente.beforeFirst();
			String[][] listedesventes = new String[nombreLigne][5];
			for(int j = 0; j < nombreLigne; j++){
				resultatVente.next();
				for(int k = 0; k < 5; k++){
					if(resultatVente.getMetaData().getColumnLabel(k+1).equalsIgnoreCase("date_debut")){
						listedesventes[j][k] = String.valueOf(resultatVente.getDate("date_debut"));
						System.out.println("que date: "+listedesventes[j][k]);
						listedesventes[j][k] = listedesventes[j][k]+" "+String.valueOf(resultatVente.getTime("date_debut"));
						System.out.println("date et heure: "+listedesventes[j][k]);
					}else{
						if(!resultatVente.getMetaData().getColumnLabel(k+1).equalsIgnoreCase("TIME(date_debut)")){
							listedesventes[j][k] = String.valueOf(resultatVente.getObject(k+1));
							System.out.println("autres champs: "+listedesventes[j][k]);
						}
					}
				}
				
			}
			
			/*récupérer les informations requises pour le tableau de bord utilisateur*/
			for(int j = 0; j < nombreLigne; j++){
					idVente = Integer.valueOf(listedesventes[j][0]);
					statut = listedesventes[j][1];
					datedebutVente = listedesventes[j][2];
					if(listedesventes[j][3].equalsIgnoreCase("null")){
						miseSup = "Aucune mise trouvée";
					}else{
						miseSup = listedesventes[j][3];
					}
					idArticle = Integer.valueOf(listedesventes[j][4]);
					
					/*récupérer les informations sur l'article objet de la vente*/
					ResultSet resultatArticle = statement.executeQuery("select * from article where idArticle='"+idArticle+"'");
					if(resultatArticle.next()){
						idUtilisateur = resultatArticle.getInt("idUtilisateur");
						nomArticle = resultatArticle.getString("nom");
						descriptionArticle = resultatArticle.getString("description");
						dateAjoutArticle = String.valueOf(resultatArticle.getDate("date_ajout"));
						statutArticle = resultatArticle.getString("statut");
						
						/*récupérer les informations sur l'utilisateur qui a initié la vente*/
						ResultSet resultatUtilisateur = statement.executeQuery("select * from utilisateur where idUtilisateur='"+idUtilisateur+"'");
						if(resultatUtilisateur.next()){
							nomUtilisateur = resultatUtilisateur.getString("nom");
							emailUtilisateur = resultatUtilisateur.getString("email");
							motdepasseUtilisateur = resultatUtilisateur.getString("mot_de_passe");
									
							utilisateurArticle = remplirUtilisateur(idUtilisateur, nomUtilisateur, emailUtilisateur, motdepasseUtilisateur);
						}
						articleVendu = remplirArticle(idArticle, nomArticle, descriptionArticle, dateAjoutArticle, statutArticle, utilisateurArticle);
					}
					vente = remplirVente(idVente, datedebutVente, statut, miseSup, articleVendu);
					lesventes.put(String.valueOf(vente.getIdVente()), vente);
			}
    	}catch(SQLException e){
    		setErreur(QUERY_ERROR, e.getMessage());
    	}
    	//System.out.println(lesventes.size());
		return lesventes;
	}
	
	public void setErreur(String champ, String message){
		erreurs.put(champ, message);
	}
	
	public HashMap<String, String> getErreur(){
		return erreurs;
	}
	
	public String[] getListeMise(){
		return lesmises;
	}
	
    public Utilisateur remplirUtilisateur(int id, String nom, String email, String motdepasse){
    	Utilisateur utilisateur = new Utilisateur();
    	utilisateur.setId(id);
    	utilisateur.setNom(nom);
    	utilisateur.setEmail(email);
    	utilisateur.setPassword(motdepasse);
    	return utilisateur;
    }
    
    public Article remplirArticle(int id, String nom, String description, String dateajout, String statut, Utilisateur utilisateur){
    	Article article = new Article();
    	article.setIdArticle(id);
    	article.setNom(nom);
    	article.setDescription(description);
    	article.setDate_ajout(dateajout);
    	article.setStatut(statut);
    	article.setUtilisateur(utilisateur);
    	return article;
    }
    
    public Vente remplirVente(int idvente, String datedebut, String statut, String mise, Article article){
    	Vente vente = new Vente();
    	vente.setIdVente(idvente);
    	vente.setDebut(datedebut);
    	vente.setStatut(statut);
    	vente.setMiseSup(mise);
    	vente.setArticle(article);
    	return vente;
    }
    
    public String convertDate(Date date){
    	String dateAC = "";
    	DateTime dt = new DateTime();
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
        //dateAC = date.toString(FORMAT_DATE);
    	return dateAC;
    }
    
    public Date[] ecartDate(){
    	System.out.println("je suis dans EcartDate");
    	Date[] listeDates = null;
    	try{
    		/* Création de l'objet gérant les requêtes */
			/* Création de l'objet gérant les requêtes */						
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/bt6gmrwbm");
			connexion = ds.getConnection();
			
			Statement statement = connexion.createStatement();
    		ResultSet resultatNombre = statement.executeQuery("SELECT COUNT* FROM Vente");
    		int nombredevente = 0;
    		while(resultatNombre.next()){
    			nombredevente = resultatNombre.getInt(1);
    		}
			/*Sauvegarder les dates pour controle du statut de la vente*/
			listeDates = new Date[nombredevente];
    		ResultSet resultatDate = statement.executeQuery("SELECT date_debut, DATE_FORMAT(date_debut, '%d-%m-%y %h:%i:%s') FROM Vente");
    		int i = 0;
    		while(resultatDate.next()){
    			listeDates[i] = resultatDate.getDate("date_debut");
    			System.out.println("Temps vente en minute: "+listeDates[i].getTime());
    			java.util.Date dateCourante = new java.util.Date();
    			System.out.println("Temps actuelle en minute: "+dateCourante.getTime());
    			//if(((int)(dateCourante.getTime()/60000) - (listeDates[i].getTime()/60000)) >= 10){
    				//try{
    				//statement.executeUpdate("UPDATE Vente SET statut='cloturé' WHERE idVente ="+resultatDate.getInt("idVente"));
    				//}catch(Exception e){
    					//System.out.println("Mise à jour statut: "+e.getMessage());
    				//}
    			//}
    			i++;
    		}
    	}catch(Exception e){
    		System.out.println("EcartDate: "+e.getMessage());
    	}
    	return listeDates;
    }

}


