package model;

public class ListItemCustom {
	 private String title;
	    private int icon;
	     
	    public ListItemCustom(String title, int icon){
	        this.title = title;
	        this.icon = icon;
	    }
	     
	    public String getTitle(){
	        return this.title;      
	    }
	     
	    public int getIcon(){
	        return this.icon;
	    }

}
