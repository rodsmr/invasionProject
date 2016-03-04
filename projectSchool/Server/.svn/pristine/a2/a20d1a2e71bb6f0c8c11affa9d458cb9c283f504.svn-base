package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import analisiComandi.AnalisiLogin;
import comunicazione.GestioneDatabase;

/**
 * Classe per il logout dell'utente
 */
public class ServerTaskLogout {


	private GestioneDatabase gestioneDB = null;
	

	private Socket client = null;
	

	private PrintWriter writer = null;
	
	private BufferedReader reader = null;
	

	private AnalisiLogin analisiLogin = null;

	private String comando = null;
	

	private String user = null;

	public ServerTaskLogout(Socket client, String comando, String user){
		
		this.client = client;
		this.comando = comando;
		this.user = user;
		
		gestioneDB = new GestioneDatabase();
		analisiLogin = new AnalisiLogin();	
	}
	
	

	
	
	public void run() {
		String result = null;
		OperazioniLogin opLogin = new OperazioniLogin();
		
		System.out.println("SERVER TASK LOGOUT");
		System.out.println("User for logout:" +user);
		result = opLogin.logout(user);
		if(result.equals("true")){
			result = "ok@"+user;			//restistuisce se il logout è stato effettuato correttamente
		}
		else{
			result = "ko@errore";
		}
		writer = null;
		try {
			writer = new PrintWriter(client.getOutputStream(),true);		//lo scrive al server che provvede al logout
			writer.print(result);
			writer.flush();
			writer.close();
		}
		catch(IOException e){
			System.out.println("Writer error");
			writer.print("ko@errore");
			System.out.println("Message: "+e.getMessage());
		}
	}
}
