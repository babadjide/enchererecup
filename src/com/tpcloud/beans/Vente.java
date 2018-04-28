package com.tpcloud.beans;

public class Vente {
	private int idVente;
	private String debut;
	private String statut;
	private String miseSup;
	
	private Article article;
	
	public void setIdVente(int idVente){
		this.idVente = idVente;
	}	
	public int getIdVente(){
		return idVente;
	}
	
	public void setDebut(String date_debut){
		this.debut = date_debut;
	}	
	public String getDebut(){
		return this.debut;
	}
	
	public void setStatut(String statut){
		this.statut = statut;
	}	
	public String getStatut(){
		return statut;
	}
	
	public void setMiseSup(String misesup){
		this.miseSup = misesup;
	}	
	public String getMiseSup(){
		return miseSup;
	}
	
	public void setArticle(Article article){
		this.article = article;
	}
	public Article getArticle(){
		return article;
	}
}
