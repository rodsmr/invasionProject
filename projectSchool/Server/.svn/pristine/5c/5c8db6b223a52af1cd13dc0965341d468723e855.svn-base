package partita;

import java.util.ArrayList;
import java.util.Iterator;

public class Territorio {

	private String user = null;
	private String codice = null;
	private Integer numeroArmate = 0;
	private ArrayList<Territorio> confini = null;
	private String continente = null;
	
	public String getContinente(){
		return this.continente;
	}

	public Territorio(String codice, String continente){	
		confini = new ArrayList<Territorio>();
		this.codice = codice;
		this.continente = continente;
	}

	public void setNewUser(String user, Integer numeroArmate){
		this.user = user;
		this.numeroArmate = numeroArmate;
	}

	public String getUser(){
		return this.user;
	}
	
	public void setArmateTerritorio(Integer armate) {
		this.numeroArmate = armate;
	}
	
	public Integer getNumArmate(){
		return this.numeroArmate;
	}
	
	public void setConfini(Territorio ...territorios){
		for(Territorio territori: territorios){
			confini.add(territori);
		}
	}
	
	public ArrayList<Territorio> getConfini(){
		return this.confini;
	}
	
	public String getCodice(){
		return this.codice;
	}

}
