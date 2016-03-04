package comunicazione;

import interfacceComunicazione.ServerAccept;
import interfacceComunicazione.Server;
import interfacceComunicazione.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import prepartita.ServerImpl;
import login.OperazioniLogin;


/**
 * Classe adibita all'avvio dell'oggetto remoto del server per l'RMI
 */
public class StartServerAcceptRMI extends UnicastRemoteObject implements ServerAccept {
	

	private static final long serialVersionUID = 1L;

	/** Valore dell'host */
	private static final String HOST = "127.0.0.1";
	
	/** URL per andare a prendere il registry */
	private static final String URL = "rmi://"+HOST+"/serverAccept";
	
	/** Registro per l'RMI */
	private Registry registry = null;
	
	/** Stub per la comunicazione iniziale */
	private ServerAccept stub = null;

	
	
	private Thread serverThread = null;
	
	/** Variabile utilizzata per richiamare i metodi del server nel client*/
	private ServerImpl server = null;
	
	/**
	 * Instanzia oggetto remoto per RMI
	 *
	 * @throws RemoteException 
	 */
	//Costruttore: da utilizzare quando farò il login
	public StartServerAcceptRMI() throws RemoteException {
	}

	/**
	 * Avvio del thread
	 */
	//start: avvia il server
	public void start() {
		
		System.out.println("-->START<--");
		
		try {
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
				System.out.println("-->Ho creato il Security Manager");
			}
			
			stub = new StartServerAcceptRMI();
			System.out.println("-->Ho creato lo stub");
			
			if (stub.equals(null)) System.out.println("Stub non c'è");
			
			registry = LocateRegistry.getRegistry(HOST);
			System.out.println("-->Ho creato il registro sull'"+HOST);

			System.out.println("-->Tento rebind di stub sull'"+URL);
			registry.rebind(URL, stub);
			System.out.println("-->Rebind di stub sull'"+URL);
			
		} catch (RemoteException e) {
			System.out.println("Errori d'avvio di RMI");
			System.out.println("Message: "+e.getMessage());
		}
		
	}
	
	/**
	 * Metodo verifica richiamato da remoto
	 */
	@Override
	public String verifica(String username) throws RemoteException {
		System.out.println("-->Attesa risultato di: "+username);
		return (new OperazioniLogin()).verifica(username);
	}

	/**
	 * Metodo registra richiamato tramite call back
	 */
	@Override
	public String registra(String username, String password) throws RemoteException {
		System.out.println("-->Attesa risultato di: "+username+";"+password);
		return (new OperazioniLogin()).registra(username, password);
	}

	/** 
	 * Metodo per eseguire il login tramite rmi
	 * @return la variabile che utilizzerò per effettuare call back
	 */
	@Override
	public Server loginRMI(String username, String password, Client client) throws RemoteException {
		
		boolean utentePresente = false;
		GestioneDatabase gestData = new GestioneDatabase();
		System.out.println("Ho invocato loginRMI");
		System.out.println("Username: "+username);
		System.out.println("Password: "+password);
		if ((new OperazioniLogin()).login(username, password).equals("true")) {
			//Il Client che mi arriva è buono.
			server = new ServerImpl(username, client, this);
			
		try {
			gestData.start();
			utentePresente = gestData.checkOnlineUser(username, "rmi");
			if(utentePresente) {
				System.out.println("Utente già presente, server nullo");
				return null;
			}
			else {
				serverThread = new Thread(server);
				server.setServerThread(serverThread);
				System.out.println("Utente NON presente, server buono");
				return server;
			}
		} catch (Exception e) {
			System.out.println("Errore connessione al DB");
			e.printStackTrace();
			return null;
			}
		}
		else{
			return null;
		}
	}

	/** 
	 * Metodo per il logout da remoto tramite RMI
	 */
	@Override
	public boolean logoutRMI(String username) throws RemoteException {
		
		boolean esitoOperazione = false;
		
		if (new OperazioniLogin().logout(username).equals("true")) {
			esitoOperazione = true;
		}
		
		return esitoOperazione;
	}
}
