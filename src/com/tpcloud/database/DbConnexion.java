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
		String url = "jdbc:mysql://bt6gmrwbm-mysql.services.clever-cloud.com:3306/bt6gmrwbm?autoReconnect=true&amp;characterEncoding=cp1250";
		String utilisateur = "uoizcqliocvbhu53";
		String motDePasse = "PIBLdIN5wiPtsEoNtak";
		java.sql.Connection connexion = null;
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
			//connexion = DriverManager.getConnection("mysql -h bt6gmrwbm-mysql.services.clever-cloud.com -P 3306 -u uoizcqliocvbhu53 -p bt6gmrwbm");
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
