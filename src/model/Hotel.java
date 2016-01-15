package model;

import java.io.Serializable;

public class Hotel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idHotel;
	private String nomH;
	private String sitewebH;
	private String longitude;
	private String latitude;
	private String description;
	private String nbreetoile;
	private String telephoneH;
	private String emailH;
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

	public Long getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(Long idHotel) {
		this.idHotel = idHotel;
	}

	public String getNomH() {
		return nomH;
	}

	public void setNomH(String nomH) {
		this.nomH = nomH;
	}

	public String getSitewebH() {
		return sitewebH;
	}

	public void setSitewebH(String sitewebH) {
		this.sitewebH = sitewebH;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNbreetoile() {
		return nbreetoile;
	}

	public void setNbreetoile(String nbreetoile) {
		this.nbreetoile = nbreetoile;
	}

	public String getTelephoneH() {
		return telephoneH;
	}

	public void setTelephoneH(String telephoneH) {
		this.telephoneH = telephoneH;
	}

	public String getEmailH() {
		return emailH;
	}

	public void setEmailH(String emailH) {
		this.emailH = emailH;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Hotel [idHotel=" + idHotel + ", nomH=" + nomH + ", sitewebH="
				+ sitewebH + ", longitude=" + longitude + ", latitude="
				+ latitude + ", description=" + description + ", nbreetoile="
				+ nbreetoile + ", telephoneH=" + telephoneH + ", emailH="
				+ emailH + ", image=" + image + "]";
	}
}
