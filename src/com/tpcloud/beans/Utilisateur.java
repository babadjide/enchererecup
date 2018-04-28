package com.tpcloud.beans;

public class Utilisateur {
	private int idUtilisateur;
	private String email;
	private String motDePasse;
	private String nom;
	
	public void setId(int idutilisateur){
		this.idUtilisateur = idutilisateur;
	}	
	public int getId(){
		return idUtilisateur;
	}
	
	public void setEmail(String email){
		this.email = email;
	}	
	public String getEmail(){
		return this.email;
	}
	
	public void setPassword(String motdepasse){
		this.motDePasse = motdepasse;
	}	
	public String getMotdepasse(){
		return this.motDePasse;
	}
	
	public void setNom(String nom){
		this.nom = nom;
	}	
	public String getNom(){
		return this.nom;
	}
}
