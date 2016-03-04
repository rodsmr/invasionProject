package partita;

import interfacceComunicazione.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import mappa.Mappa;

public class Partita {

	private GestioneComandiPartita gestioneComandi = null;
	
	private Socket clientConnesso = null;
	private Server server = null;
	private String username = null;
	private String coloreUser = null;
	private BufferedReader lettore = null;
	private ArrayList<String> indiceTerritori = null;
	private Mappa map = null;
	
	public Partita(Socket client, String username, String colore) {
		this.clientConnesso = client;
		this.username = username; 
		this.coloreUser = colore;
		this.indiceTerritori = new ArrayList<String>();
		
		this.map = new Mappa(username,coloreUser);

		this.gestioneComandi = new GestioneComandiPartita();
		this.gestioneComandi.setVariabili(this.clientConnesso,indiceTerritori,coloreUser,username,this,map);
		
		Thread thread = new Thread(gestioneComandi);
		thread.start();
		
	}
	
	public Partita(Server server, String username, String colore) {
		this.server = server;
		this.username = username;
		this.coloreUser = colore;
		
		this.map = new Mappa(this.username, this.coloreUser);
		
		this.indiceTerritori = new ArrayList<String>();
		
	}
	
	public Mappa getMappa() {
		return this.map;
	}
	
}