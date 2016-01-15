package model;

import java.io.Serializable;

public class Restaurant implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idresto;
     private String telephone;
     private String email;
     private String siteweb;
     private String longitude;
     private String latitude;
     private String nom;
     private String description;
     private String horairehouverture;
     private String horairefermeture;
     private String image;
     private String ville;
     private String pays;
     
     
     
     
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


	public Restaurant() {
		super();
	}
	
	
	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Long getIdresto() {
		return idresto;
	}
	public void setIdresto(Long idresto) {
		this.idresto = idresto;
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
	public String getHorairehouverture() {
		return horairehouverture;
	}
	public void setHorairehouverture(String horairehouverture) {
		this.horairehouverture = horairehouverture;
	}
	public String getHorairefermeture() {
		return horairefermeture;
	}
	public void setHorairefermeture(String horairefermeture) {
		this.horairefermeture = horairefermeture;
	}
     
	@Override
	public String toString() {
		//return nom+':'+longitude+','+latitude;
		return nom;
	}

}
