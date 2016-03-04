package comunicazione;

import java.io.*;
import java.net.*;
import java.sql.*;

import login.TaskGestioneComandi;


/**
 * Avvia il socket per la comunicazione
 */
public class StartServerSocket {
	
	/** Statement per accesso al database */
	private Statement statement = null;
	
	/** Porta su cui instanziare il canale */
	private static final int PORT = 4500;
	
	/**
	 * Set dello statement
	 */
	public synchronized void setStatement(Statement statement) {
		this.statement = statement;
	}

	/**
	 *	Get dello statement
	 *
	 * @return lo statement
	 */
	public Statement getStatament() {
		return this.statement;
	}

	/**
	 * start del thread per la creazione del socket
	 */
	public void start() {
		
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		Thread thread = null;
		TaskGestioneComandi taskGestione = null;
		
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Socket avviato");
		} catch (IOException e) {
			System.out.println("Errore listen su "+PORT);
			System.exit(1);
		}
		while(true){
			try {
				clientSocket = serverSocket.accept();		
				taskGestione = new TaskGestioneComandi(clientSocket);
				thread = new Thread(taskGestione);
				thread.start();
			} catch (IOException e) {
				System.err.println("Errore su accept da parte del server");
			}
		}
	}	
}

	
	


