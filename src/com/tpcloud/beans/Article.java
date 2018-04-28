package com.tpcloud.beans;

public class Article {
	private int idArticle;
	private String nom;
	private String description;
	private String date_ajout;
	private String statut;
	
	private Utilisateur utilisateur;
	
	public void setIdArticle(int idArticle){
		this.idArticle = idArticle;
	}	
	public int getIdArticle(){
		return idArticle;
	}
	
	public void setNom(String nom){
		this.nom = nom;
	}	
	public String getNom(){
		return nom;
	}
	
	public void setDescription(String description){
		this.description = description;
	}	
	public String getDescription(){
		return description;
	}
	
	public void setDate_ajout(String date_ajout){
		this.date_ajout = date_ajout;
	}	
	public String getDate_ajout(){
		return date_ajout;
	}
	
	public void setStatut(String statut){
		this.statut = statut;
	}	
	public String getStatut(){
		return statut;
	}
	
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur = utilisateur;
	}
	public Utilisateur getUtilisateur(){
		return utilisateur;
	}
}
