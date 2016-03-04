package login;

import java.net.Socket;

import interfacceComunicazione.Client;
import interfacceComunicazione.Server;

import javax.swing.*;

import mappa.Mappa;

import partita.FinestraColori;
import prepartitaClient.FinestraPrePartita;
//AGGIORNARE QUANDO CI SAR� FinestraPartita e tutte le finestre
public class GestioneFinestre {
	
	private FinestraRegistrazione finestraRegistrazione = null;
	private FinestraLogin finestraLogin = null;
	private FinestraPrePartita finestraPartita = null;
	private FinestraComunicazione finestraComunicazione = null;
	private Mappa finestraMappa = null;
	private FinestraColori finestraColori = null;
	
	public void setCorrectWindow(Socket clientSocket, Server server, String next, JFrame chiudi, String tipoComunicazione, String user, String coloriLiberi, ClientImpl client) {
		
		next = next.toLowerCase();
		if (chiudi.getTitle().endsWith("Giochiamo")) {
			chiudi.dispose();
		}
		else {
			chiudi.setVisible(false);
		}
		if (next.equals("login")) {
			finestraLogin = new FinestraLogin();
			finestraLogin.visualizzaTipoComunicazione.setText(tipoComunicazione.toUpperCase());
			finestraLogin.getFinestraLogin().setVisible(true);
		}
		
		if (next.equals("registrati")) {
			finestraRegistrazione = new FinestraRegistrazione();
			finestraRegistrazione.visualizzaTipoComunicazione.setText(tipoComunicazione.toUpperCase());
			finestraRegistrazione.getFinestraRegistrazione().setVisible(true);
		}
		
		if (next.equals("gioca")) {
			finestraPartita = new FinestraPrePartita();
			finestraPartita.setTipoComunicazione(tipoComunicazione.toUpperCase());
			//System.out.println("setUsername(user)"+user);
			finestraPartita.setUsername(user);
			finestraPartita.setSocket(clientSocket);
			finestraPartita.getFinestraPartita().setVisible(true);
			finestraPartita.setServer(server);
			finestraPartita.setClientInt(client);
		}
		
		//LOGOUT
		if (next.equals("comunicazione")) {
			finestraComunicazione = new FinestraComunicazione();
			finestraComunicazione.getFinestraComunicazione().setVisible(true);
		}
		
		/*
		if (next.equals("mappa")) {
			finestraMappa = new Mappa();
			finestraMappa.getFinestraMappa().setVisible(true);
		}
		*/
		
		if (next.equals("colore")) {
			finestraColori = new FinestraColori(coloriLiberi);
			finestraColori.setSocket(clientSocket);
			finestraColori.setRMI(server);
			finestraColori.getFinestraColore().setTitle(user+": scegli il tuo colore");
			finestraColori.setUsername(user);
			finestraColori.getFinestraColore().setVisible(true);
		}
		
		//passo da comando la stringa trePlayer, quattroPlayer, cinquePlayer, seiPlayer
		//e se termina con Player apro la Mappa	
	}		
}
