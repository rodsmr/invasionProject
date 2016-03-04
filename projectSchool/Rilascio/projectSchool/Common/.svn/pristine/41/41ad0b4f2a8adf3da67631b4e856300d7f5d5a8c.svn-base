package interfacceComunicazione;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia per il login e il logout di un utente tramite rmi
 */
public interface ServerAccept extends Remote {

	/**
	 * Verifica che l'utente non sia già presente nel database
	 *
	 * @param username utente
	 * @return esito della verifica 
	 * @throws RemoteException
	 */
	//search user on DB before create a new user
	public String verifica(String username) throws RemoteException;
	
	/**
	 * Registrazione da remoto tramite rmi
	 *
	 * @param username utente
	 * @param password dell'utente
	 * @return esito registra
	 * @throws RemoteException
	 */
	//registry new user
	public String registra(String username, String password) throws RemoteException;

	/**
	 * Login da rmi
	 *
	 * @param username dell'utente
	 * @param password 
	 * @param client interfaccia per richiamare i metodi del client
	 * @return l'oggetto remoto per le chiamate remote
	 * @throws RemoteException 
	 */
	//login user
	public Server loginRMI(String username, String password, Client client) throws RemoteException;
	
	/**
	 * Logout rmi
	 *
	 * @param username 
	 * @return true se viene effettuato il logout
	 * @throws RemoteException 
	 */
	//logout user
	public boolean logoutRMI(String username) throws RemoteException;
}
