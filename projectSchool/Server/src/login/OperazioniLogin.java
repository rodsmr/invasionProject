package login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comunicazione.GestioneDatabase;
import comunicazione.StartServerSocket;


/**
 * The Class OperazioniLogin.
 */
public class OperazioniLogin {
	
	/** Gestione database. */
	GestioneDatabase gestioneDatabase = null;
	
	/** Statement per operazioni */
	Statement st = null;
	
	/** Task di avvio del socket */
	StartServerSocket start = null;
	
	String result = null;
	
	/**
	 * Costruttore classe
	 */
	public OperazioniLogin() {
		result = "false";
	}	
	
	/**
	 * La funzione logout avvia il database, crea lo statement ed esegue la query di rimozione.
	 *
	 * @param user dell'utente che svolge il logout
	 * @return una variabile Stringa in cui c'è il risultato del logout
	 */
	public String logout(String user) {
		System.out.println("OPERAZIONI LOGIN --> LOGOUT");
		GestioneDatabase gestioneDatabase = new GestioneDatabase();
		Statement st = null;
		String query = "DELETE FROM giocatorionline WHERE giocatore = '"+user+"'";
		String valoreRitorno = null;
		
		try {
			gestioneDatabase.start();
			System.out.println("DB AVVIATO");
		} catch (Exception e) {
			System.out.println("DB non avviato");
			System.out.println("Message not start: "+e.getMessage());
		}
		try{
			st = gestioneDatabase.getConnessione().createStatement();	//creo statement per query
			Integer result = st.executeUpdate(query);
			if(result==1){								//query eseguita correttamente
				valoreRitorno = "true";
			}
			else if(result==0){
				valoreRitorno = "false";
			}
			System.out.println("QUERY ESEGUITA");
			st.close();
			gestioneDatabase.chiudiRisorse();		//chiudo tutte le risorse del database che mi permettono di accedervi
		}
		catch(SQLException e){
			System.out.println("Problema creazione statement");
			System.out.println("Errore nel risultato della query");
			System.out.println("Message in verifica: "+e.getMessage());
			valoreRitorno = "false";
		}
		System.out.println("OPERAZIONI LOGIN --> FINE LOGOUT");
		return valoreRitorno;
	}

	/**
	 * Login.
	 *
	 * @param String dell'username utente
	 * @param Password dell'utente
	 * @return Se l'utente è registrato già nel database
	 */
	public String login(String user, String psw){

		System.out.println("USERNAME LOGINDB: "+user);
		GestioneDatabase gestioneDatabase = new GestioneDatabase();
		Statement st = null;
		
		try{
			gestioneDatabase.start();
		} catch(Exception e){
			System.out.println("DB non avviato");
			System.out.println("Message not start: "+e.getMessage());
		}
		try{
			st = gestioneDatabase.getConnessione().createStatement();
			ResultSet risultatoQuery = null;
			
			risultatoQuery = st.executeQuery("SELECT giocatore, password  FROM utente");
			while(risultatoQuery.next() && result=="false"){
	
				if (risultatoQuery.getString("giocatore").equals(user) && risultatoQuery.getString("password").equals(psw)) {
					result = "true";
				}
				else {
					result = "false";
				}
			}
			risultatoQuery.close();
			st.close();
		}
		catch(SQLException e){
			System.out.println("Problema creazione statement");
			System.out.println("Errore nel risultato della query");
			System.out.println("Message in verifica: "+e.getMessage());
			result = "false";
		}
		return result;	
	}
	
	/**
	 * Verifica se l'user dell'utente è già presente nel database
	 *
	 * @param User dell'utente 
	 * @return true o false a seconda che l'utente è presente o meno
	 */
	public String verifica(String user) {	
		
		GestioneDatabase gestioneDatabase = new GestioneDatabase();
		Statement st = null;
		
		try {
			gestioneDatabase.start();
		} catch (Exception e1) {
			System.out.println("DB non avviato");
			System.out.println("Message not start: "+e1.getMessage());
		}
		try {
			st = gestioneDatabase.getConnessione().createStatement();

			ResultSet risultatoQuery = null;

			System.out.println("Verifica user effettivo: "+user);
			System.out.println("opLogin->verifica->querySelect");
			risultatoQuery = st.executeQuery("SELECT giocatore FROM utente");
			System.out.println("AFTER opLogin->verifica->querySelect");
			String risultato = null;
			while(risultatoQuery.next() && result == "false"){
				risultato = risultatoQuery.getString("giocatore");
				System.out.println("-->Verifica: "+risultato+" "+user);
				if(risultato.equals(user)){
					result = "true";
				}				
			}
			risultatoQuery.close();
			st.close();
			gestioneDatabase.chiudiRisorse();
		} catch (SQLException e) {
			System.out.println("Problema creazione statement");
			System.out.println("Errore nel risultato della query");
			System.out.println("Message in verifica: "+e.getMessage());
			result = "true";
		}
		return result;
	}
	
	/**
	 * Registra.
	 *
	 * @param Username utente
	 * @param Password utente
	 * @return il risultato dell'operazione
	 */
	public String registra(String user, String password){
		
		GestioneDatabase gestioneDatabase = new GestioneDatabase();
		Statement st = null;
		String result = null;
		
		if (password.equalsIgnoreCase("")) {
			return "vuota"; //quando la password è vuota 
		}		
		else {
			
			try {
				gestioneDatabase.start();
			} catch (Exception e1) {
				System.out.println("DB non avviato");
				System.out.println("Message not start: "+e1.getMessage());
				result = "fallita";
			}
			System.out.println("--->registra: "+user+" "+password);
			String query = "INSERT INTO utente(giocatore, password, punteggio) VALUES('"+user+"','"+password+"', 0)";
			try {
				
				st = gestioneDatabase.getConnessione().createStatement();
				
				if (st.executeUpdate(query) == 1) {
					System.out.println("Registrazione OK");
					result = "ok";									//registrazione andata a buon fine
				}
				else {
					System.out.println("Registrazione NOK");				
					result = "fallita";
				}
				st.close();
			} catch (SQLException e) {
				System.out.println("Message: "+e.getMessage());
				result = "fallita";
			}
			return result;
		}
	}
}
