package interfacceComunicazione;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Interfaccia client che contiene i metodi richiamati dal server per le chiamate remote
 */
public interface Client extends Remote {
	
	/**
	 * Metodo per impostare il comando da eseguire al client
	 *
	 * @param comando da eseguire
	 * @return esito della computazione
	 * @throws RemoteException
	 */
	public String comandoDaEseguire(String comando) throws RemoteException;
	
	/**
	 * Get del comando da eseguire
	 *
	 * @return il comando da eseguire
	 * @throws RemoteException 
	 */
	public String getComandoDaEseguire() throws RemoteException;
	
	/**
	 * Set del colore
	 *
	 * @param imposto il colore da settare
	 * @throws RemoteException
	 */
	public void setColore(String colore) throws RemoteException;
	

	public String getScelta() throws RemoteException;
}
