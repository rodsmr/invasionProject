package partita;
//ciao
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import login.GestioneFinestre;
import mappa.Mappa;

public class GestioneComandiPartita implements Runnable {

	//Avr� un socket che dovr� essere passato, legge il comando e lo elabora
	private String territorioPerMossa = "null"; //territorioAttaccante
	private int armateDifesa = 0; //pedineDifensore
	private String territorioDaDifendere = "null"; //territorioDifensore
	private int pedine = 0; //pedineAttaccante
	
	private Socket clientConnesso = null;
	private Mappa mappa = null;
	private String coloreUser = null;
	private String username = null;
	private PrintWriter scrittore = null;
	private BufferedReader reader = null;
	private ArrayList<String> indiceTerritori = null;
	private Partita partita = null;
	public String esito = "false";
	private String result = "null";
	
	private String resultFittizio = "null";
	
	public void setResultFittizio(String resultI){
		this.resultFittizio = resultI;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public void setMappa(Mappa mappa) {
		this.mappa = mappa;
	}
	
	public void setIndiceTerritori(ArrayList<String> indiceTerritori) {
		this.indiceTerritori = indiceTerritori;
	}
	
	public BufferedReader getReader() {
		return this.reader;
	}
	
	public void setPedine(int pedine) {
		System.out.println("Setting pedine: "+pedine);
		this.pedine = pedine;
	}
	
	public void setTerritorioPerMossa(String attacco) {
		System.out.println("Setting tA/S: "+attacco);
		this.territorioPerMossa = attacco;
	}
	
	public void setDifesa(String difesa) {
		System.out.println("Setting tD: "+difesa);
		this.territorioDaDifendere = difesa;
	}
	
	public void setColoreUser(String coloreUser) {
		this.coloreUser = coloreUser;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public void setVariabili(Socket clientConnesso, ArrayList<String> indiceTerritori,String coloreUser,String username,Partita partita, Mappa map) {
		this.clientConnesso = clientConnesso;
		this.indiceTerritori = indiceTerritori;
		this.coloreUser = coloreUser;
		this.username = username;
		this.partita = partita;
		this.mappa = map;
		
		if (clientConnesso != null) {
			try {
				reader = new BufferedReader(new InputStreamReader(clientConnesso.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {

		String comandoLetto = null;
		
		if(clientConnesso!=null) {
			try {
				//PrintWriter writer = new PrintWriter(clientConnesso.getOutputStream(),true);
				int i = 1;
				//PER COMANDO TERRITORI
				do{
					System.out.println("NUOVO COMANDO");
					comandoLetto = reader.readLine();
					System.out.println("COMANDO DA ESEGUIRE: "+comandoLetto);
					esito = eseguiComando(comandoLetto);
					System.out.println("ESITO FINE TERRITORI: "+esito);
					while(!reader.ready()){
						Thread.sleep(5000);
					}
				}
				while(indiceTerritori.size()!=0);		
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public String eseguiComando(String comandoDaEseguire){
			
		String inizioComando = comandoDaEseguire;
		String[] insiemeTerritori = null;
		int i = 0;
		int turnoFinito = 1;
	do{
		if (comandoDaEseguire.startsWith("territorio")) {
			inizioComando = "territorio";
		}
		
		else if (comandoDaEseguire.startsWith("posizionamento")){
			inizioComando = "posizionamento";
		}
		
		else if (comandoDaEseguire.startsWith("aggiorna")){
			inizioComando = "aggiorna";
		}
		else if (comandoDaEseguire.startsWith("turno")) {
			inizioComando = "turno";
		}
		else if (comandoDaEseguire.startsWith("attaccabili")) {
			inizioComando = "attaccabili";
		}
		else if (comandoDaEseguire.startsWith("difesa")) {
			inizioComando = "difesa";
		}
		else if (comandoDaEseguire.startsWith("spostabili")) {
			inizioComando = "spostabili";
		}
		else if (comandoDaEseguire.startsWith("ricarica")){
			inizioComando = "ricarica";
		}
			switch (inizioComando) {
	
				case "territorio":{
					insiemeTerritori = new String[15]; //dovrebbe aver dimensione massima 14 (#player = 3), 15 per safety
					comandoDaEseguire = comandoDaEseguire.substring(11);
					//System.out.println("Comando dopo sub: "+comandoDaEseguire);
					insiemeTerritori = comandoDaEseguire.split(";");
					for (i = 0; i < insiemeTerritori.length; i++) {
						if (indiceTerritori.add(insiemeTerritori[i])) {
							esito = "true";
						}
						else {
							esito = "false";
						}
					}
					System.out.println("GCP, mappa -> "+mappa);
					if(esito.equals("true")){
						mappa.coloraMappa(indiceTerritori);
						mappa.getFinestraMappa().setVisible(true);
						mappa.setTerritoriGiocata();
						mappa.setColoreAttaccante(coloreUser);
					}
					break;
				}
				case "ricarica":{
					mappa.resetMosse();
					ArrayList<JTextField> caselle = new ArrayList<JTextField>();
					System.out.println("----> RICARICA <-----");
					caselle = mappa.getCaselle();
					Iterator<String> itTerr = indiceTerritori.iterator();
					Iterator<JTextField> itCaselle = caselle.iterator();
					String tempTerr = null;
					JTextField tempCas = null;
					while(itTerr.hasNext()){
						tempTerr = itTerr.next();
						itCaselle = caselle.iterator();
						while(itCaselle.hasNext()){
							tempCas = itCaselle.next();
							System.out.println("TEMPCAS: "+tempCas.getName()+"\t TEMPTERR: "+tempTerr);
							if(tempTerr.equals(tempCas.getName())){
								tempCas.setEditable(true);
							}
						}
					}
					comandoDaEseguire = comandoDaEseguire.substring(9);
					String[] vecchiaPosizione = mappa.getPedine();
					JOptionPane.showMessageDialog(mappa.getFinestraMappa(), "Armate bonus: "+comandoDaEseguire);
					mappa.setArmate(Integer.parseInt(comandoDaEseguire),vecchiaPosizione);
					System.out.println("ASPETTA CHE IL PULSANTE LO SBLOCCHI");
					
					while(!esito.equals("true")){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					turnoFinito = 0;
					comandoDaEseguire = "turno";
					break;
				}
				case "turno": {
					mappa.resetDadi();
					mappa.resetMosse();
					System.out.println("--> TURNO <--");
					territorioPerMossa = "null";
					System.out.println("ARRIVA");
					mappa.interazioneMosse();
					result = mappa.territorioDaAttaccare(this);		//setta la classe comandi in mappa
					while (!(territorioPerMossa.startsWith("at") || territorioPerMossa.startsWith("sp") || territorioPerMossa.startsWith("fi"))) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					System.out.println("--> FINE ATTACCA: "+territorioPerMossa+"<--");
					esito = territorioPerMossa;
					break;
				}
				case "posizionamento":{
					System.out.println("GESTIONE COMANDI: POSIZIONAMENTO");
					Integer numeroArmate = 0;
					esito = "false";			
					
					comandoDaEseguire = comandoDaEseguire.substring(15);
					numeroArmate = Integer.parseInt(comandoDaEseguire);
					mappa.setArmate(numeroArmate);
					result = mappa.posizionaArmate(this);
					while(!esito.equals("true")){
						try {
							//System.out.println("BLOCCATO: "+esito);
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (clientConnesso == null)
						esito = result;
					System.out.println("USCITO DA POSIZIONAMENTO con esito: "+esito);
					break;
				}			
				case "aggiorna":{
					System.out.println("GESTIONE COMANDI: AGGIORNA");
					
					comandoDaEseguire = comandoDaEseguire.substring(9);
					esito = mappa.aggiorna(comandoDaEseguire);
					break;
				}
				
				case "attaccabili": {
					//QUESTO � il mio ATTACCO
					System.out.println("-->TERRITORI ATTACCABILI<--");
					comandoDaEseguire = comandoDaEseguire.substring(12);
					System.out.println(comandoDaEseguire);
					mappa.setAttaccoA(comandoDaEseguire);
					pedine = 0;
					while (pedine == 0)  {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("--> TERRITORI ATTACCABILI: "+result+"<--");
					
					scrittore.println("attacco@"+territorioPerMossa.substring(8)+";"+territorioDaDifendere+";"+pedine);
					scrittore.flush();
	
					try {
						while (!reader.ready()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						result = reader.readLine();
						//dado@attacco:a1;a2;a3;#difesa:b1;b2;
						System.out.println("Result FROM attacca: "+result);
						
						result = result.substring(5);
						String[] risultatiDadi = result.split("#");
						String[] valoriDadi = risultatiDadi[0].split(":");
						String[] dadiAttacco = valoriDadi[1].split(";");
						valoriDadi = risultatiDadi[1].split(":");
						String[] dadiDifesa = valoriDadi[1].split(";");
							
						int tempDado = 0;
						int h = 0;
						int k = 0;
	
							//Ordino attacca
						for (h = 0; h < (dadiAttacco.length)-1; h++) {
							for (k = h+1; k < dadiAttacco.length; k++) {
								if (Integer.parseInt(dadiAttacco[h]) < Integer.parseInt(dadiAttacco[k])) {
									tempDado = Integer.parseInt(dadiAttacco[h]);
									dadiAttacco[h] = dadiAttacco[k];
									dadiAttacco[k] = new Integer(tempDado).toString();
								}
							}
						}
							
							//Ordino difesa
						for (h = 0; h < (dadiDifesa.length)-1; h++) {
							for (k = h+1; k < dadiDifesa.length; k++) {
								if (Integer.parseInt(dadiDifesa[h]) < Integer.parseInt(dadiDifesa[k])) {
									tempDado = Integer.parseInt(dadiDifesa[h]);
									dadiDifesa[h] = dadiDifesa[k];
									dadiDifesa[k] = new Integer(tempDado).toString();
								}
							}
						}
						mappa.valoreDadi(dadiAttacco, dadiDifesa);
							
						//Stampo valori
						for (h = 0; h < dadiAttacco.length; h++) {
							System.out.print(dadiAttacco[h]+"\t");
						}
						System.out.println();
						for (h = 0; h < dadiDifesa.length; h++) {
							System.out.print(dadiDifesa[h]+"\t");
						}
	
						System.out.println();
							//Scrivo al server
						scrittore.println("dado@true");
						scrittore.flush();
	
							//Attesa esitoAttacco
						while (!reader.ready()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						result = reader.readLine();
							
						inizioComando = "esitoattacco";
						result = result.substring(13);
						if (result.equalsIgnoreCase("gameover")) {
								JOptionPane.showMessageDialog(mappa.getFinestraMappa(), "MORTO. GAME OVER");
								//distingure tra Socket & RMI
								new GestioneFinestre().setCorrectWindow(clientConnesso, null, "login", mappa.getFinestraMappa(), "socket", null, null, null);
						}
						else {
							String[] esitiAttacco = result.split("#");
							String[] tempPedine = esitiAttacco[1].split(";");
									
							esito = mappa.esitoAttacco("attacco", esitiAttacco[0], Integer.parseInt(tempPedine[0]), Integer.parseInt(tempPedine[1]), coloreUser);
								
							//devo aggiornare i territori
							if (esito.equals("true")) {
								indiceTerritori.add(territorioDaDifendere);
							}
									
							mappa.resetDadi();
							mappa.resetMosse();
							mappa.setTerritoriGiocata();
							
							inizioComando = "fineattacco";
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "difesa": {
					//QUESTA � la mia difesa
					comandoDaEseguire = comandoDaEseguire.substring(7);
					String[] temp = new String[3];
					temp = comandoDaEseguire.split(";");
					int oldArmateDifesa = mappa.difenditi(Integer.parseInt(temp[2]));
					armateDifesa = mappa.checkValiditaArmateDifesa(oldArmateDifesa, temp[1]);
					
					if (oldArmateDifesa != armateDifesa) 
						JOptionPane.showMessageDialog(mappa.getFinestraMappa(), "Avevi selezionato "+oldArmateDifesa+": troppe. Default difesa: "+armateDifesa, "Default difesa", JOptionPane.WARNING_MESSAGE);
					
					System.out.println("Mi difendo con: "+armateDifesa+" armate");
					System.out.println("COLORE DIFENSORE: "+coloreUser);
					mappa.setColoreDifensore(coloreUser);
					
					scrittore.println(armateDifesa);
					scrittore.flush();
					
					//comincia difesa
					
					try {
						while (!reader.ready()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						result = reader.readLine();
						//dado@attacco:a1;a2;a3;#difesa:b1;b2;
						System.out.println("Result FROM difesa: "+result);
						
						result = result.substring(5);
						String[] risultatiDadi = result.split("#");
						String[] valoriDadi = risultatiDadi[0].split(":");
						String[] dadiAttacco = valoriDadi[1].split(";");
						valoriDadi = risultatiDadi[1].split(":");
						String[] dadiDifesa = valoriDadi[1].split(";");
						
						int tempDado = 0;
						int h = 0;
						int k = 0;
	
						//Ordino attacca
						for (h = 0; h < (dadiAttacco.length)-1; h++) {
							for (k = h+1; k < dadiAttacco.length; k++) {
								if (Integer.parseInt(dadiAttacco[h]) < Integer.parseInt(dadiAttacco[k])) {
									tempDado = Integer.parseInt(dadiAttacco[h]);
									dadiAttacco[h] = dadiAttacco[k];
									dadiAttacco[k] = new Integer(tempDado).toString();
								}
							}
						}
							
						//Ordino difesa
						for (h = 0; h < (dadiDifesa.length)-1; h++) {
							for (k = h+1; k < dadiDifesa.length; k++) {
								if (Integer.parseInt(dadiDifesa[h]) < Integer.parseInt(dadiDifesa[k])) {
									tempDado = Integer.parseInt(dadiDifesa[h]);
									dadiDifesa[h] = dadiDifesa[k];
									dadiDifesa[k] = new Integer(tempDado).toString();
								}
							}
						}
							
						mappa.valoreDadi(dadiAttacco, dadiDifesa);
						
						//Stampo valori
						for (h = 0; h < dadiAttacco.length; h++) {
							System.out.print(dadiAttacco[h]+"\t");
						}
						System.out.println();
						for (h = 0; h < dadiDifesa.length; h++) {
							System.out.print(dadiDifesa[h]+"\t");
						}
						System.out.println();
						//Scrivo al server
						scrittore.println("dado@true");
						scrittore.flush();
	
						//Attesa esitoAttacco
						while (!reader.ready()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						result = reader.readLine();
							
						inizioComando = "esitoattacco";
						result = result.substring(13);
						String[] esitiAttacco = result.split("#");
						String[] tempPedine = esitiAttacco[1].split(";");
							
						esito = mappa.esitoAttacco("difesa", esitiAttacco[0], Integer.parseInt(tempPedine[0]), Integer.parseInt(tempPedine[1]), coloreUser);
								
						if (esito.equals("true")) {
							indiceTerritori.remove(territorioDaDifendere);
						}
						
							
						mappa.resetDadi();
						inizioComando = "fineattacco";					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "spostabili": {
					System.out.println("--> SPOSTO I MIEI TERRITORI<--");
					comandoDaEseguire = comandoDaEseguire.substring(11);
					mappa.setTerritoriGiocata();
					mappa.setAttaccoA(comandoDaEseguire);
					pedine = 0;
					while (pedine == 0)  {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("--> TERRITORI SPOSTABILI: "+result+"<--");
					
					JOptionPane.showMessageDialog(mappa.getFinestraMappa(), "Fine turno!");
					
					break;
				}
				case "noSpostabili": {
					JOptionPane.showMessageDialog(mappa.getFinestraMappa(), "Nessuno stato raggiungibile, nuova operazione", "Errore selezione stato", JOptionPane.WARNING_MESSAGE);
					result = "null";
					territorioPerMossa = "null";
					mappa.resetMosse();
					mappa.reSetBottoneSposta(); //setto solo il bottone per spostare
					mappa.setTerritoriGiocata(); //setto il menu AttaccoDa
					result = mappa.territorioDaAttaccare(this);
					while (!territorioPerMossa.startsWith("sp")) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					System.out.println("--> FINE NOSPOSTABILI: "+territorioPerMossa+"<--");
					
					esito = territorioPerMossa;
					
					break;
				}
			}
		//SOCKET
		if(clientConnesso != null){
			try {
				setScrittore(new PrintWriter(clientConnesso.getOutputStream(), true));
				switch (inizioComando) {
					case "fineattacco":{
						esito = "attaccoconcluso";
						System.out.println("HO FINITO L'ATTACCO");
						getScrittore().println(esito);
						getScrittore().flush();
						break;
					}
				
					case "turno": {
						System.out.println("Case turno, scrivo: "+esito);
						getScrittore().println(esito);
						getScrittore().flush();					
						break;
					}
					case "attaccabili": {
						System.out.println("Case attaccabili, scrivo: ");
						System.out.println("attacco@"+territorioPerMossa.substring(8)+";"+territorioDaDifendere+";"+pedine);
						getScrittore().println("attacco@"+territorioPerMossa.substring(8)+";"+territorioDaDifendere+";"+pedine);
						getScrittore().flush();	
						break;
					}
					case "difesa": {
						System.out.println("Case difesa, scrivo: "+armateDifesa);						
						getScrittore().println(armateDifesa);
						getScrittore().flush();	
						break;
					}
					case "posizionamento": {
						break;
					}
					case "aggiorna": {
						break;
					}
					case "ricarica":{
						break;
					}
					case "spostabili": {
						System.out.println("Case spostabili, scrivo: "+"sposta@"+territorioDaDifendere+";"+pedine);
						getScrittore().println("sposta@"+territorioDaDifendere+";"+pedine);
						getScrittore().flush();
						break;
					}
					default: {
						System.out.println("Case default, scrivo: "+inizioComando+"@"+esito);
						getScrittore().println(inizioComando+"@"+esito);
						getScrittore().flush();		
						break;
					}
				}		
			} catch (IOException e) {
				getScrittore().println(inizioComando+"@false");
				getScrittore().flush();
				System.out.println("GESTCOMPARTITA: writer error: "+e.getMessage());
			}
		}
		else {
			switch (inizioComando) {
				case "posizionamento": {
					return result;
				}
				case "turno": {
					System.out.println("Turno --> "+territorioPerMossa);
					return territorioPerMossa;
				}
				case "spostabili": {
					System.out.println("Spostabili --> "+result);
					return result;
				}
				case "noSpostabili": {
					System.out.println("No-Spostabili --> "+territorioPerMossa);
					return territorioPerMossa;					
				}
				case "ricarica": {
					return resultFittizio;
				}
			}
		}
		}while(turnoFinito == 0);
		System.out.println("Ritorno per RMI: "+esito);
		return esito;
	}
	
	public String whoWin(String[] dadiAttacco, String[] dadiDifesa) {
		
		String vincitore = null;
		int i = 0;
		int j = 0;
		System.out.println("Pedine: "+pedine+" Armate difesa: "+armateDifesa);
		
		while (i < pedine && j < armateDifesa && !(vincitore.equals("attaccante"))) {
			
			if (Integer.parseInt(dadiAttacco[i]) > Integer.parseInt(dadiDifesa[j])) {
				vincitore = "attaccante";
			}
			else {
				vincitore = "difensore";
			}
			
			i++;
			j++;
		}
		return vincitore;
	}
	
	
	/**
	 * @return the scrittore
	 */
	public PrintWriter getScrittore() {
		return scrittore;
	}

	/**
	 * @param scrittore the scrittore to set
	 */
	public void setScrittore(PrintWriter scrittore) {
		this.scrittore = scrittore;
	}

	public Socket getSocket() {
		return this.clientConnesso;
	}


}
