package prepartita;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import analisiComandi.AnalisiLogin;

public class ServerTaskPrepartita implements Runnable {

	private String comando = null;
	private Socket client = null;
	private PrintWriter writer = null;
	private Prepartita prePartita = null;
	private AnalisiLogin analisiLogin = null;
	
	public ServerTaskPrepartita(Socket client, String comando) {
		this.client = client;
		this.comando = comando;
		prePartita = new Prepartita();
		analisiLogin = new AnalisiLogin();
	}

	public void run() {

		System.out.println("comando: "+comando);
		if (comando.substring(0, 11).equals("prepartitaA")) {
			char numeroPlayer = '0';
			System.out.println("SONO IN ANNULLA IN SERVER");
			//devo cancellare in lista e comunicare a tutti gli altri utenti che sono in attesa
			//qua che adesso l'utente è calato
			comando = comando.substring(12);
			System.out.println("ANNULLA comando sub: "+comando);
			int posizioneSeparatore = analisiLogin.posizioneSeparatore(comando);//posizioneSeparatoreDati(comando);		
			System.out.println("Separatore ANNULLA: "+posizioneSeparatore);
			
			String user = analisiLogin.decodificaUser(comando, posizioneSeparatore);
			String numeroGiocatori = analisiLogin.decodificaPW(comando, posizioneSeparatore);
			String result = prePartita.rimuoviInLista(user, numeroGiocatori, client);

			System.out.println("1-"+result);
			if (result.startsWith("false")) {
				System.out.println("Rimozione fallita");
			}
			else {
				System.out.println("Rimozione corretta");
			}
			try {
				writer = new PrintWriter(client.getOutputStream(),true);
				writer.println(result);
				writer.flush();
				//writer.close();
			} catch (IOException e) {
				System.out.println("Errore creazione writer ServerTaskPrepartita Annulla");
			}
		}
		else {
			//devo inserire in lista	
			comando = comando.substring(11);
			int posizioneSeparatore = analisiLogin.posizioneSeparatore(comando);//posizioneSeparatoreDati(comando);		
			System.out.println("Separatore INSERIMENTO: "+posizioneSeparatore);
			
			//Inserimento
			String user = analisiLogin.decodificaUser(comando, posizioneSeparatore);//getUserFromComando(comando, posizioneSeparatore);
			String numeroGiocatori = analisiLogin.decodificaPW(comando, posizioneSeparatore);//getPWFromComando(comando, posizioneSeparatore);		
			String result = prePartita.aggiungiInLista(user, numeroGiocatori,client, null);
			
			//char numeroPlayer = result.charAt(result.length()-1);
			//result = result.substring(0, result.length()-1);
			if(result.equals("true"))
				System.out.println("User inserito correttamente");
			else
				System.out.println("Inserimento user fallito");
			try {
				writer = new PrintWriter(client.getOutputStream(),true);
				writer.println(result);
				writer.flush();
				//writer.close();
			} catch (IOException e) {
				System.out.println("Errore creazione writer ServerTaskPrepartita");
			}
		}
		
	}

}
