package model;

import java.io.Serializable;

public class Monument implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idMonument;
    private String nomM;
    private String longitude;
    private String latitude;
    private String description;
    private String type;
    private String dateconstruction;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getIdMonument() {
		return idMonument;
	}
	public void setIdMonument(Long idMonument) {
		this.idMonument = idMonument;
	}
	public String getNomM() {
		return nomM;
	}
	public void setNomM(String nomM) {
		this.nomM = nomM;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDateconstruction() {
		return dateconstruction;
	}
	public void setDateconstruction(String dateconstruction) {
		this.dateconstruction = dateconstruction;
	}
	
     
     
	
	@Override
	public String toString() {
		//return nomM+':'+longitude+','+latitude;
		return nomM;
	}
}
