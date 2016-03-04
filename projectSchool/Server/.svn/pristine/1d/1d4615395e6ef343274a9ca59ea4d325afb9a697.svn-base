package login;

import java.net.*;
import java.io.*;

import analisiComandi.AnalisiLogin;

public class ServerTaskRegistra {

	private Socket client = null;
	private String comando = null;
	private AnalisiLogin analisiLogin = null;
	
	public ServerTaskRegistra(Socket client, String comando) {
		this.client = client;
		this.comando = comando;
		this.analisiLogin = new AnalisiLogin();
	}
	
	public synchronized void run() {
		
		//Stringa ricevuta dal socket con notazione #user$pw#
		String user = null;
		String password = null;
		PrintWriter scrittore = null;
		String risultato = null;
		Integer flag = 0;
		
		try {
			//Scrittore
			scrittore = new PrintWriter(client.getOutputStream(), true);
			OperazioniLogin oplogin = new OperazioniLogin();
			System.out.println("Devo elaborare: "+comando);
			
			//Separatori			
			int primaAt = comando.indexOf('@');
			comando  = comando.substring(9);
			int posizioneSeparatore = analisiLogin.posizioneSeparatore(comando);
			
			user = analisiLogin.decodificaUser(comando, posizioneSeparatore);
			password = analisiLogin.decodificaPW(comando, posizioneSeparatore);
			System.out.println("User: "+user+" PW: "+password);
			
			risultato = oplogin.registra(user, password);
			
			System.out.println("-->Ho appena invocato oplogin.registra: "+risultato);
			
			if(risultato.equals("vuota"))			//quando la password è vuota 
				flag = -1;
			
			else if(risultato.equals("fallita"))		//quando è sbagliata la password
				flag = 0;
			
			else if(risultato.equals("ok"))			//password corretta
				flag = 1;
		}
		catch(IOException e){
			System.out.println("Errore creazione scrittore");
			
			
		} finally {
			//Rispedisco lo stato della registrazione
			scrittore.println(flag.toString());
			scrittore.flush();
			scrittore.close();
		}
	}	
}
