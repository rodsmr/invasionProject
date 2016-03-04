package analisiComandi;

/**
 * Classe per la gestione dei login con caratteri speciali
 */
public class AnalisiLogin {

	/**
	 * Sostituzione dei caratteri speciali
	 *
	 * @param username dell'utente
	 * @return restituisce la stringa modificata
	 */
	public String inviaUser(String user) {
		user = user.replaceAll("#", "§#");
		return user.replaceAll(";", "#;");
	}
	
	/**
	 * Metodo per cambiare i caratteri speciali della psw
	 *
	 * @param password utente
	 * @return la password con i caratteri speciali cambiati
	 */
	public String inviaPW(String pw) {
		return pw.replaceAll(";", "#;");
	}
	
	/**
	 * Metodo per riconoscere se il login è stato fatto correttamente o meno
	 *
	 * @param esito del login
	 * @return true o l'errore a seconda se il login è stato effettuato correttamente
	 */
	public String interpretazioneEsitoLogin(String esito) {
		
		if (esito.startsWith("ok@")) {
			esito = "true";
		}
		else if (esito.startsWith("ko@")) {
			esito = esito.substring(3);
		}
		
		return esito;
	}	
	
	/**
	 * Decodifica user.
	 *
	 * @param comando è l'user che dovrà subire la decodifica
	 * @param indica la posizione del separatore nella stringa di login
	 * @return restituisce l'user originario
	 */
	public String decodificaUser(String comando, int posizioneSeparatore) {

		int i = 0;
		String user = "";
		
		for (i = 0; i < posizioneSeparatore; i++) {
			user += comando.charAt(i);
		}
		user = user.replaceAll("#;", ";");
		return user.replaceAll("§#", "#");
	}
	
	/**
	 * Decodifica pw.
	 *
	 * @param password modificata
	 * @param indica posizione del separatore
	 * @return la password corretta (quella inserita dall'utente)
	 */
	public String decodificaPW(String comando, int posizioneSeparatore) {

		int i = posizioneSeparatore + 1;
		String pw = "";
		
		for (i = posizioneSeparatore + 1; i < comando.length(); i++) {
			pw += comando.charAt(i);
		}
		
		return pw.replaceAll("#;", ";");
	}
	
	/**
	 * Stabilisce la posizione del separatore
	 *
	 * @param comando del quale determinare la posizione del separatore
	 * @return la posizione del separatore
	 */
	public int posizioneSeparatore(String comando) {
		
		int posizioneSep = 0;
		int i = 0;
		int primaAt = comando.indexOf('@');
		String utente = comando;
		
		//Prendo posizioneSeparatore
		for (i = 0; i < utente.length(); i++) {
			if (utente.charAt(i) == ';') {
				if (utente.charAt(i-1) == '#') {
					if (utente.charAt(i-2) == '§') {
						posizioneSep = i;
					}
				}
				else {
					posizioneSep = i;
				}
			}
		}
		return posizioneSep;
		
	}
	
}
