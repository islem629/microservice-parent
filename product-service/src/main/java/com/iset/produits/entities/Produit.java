package com.iset.produits.entities;

import java.util.Date;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity 
public class Produit{  

@Id 
@GeneratedValue (strategy = GenerationType.IDENTITY) 
private Long idProduit; 
private String nomProduit; 
private Double prixProduit; 
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date dateCreation;
@ManyToOne
private Categories categorie;

	@Override
	public String toString() {
		return "Produit{" +
				"idProduit=" + idProduit +
				", nomProduit='" + nomProduit + '\'' +
				", prixProduit=" + prixProduit +
				", dateCreation=" + dateCreation +
				", categorie=" + categorie +
				'}';
	}

	public Produit(String nomProduit, Double prixProduit, Date dateCreation) {
	super();
	this.nomProduit = nomProduit;
	this.prixProduit = prixProduit;
	this.dateCreation = dateCreation;
}
 public Produit() {}
	public Categories getCategorie() {
		return categorie;
	}

	public void setCategorie(Categories categorie) {
		this.categorie = categorie;
	}


	public Long getIdProduit() {
	return idProduit;
}
public void setIdProduit(Long idProduit) {
	this.idProduit = idProduit;
}
public String getNomProduit() {
	return nomProduit;
}
public void setNomProduit(String nomProduit) {
	this.nomProduit = nomProduit;
}
public Double getPrixProduit() {
	return prixProduit;
}
public void setPrixProduit(Double prixProduit) {
	this.prixProduit = prixProduit;
}
public Date getDateCreation() {
	return dateCreation;
}
public void setDateCreation(Date dateCreation) {
	this.dateCreation = dateCreation;
}
 
 } 