package avviaServer;

import java.rmi.RemoteException;

import comunicazione.StartServerAcceptRMI;
import comunicazione.StartServerSocket;

// TODO: Auto-generated Javadoc
/**
 * Avvio del server
 */
public class StartServer {

	public static void main(String[] args) {
		
		try {
			new StartServerAcceptRMI().start();		//richiamo thread per la creazione dell'oggetto remoto
		} catch (RemoteException e) {
			System.out.println("Errore avvio RMI");
		}

		new StartServerSocket().start();			//Richiamo thread per la creazione del socket
	}	
}


