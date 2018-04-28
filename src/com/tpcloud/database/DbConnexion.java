package com.tpcloud.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DbConnexion {
	public java.sql.Connection dbconnexion(){
		/* Chargement du driver JDBC pour MySQL */
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
		    System.out.println(e.getMessage());
		}
		
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/bdd_enchere";
		String utilisateur = "rony";
		String motDePasse = "Pm32rony,";
		java.sql.Connection connexion = null;
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		
			/* Ici, nous placerons nos requêtes vers la BDD */
			/* ... */

		} catch ( SQLException e ) {
			System.out.println(e.getMessage());		
		} //finally {
			//if ( connexion != null )
				//try {
					/* Fermeture de la connexion */
					//connexion.close();
				//} catch ( SQLException ignore ) {
					/* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
				//}
		//}
		return connexion;
	}	
}
