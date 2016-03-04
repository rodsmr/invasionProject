package interfacceComunicazione;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Interfaccia server per richiamare i metodi del server dal client tramite chiamate remote
 */
public interface Server extends Remote {
	
	/**
	 * Inserimento in lista prepartita dell'utente
	 *
	 * @param username utente
	 * @param in quale lista di attesa andarsi ad accodare
	 * @param metodo utilizzato 
	 * @return esito dell'inserimento 
	 * @throws RemoteException
	 */
	public String inserimentoListaPrepartita(String username, String player, String metodo) throws RemoteException;

	/**
	 * Rimuove l'utente dalla lista di attesa
	 *
	 * @param username dell'utente
	 * @param numeroGiocatori 
	 * @param tipo di comunicazione
	 * @return esito rimozione
	 * @throws RemoteException 
	 */
	public String rimuoviInLista(String username, String numeroGiocatori, String metodo) throws RemoteException;

	/**
	 * Assegnazione colore
	 *
	 * @throws RemoteException
	 */
	public void assegnaColoreServerImpl() throws RemoteException;
	
}
