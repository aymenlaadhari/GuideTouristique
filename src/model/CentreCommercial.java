package model;

import java.io.Serializable;

public class CentreCommercial implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private String telephone;
    private String email;
    private String nom;
    private String description;
    private String siteweb;
    private String longitude;
    private String latitude;
    private String categorie;
    private String image;
    private String ville;
    private String pays;
    
    
    
    
    
    
	public void setId(long id) {
		this.id = id;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSiteweb() {
		return siteweb;
	}
	public void setSiteweb(String siteweb) {
		this.siteweb = siteweb;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public CentreCommercial() {
		super();
	}
	
	
    
	public CentreCommercial(long id, String telephone, String email,
			String nom, String description, String siteweb, String longitude,
			String latitude, String categorie) {
		super();
		this.id = id;
		this.telephone = telephone;
		this.email = email;
		this.nom = nom;
		this.description = description;
		this.siteweb = siteweb;
		this.longitude = longitude;
		this.latitude = latitude;
		this.categorie = categorie;
	}
	@Override
	public String toString() {
		return nom+':'+longitude+','+latitude;
	}
}
