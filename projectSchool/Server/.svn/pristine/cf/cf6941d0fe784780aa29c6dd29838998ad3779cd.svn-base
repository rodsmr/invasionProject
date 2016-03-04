package gestioneListe;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaUtentiOnline implements Serializable{
		
	private static final long serialVersionUID = 1L;
	public static ArrayList<String[]> clientNames = null;
	
	public ArrayList<String[]> istance(){
		if(clientNames == null) clientNames = new ArrayList<String[]>();
		return clientNames;
	}
	
	/**
	 * Funzione per la restituzione della lista
	 * @return restituisce la lista degli utenti online
	 */
	public ArrayList<String[]> getClientNames(){
		return clientNames;
	}
	
	/**
	 * Funzione che restituisce numero utenti online
	 * @return numero utenti online
	 */
	public int getNumUtenti(){
		return clientNames.size();
	}
}
