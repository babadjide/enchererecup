package com.tpcloud.beans;

public class Mise {
	private int idMise;
	private String montant;
	
	private Vente vente;
	private Utilisateur utilisateur;
	
	public void setIdMise(int idMise){
		this.idMise = idMise;
	}	
	public int getIdMise(){
		return idMise;
	}
	
	public void setMontant(String montant){
		this.montant = montant;
	}	
	public String getMontant(){
		return montant;
	}
	
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur = utilisateur;
	}	
	public Utilisateur getUtilisateur(){
		return utilisateur;
	}
	
	public void setVente(Vente vente){
		this.vente = vente;
	}
	public Vente getVente(){
		return vente;
	}
}

