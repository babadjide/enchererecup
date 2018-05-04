package com.tpcloud.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;

import java.sql.Connection;
import com.mysql.jdbc.Statement;

public class DbConnexion {
	
	static final String URL = "jdbc:mysql://bt6gmrwbm-mysql.services.clever-cloud.com:3306/bt6gmrwbm?autoReconnect=true&amp;characterEncoding=cp1250";
	//static final String URL = "jdbc:mysql://bt6gmrwbm-mysql.services.clever-cloud.com:3306/bt6gmrwbm";
	static final String UTILISATEUR = "uoizcqliocvbhu53";
	static final String MOTDEPASSE = "PIBLdIN5wiPtsEoNtak";
	static final String HOSTNAME = "bt6gmrwbm-mysql.services.clever-cloud.com";
	static final String PORT = "3306";
	static final String DATABASE = "bt6gmrwbm";
	
	//Config config  = new Config();
	
	
	public java.sql.Connection dbconnexion() throws Exception {
		java.sql.Connection connexion = null;
		/* Chargement du driver JDBC pour MySQL */
		
		try {
		    //Class.forName( "com.mysql.jdbc.Driver" );
		    //connexion = DriverManager.getConnection("jdbc:mysql://"+HOSTNAME+":"+PORT+"/"+DATABASE+", "+UTILISATEUR+", "+MOTDEPASSE);
		    //connexion = DriverManager.getConnection("\"jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE + "\"", "\"" + UTILISATEUR + "\"" , "\"" +MOTDEPASSE + "\"");

		    
			Context initCtx = new InitialContext();

			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			org.apache.tomcat.dbcp.dbcp.BasicDataSource ds = (org.apache.tomcat.dbcp.dbcp.BasicDataSource)

			envCtx.lookup("jdbc/bt6gmrwbm");
			
			connexion = ds.getConnection();
		} catch ( SQLException e ) {
			throw e;
		} //finally {
			//if ( connexion != null )
				//try {
					/* Fermeture de la connexion */
					//connexion.close();
				//} catch ( SQLException ignore ) {
			/* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
				//}
		//}		
		
		//MySQL mysql = new MySQL(HOSTNAME, PORT, DATABASE, UTILISATEUR, MOTDEPASSE);
		System.out.println("Connexion établie");
		return connexion;
		}
}

