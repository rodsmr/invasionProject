package comunicazione;

import java.sql.*;

/**
 *  Classe utilizzata per la gestione della connessione con il database
 */
public class GestioneDatabase {

	/** Nome del database. */
	private static final String DBNAME = "invasion";
	
	/** Driver utilizzati. */
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	
	/** Variabile per la connessione al database. */
	private Connection connessione = null;
	
	/**
	 * Lancio del run
	 *
	 * @throws Lancia l'eccezione nel caso in cui non è possibile avviare la connessione
	 */
	public void start() throws Exception {
		String URL = "jdbc:derby:"+DBNAME;
		String user = "admin";
		String pw = "admin";
		
		Class.forName(DRIVER).newInstance();
		connessione = DriverManager.getConnection(URL, user, pw);
	}
	
	/**
	 * Get della connessione
	 *
	 * @return la connessione al database
	 */
	public Connection getConnessione() {
		return this.connessione;
	}
	
	/**
	 * Metodo per la visualizzazione del database
	 */
	public void visualizzaDB() {
		ResultSet resultSet = null;
		Statement statement = null;
		
		try {
			statement = connessione.createStatement();
			resultSet = statement.executeQuery("SELECT giocatore, password FROM utente");
			while (resultSet.next()) {
				System.out.println("Giocatore: "+resultSet.getString("giocatore")+"\t"+resultSet.getString("password"));
			}
		} catch (SQLException e) {
			System.out.println("Problma esecuzione resultSet");
			System.out.println("Message esecuzione: "+e.getMessage());
			// bisogna decidere cosa fargli fare
		} finally {
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("Problma chiusura resultSet");
				System.out.println("Message chiusura: "+e.getMessage());
				// bisogna decidere cosa fargli fare
			}
		}
	}
	
	/**
	 * Metodo che chiude tutte le risorse per l'accesso al database
	 */
	public void chiudiRisorse() {
		
		String queryCancella = "DELETE FROM giocatorionline";
		
		try {
			connessione.createStatement().executeUpdate(queryCancella);
			connessione.createStatement().close();
			connessione.close();
		} catch (SQLException e) {
			System.out.println("Problemi chiusa statetement e/o connessione");
			// bisogna decidere cosa fargli fare
		}
	}
	
	/**
	 * Metodo che controlla se l'utente che vuole effettuare il login è già loggato
	 *
	 * @param username dell'utente
	 * @param tipo di connessione 
	 * @return true se l'utente è già online
	 */
	//ritorna true se l'utente c'è già
	public boolean checkOnlineUser(String username, String metodo) {
		
		System.out.println("CONFRONTO IN DB di "+username);
		Statement st = null;
		
		String queryInsert = "INSERT INTO giocatorionline(giocatore, metodo) VALUES('"+username+"','"+metodo+"')";
		
		try {
			System.out.println("Sto per inserire: "+username+" "+metodo);
			st = connessione.createStatement(); 

			st.execute(queryInsert);

			st.executeUpdate("UPDATE utente SET ultimoaccesso = CURRENT_TIMESTAMP WHERE giocatore = '"+username+"'");
			
		} catch(SQLIntegrityConstraintViolationException e){
			System.out.println("Utente già loggato");
			return true;
		} catch (SQLException e) {

		} finally {
			try {
				st.close();
			} catch (SQLException e) {

			}
		}
		return false;
		
	}
	
	/**
	 * Conta il numero di utenti online
	 *
	 * @return il numero di giocatori che sono online
	 */
	public Integer numOnlineUser(){
	
		int numeroOnline = 0;
		ResultSet risultatoquery = null;
		String queryNum = "SELECT giocatore FROM giocatorionline";
		Statement st = null;
		
		try {
			st = connessione.createStatement();
			risultatoquery = st.executeQuery(queryNum);
			while(risultatoquery.next()){
				numeroOnline++;
			}
		} catch (SQLException e) {
			System.out.println("Errore query conteggio giocatori online");
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				//bisogna decidere cosa fargli fare
			}
		}
		
		return numeroOnline;
	}
}
			
			
			
			
			
		
			
		
			


