package login;

import java.net.Socket;
import java.io.*;

import analisiComandi.AnalisiLogin;

import comunicazione.GestioneDatabase;


/**
 *	Classe per la gestione dei login dei vari utenti 
 */
public class ServerTaskLogin {

	/** Variabile per la gestione del database */
	private GestioneDatabase gestioneDB = null;
	
	/** Socket del client che si connette */
	private Socket client = null;
	
	private String comando = null;
	
	/** Scrittore per comunicare sul socket */
	private PrintWriter scrittore = null;
	
	/** Classe per scompattare gli username con caratteri speciali */
	private AnalisiLogin analisiLogin = null;
	
	/**
	 * Istanzio un nuovo thread per gestire i login
	 *
	 * @param Prende il socket
	 * @param Comando da eseguire
	 */
	public ServerTaskLogin(Socket client, String comando) {
		this.client = client;
		this.comando = comando;
		
		gestioneDB = new GestioneDatabase();			
		analisiLogin = new AnalisiLogin();
	}


	/**
	 * Esecuzione del thread
	 */
	public void run() {
		
		comando = comando.substring(6);
		int posizioneSeparatore = analisiLogin.posizioneSeparatore(comando);
		System.out.println("Separatore: "+posizioneSeparatore);
		String user = analisiLogin.decodificaUser(comando, posizioneSeparatore);	//decodifica user con caratteri speciali
		String pw = analisiLogin.decodificaPW(comando, posizioneSeparatore);	
		System.out.println("User per LOGIN ricevuto: "+user);
		System.out.println("PW per LOGIN ricevuto: "+pw);
		String result = null;
		
		scrittore = null;
		OperazioniLogin opLogin = new OperazioniLogin();
		
		result = opLogin.login(user, pw);
		System.out.println("Esito di "+user+" e "+pw+": "+result);
		if (result.equals("true")) {	
			try {
				gestioneDB.start();		//istanzio le risore del database che mi servono per le interrogazioni
				if (!(gestioneDB.checkOnlineUser(user, "socket"))) {		//controlla se l'utente ha già effettuato il login	
					result = "ok@";
				}
				else {
					result = "ko@prevLog";
				}
			} catch (Exception e) {
				result = "ko@sqlExcp";				//il database non è stato avviato
			}
		}
		else {
			result = "ko@invalidData";			//quando l'utente non è registrato
		}
		
		try {
			scrittore = new PrintWriter(client.getOutputStream(),true);
			scrittore.print(result);
			scrittore.flush();
			scrittore.close();
		}
		catch(IOException e){
			scrittore.println("false");
		}
	}
}