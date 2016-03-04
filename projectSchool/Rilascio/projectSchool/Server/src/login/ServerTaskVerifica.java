package login;

import java.io.*;
import java.net.*;


/**
 * Task del thread per la verifica che l'user non sia già inserito nel database in fase di registrazione
 */
public class ServerTaskVerifica {
	
	private Socket clientSocket = null;
	

	private String comando = null;
	
	/**
	 * Costruttore della classe
	 *
	 * @param Socket del client
	 * @param comando da eseguire
	 */
	public ServerTaskVerifica(Socket clientSocket, String comando){
		this.clientSocket = clientSocket;
		this.comando = comando;
	}

		//verifica se il nome utente inserito durante la registazione è già inserito nel database

		public synchronized void run() {
		
		PrintWriter writer = null;
		String nome = null;
		
		String risultato = null;
		int primaAt = comando.indexOf('@');
		comando = comando.substring(9);
		
		OperazioniLogin oplogin = new OperazioniLogin();
		
		try {
			
			writer = new PrintWriter(clientSocket.getOutputStream(),true);
			
			nome = "";
			int i = 0;
			
			for(i = primaAt + 1; i < comando.length(); i++){
				nome = nome+comando.charAt(i); 
			}
			
			System.out.println("Nome ricevuto dal client: "+nome);
			
			risultato = oplogin.verifica(nome);
			
			writer.println(risultato);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			System.err.println("Errore creazione lettore/scrittore nel thread");
		}
		
		
	}
	
	
}
