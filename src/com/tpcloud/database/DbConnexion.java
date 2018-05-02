package com.tpcloud.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DbConnexion {
	
	//String url = "jdbc:mysql://bt6gmrwbm-mysql.services.clever-cloud.com:3306/bt6gmrwbm?autoReconnect=true&ampcharacterEncoding=cp1250";
	static final String URL = "jdbc:mysql://bt6gmrwbm-mysql.services.clever-cloud.com:3306/bt6gmrwbm?autoReconnect=true&amp;characterEncoding=cp1250";
	static final String UTILISATEUR = "uoizcqliocvbhu53";
	static final String MOTDEPASSE = "PIBLdIN5wiPtsEoNtak";
	
	public java.sql.Connection dbconnexion() throws Exception {
		java.sql.Connection connexion = null;
		/* Chargement du driver JDBC pour MySQL */
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );		    
		    connexion = DriverManager.getConnection( URL, UTILISATEUR, MOTDEPASSE );		    
		} catch ( ClassNotFoundException e ) {
		    throw e;
		} catch ( SQLException e ) {
			throw e;
		}
		System.out.println("Connexion établie");
		return connexion;
		}
}
		
		/* Connexion à la base de données */

		
		//try {
			
			//connexion = DriverManager.getConnection("mysql -h bt6gmrwbm-mysql.services.clever-cloud.com -P 3306 -u uoizcqliocvbhu53 -p bt6gmrwbm");
			/* Ici, nous placerons nos requêtes vers la BDD */
			/* ... */

		//} catch ( SQLException e ) {
			//System.out.println(e.getMessage());
		//} //finally {
			//if ( connexion != null )
				//try {
					/* Fermeture de la connexion */
					//connexion.close();
				//} catch ( SQLException ignore ) {
					/* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
				//}
		//}
		//return connexion;
	//}	
//}
