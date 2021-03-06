package partita;

import interfacceComunicazione.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;


public class Giocatore {

	private String username = null;
	private Socket client = null;
	private Client clientInt = null;
	private String colore = null;
	private String azionePartita = null;
	private ArrayList<Territorio> territori = null;
	private Integer numeroArmate = 0;
	private int perso = 0;
	private ArrayList<Territorio> spostabili = null;
	private Iterator<Giocatore> itGioc = null;
	private Iterator<Territorio> itTerr = null;
	private Giocatore supGioc = null;
	private Territorio supTerr = null;
	private int bonus = 0;
	
	
	PrintWriter writer = null;
	BufferedReader reader = null;
	
	//Se sto comunicando con socket
	public Giocatore(String username, Socket client){
		this.username = username;
		this.client = client;
		this.territori = new ArrayList<Territorio>();
		
		//non lo vorrei qua
		try {
			writer = new PrintWriter(client.getOutputStream(),true);
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			writer = null;
			reader = null;
		}
	}
	
	//Se sto comunicando con RMI --> dovr� poter fare una callback();
	public Giocatore(String username, Client clientInt) {
		this.username = username;
		this.clientInt = clientInt;
		this.territori = new ArrayList<Territorio>();
		System.out.println("Giocatore: create RMI: "+clientInt);
	}

	public Giocatore(String username) {
		// TODO Auto-generated constructor stub
		this.username = username;
	}

	public String inviaTerritori() {
		
		String esitoOperazione = null;
		
		System.out.println("Mi trovo nel metodo inviaTerritori");
		String territorio = "territorio@";
		Iterator<Territorio> it = territori.iterator();
		
		System.out.println("Valore territorio in inviaTerritori da "+username);
		while(it.hasNext()){
			//System.out.println(it.next().toString());
			//territorio.concat(it.next().toString());
			territorio = territorio + it.next().getCodice() + ";";
		}
		
		System.out.print("Territorio da inviare: ");
		System.out.println(territorio);	
		
		//SOCKET
		if (client != null) {	
			
			try {
				writer.println(territorio);
				writer.flush();
				
				esitoOperazione = reader.readLine();
				
				if (esitoOperazione.endsWith("true")) {
					esitoOperazione = "true";
				}
				else {
					esitoOperazione = "false";
				}
				System.out.println("-->ESITO TERRITORI: "+esitoOperazione);

			} catch (IOException e) {
				System.out.println("Problema writer inviaTerritori: "+e.getMessage());
			}		
		}
		//RMI
		else if (clientInt != null) {
			try {
				System.out.println("SONO IN GIOCATORE PER INVIO TERRITORIO CON RMI");
				esitoOperazione = clientInt.comandoDaEseguire(territorio);
				//dovr� leggere lo stato di questa variabile
			} catch (RemoteException e) {
				System.out.println("Giocatore problema territori: "+e.getMessage());
			}
		}
		
		return esitoOperazione;
	}
	
	public void visualizzaterritori(){
		System.out.println("Utente: "+username);
		Iterator<Territorio> it = territori.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
	}
	
	public void setTerritori(Territorio territorio){
		this.territori.add(territorio);
	}
	
	public String getUser(){
		return this.username;
	}
	
	public Socket getSocket(){
		return this.client;
	}
	
	public Client getClientInt() {
		return this.clientInt;
	}
	
	//Setto il comando che dovr� inviare al Client. Richiamato in Partita#
	public void setAzioneComando(String comando){		
		this.azionePartita = comando;
	}

	
	//Eseguo il comando settato da Partita#
	public String eseguiComandi(String[] colori, Partita partita) throws IOException {
			
		String inizioComando = null;
		
		if(azionePartita.equals("colore")){
			inizioComando = "colore";
		}
		
		if(azionePartita.startsWith("attacca")){
			inizioComando = "attacca";
		}
		
		if(azionePartita.startsWith("sposta")){
			inizioComando = "sposta";
		}
		
		if(azionePartita.startsWith("fine")) {
			inizioComando = "fine";
		}
		
		if(azionePartita.startsWith("ricarica")){
			inizioComando = "ricarica";
		}
			switch(inizioComando) {
				case "colore":{	
					if (client != null) {
						
						System.out.println(azionePartita+"@"+colori[0]+colori[1]+colori[2]+colori[3]+colori[4]+colori[5]);
						writer.println(azionePartita+"@"+colori[0]+colori[1]+colori[2]+colori[3]+colori[4]+colori[5]);
						writer.flush();

						System.out.println("Aspetta la risposta dal client.");
						setColore(reader.readLine());
						System.out.println("Il client mi ha risposto");
					
					}
					else if (clientInt != null) {
						System.out.println("Invio colore RMI");
						//Mando i colori possibili
						setColore(clientInt.comandoDaEseguire(azionePartita+"@"+colori[0]+colori[1]+colori[2]+colori[3]+colori[4]+colori[5]));
						System.out.println("Colore scelto da "+username+" e' : "+colore);
						//Dovr� attendendere una sua risposta e la salver� in colore...
						//� il modo giusto per fare ci�? Parlane con Antonio
						
					}
					
					for(int i = 0; i < colori.length; i++){
						if(colore.equals(colori[i])){
							this.colore = colore;
							colori[i] = "";
						}
					}
					System.out.println("Colore scelto da "+username+" e' : "+colore);
					
					break;
				}
				
				case "attacca":{
					
					if(client!=null){
						ArrayList<Giocatore> giocatori = new ArrayList<Giocatore>();
						Iterator<Giocatore> iteratore = giocatori.iterator();
						String attaccante = null;
						String attaccabili = null;
						String risposta = null;
						String attaccato = null;
						String numeroPedine = null;
						String[] supporto = new String [3];
						String userDifesa = null;
						Iterator<Territorio> iteratoreTerr = null;
						Socket difesa = null;
						PrintWriter writerDifesa = null;
						BufferedReader readerDifesa = null;
						Territorio terrAttaccato = null;
						Territorio terrAttaccante = null;
						Dado dado = new Dado();
						
						System.out.println("-----> SONO IN ATTACCA <-----");
						do{
							attaccante = azionePartita.substring(8);
							System.out.println("VALORE DI ATTACCANTE: "+attaccante);						
							attaccabili = getTerritoriAttaccabili(attaccante);
						
							System.out.println("TERRITORI ATTACCABILI TORNATI: "+attaccabili);
						
							writer.println(attaccabili);
							writer.flush();
							
							System.out.println("FINE SCRITTURA");
							if(attaccabili.equals("attaccabili@")){
								System.out.println("PRIMA DI RISPOSTA");
								risposta = reader.readLine();
								System.out.println("VALORE DI RISPOSTA: "+risposta);
								azionePartita = risposta;
								System.out.println("VALORE DI AZIONEPARTITA: "+azionePartita);
							}
						}while(azionePartita.startsWith("attacco"));
						
						risposta = reader.readLine();
						
						System.out.println("RISPOSTA ATTACCO");
						risposta = risposta.substring(8);
						System.out.println("VALORE DI RISPOSTA: "+risposta);
						supporto = risposta.split(";");
						attaccante = supporto[0];
						attaccato = supporto[1];
						numeroPedine = supporto[2];
						
						
						System.out.println("ATTACCANTE: "+attaccante+"\t ATTACCATO: "+attaccato+"\t PEDINE: "+numeroPedine);
						
						giocatori = partita.getGiocatore();
						iteratore = giocatori.iterator();
						
						while(iteratore.hasNext() && terrAttaccante == null){
							supGioc = iteratore.next();
							iteratoreTerr = supGioc.territori.iterator();
							while(iteratoreTerr.hasNext() && terrAttaccante == null){
								supTerr = iteratoreTerr.next();
								if(supTerr.getCodice().equals(attaccante)){
									terrAttaccante = supTerr;
								}
							}
							
						}
						
						iteratore = giocatori.iterator();
						
						while(iteratore.hasNext() && userDifesa == null){
							supGioc = iteratore.next();
							iteratoreTerr = supGioc.territori.iterator();
							while(iteratoreTerr.hasNext() && userDifesa == null){
								supTerr = iteratoreTerr.next();
								System.out.println("USER: "+supTerr.getUser());
								if(supTerr.getCodice().equals(attaccato)){
									userDifesa = supTerr.getUser();
									terrAttaccato = supTerr;
								}	
							}
						}
						System.out.println("NOME DEL DIFENSORE: "+userDifesa);
						
						iteratore = giocatori.iterator();
						Giocatore difensore = null;
						
						while(iteratore.hasNext() && difesa == null){
							supGioc = iteratore.next();
							if(supGioc.username.equals(userDifesa)){
								difesa = supGioc.getSocket();
								difensore = supGioc;
							}
						}
						
						risposta = "difesa@"+supporto[0]+";"+supporto[1]+";"+supporto[2]+";";
						writerDifesa = new PrintWriter(difesa.getOutputStream(),true);
						readerDifesa = new BufferedReader(new InputStreamReader(difesa.getInputStream()));
						
						writerDifesa.println(risposta);
						writerDifesa.flush();
						
						
						String difesaPedine = readerDifesa.readLine();
						
						System.out.println("DIFESAPEDINE: "+difesaPedine);
						Integer[] dadoAttacca = new Integer[3];
						Integer[] dadoDifesa = new Integer[2];
						
						String responso = "#difesa:";
						risposta = "dado@attacco:";
						
						for(int i = 0; i < 3; i++){
							if(i < Integer.parseInt(numeroPedine)){
								dadoAttacca[i] = dado.lanciaDado();
								risposta+=dadoAttacca[i]+";";
							}
							else{
								risposta+="0;";
							}
						}
						risposta += responso;
						for(int i = 0; i < 2; i++){
							if(i < Integer.parseInt(difesaPedine)){
								dadoDifesa[i] = dado.lanciaDado();
								risposta+=dadoDifesa[i]+";";
							}
							else{
								risposta+="0;";
							}
						}			
						
						System.out.println("RISCONTRO DADI: "+risposta);
						writer.println(risposta);
						writer.flush();
						writerDifesa.println(risposta);
						writerDifesa.flush();
						
						risposta = readerDifesa.readLine();
						System.out.println("RispostaDifesa -> "+risposta);
						
						risposta = reader.readLine();
						System.out.println("RispostaAttacco -> "+risposta);
						
						String[] vincitore = vince(dadoAttacca,dadoDifesa,numeroPedine,difesaPedine);
						risposta = "esitoattacco@";
						for(int i = 0; i < Integer.parseInt(difesaPedine); i++){
							risposta+=vincitore[i]+";";
						}
						risposta = risposta + "#"+numeroPedine+";"+difesaPedine+";";
						
						System.out.println("RISULTATO ATTACCO: "+risposta);
						
						int vittorieAttacco = 0;
						
						for(int i = 0; i < Integer.parseInt(difesaPedine); i++){
							if(vincitore[i].equals("attacco")){
								vittorieAttacco++;
							}
						}
						
						if(vittorieAttacco==0){
							terrAttaccante.setArmateTerritorio(terrAttaccante.getNumArmate()-Integer.parseInt(difesaPedine));
						}
						else if(vittorieAttacco==1){
							if(difesaPedine.equals("1")){
								terrAttaccato.setArmateTerritorio(terrAttaccato.getNumArmate()-1);
							}
							else{
								terrAttaccato.setArmateTerritorio(terrAttaccato.getNumArmate()-1);
								terrAttaccante.setArmateTerritorio(terrAttaccante.getNumArmate()-1);
							}
						}
						else{
							terrAttaccato.setArmateTerritorio(terrAttaccato.getNumArmate()-Integer.parseInt(difesaPedine));
						}
						if(terrAttaccato.getNumArmate()<=0){
							int pedine = Integer.parseInt(numeroPedine) - Integer.parseInt(difesaPedine) + vittorieAttacco;
							terrAttaccato.setNewUser(username, pedine);
							this.territori.add(terrAttaccato);
							difensore.territori.remove(terrAttaccato);
						}
						
						System.out.println("PEDINE TERRITORIO ATTACCANTE: "+terrAttaccante.getNumArmate()+"\t USER: "+terrAttaccante.getUser());
						System.out.println("PEDINE TERRITORIO ATTACCATO: "+terrAttaccato.getNumArmate()+"\t USER: "+terrAttaccato.getUser());
						
						partita.getPosizioneTotale()[Integer.parseInt(terrAttaccante.getCodice())-1] = terrAttaccante.getNumArmate().toString();
						partita.getPosizioneTotale()[Integer.parseInt(terrAttaccante.getCodice())-1] = terrAttaccante.getNumArmate().toString();
						
						
						if(difensore.territori.size()==0){
							perso = 1;
							writer.println(risposta);
							writer.flush();
							writerDifesa.println("esitoattacco@gameover");
							writerDifesa.flush();
							partita.getGiocatore().remove(difensore);
							partita.getClassifica().add(difensore);
						}
						else{
							writer.println(risposta);
							writer.flush();
							writerDifesa.println(risposta);
							writerDifesa.flush();
						}
						
						System.out.println("STAMPA TERRITORI ATTACCANTE: ");
						iteratoreTerr = territori.iterator();
						while(iteratoreTerr.hasNext()){
							supTerr = iteratoreTerr.next();
							System.out.println(supTerr.getCodice()+"\t"+supTerr.getNumArmate());
						}
						iteratoreTerr = difensore.territori.iterator();
						System.out.println("STAMPA TERRITORI DIFENSORE: ");
						while(iteratoreTerr.hasNext()){
							supTerr = iteratoreTerr.next();
							System.out.println(supTerr.getCodice()+"\t"+supTerr.getNumArmate());
						}
						
						risposta = reader.readLine();
						risposta = readerDifesa.readLine();
						System.out.println("VALORE RISPOSTA FINE ATTACCO: "+risposta);
						
					}
					break;
				}
				case "ricarica":{
					String result = null;
					String[] territori = new String[42];
					
					if (client != null) {
						writer.println(azionePartita);
						writer.flush();
						
						result = reader.readLine();
						result = result.substring(11);
					}
					else {
						result = clientInt.comandoDaEseguire(azionePartita);
						System.out.println("Giocatore RMI, ricarica --> "+result);
					}
					
					territori = result.split(";");
					
					itTerr = this.getLista().iterator();
					while(itTerr.hasNext()){
						supTerr = itTerr.next();
						supTerr.setArmateTerritorio(Integer.parseInt(territori[Integer.parseInt(supTerr.getCodice())-1]));
						partita.getPosizioneTotale()[Integer.parseInt(supTerr.getCodice())-1] = territori[Integer.parseInt(supTerr.getCodice())-1];
						System.out.println("TERRITORIO: "+supTerr.getCodice()+ "\t ARMATE: "+supTerr.getNumArmate());
					}
					break;
				}
				
				case "sposta":{
					//SOCKET, RMI DA IMPLEMENTARE
					String startSposta = null; 
					spostabili = new ArrayList<Territorio>();
					ArrayList<Giocatore> giocatori = new ArrayList<Giocatore>();
					Territorio inizioSposta = null;
					Territorio fineSposta = null;
					String check = null;
					String result = null;
					
					do{
						inizioSposta=null;
						supTerr = null;
						System.out.println("VALORE AZIONEPARTITA: "+azionePartita);
						startSposta = azionePartita.substring(7);
						System.out.println("VALORE STARTSPOSTA: "+startSposta);
						azionePartita = "scherzone";
						System.out.println("VALORE AZIONEPARTITA: "+azionePartita);
						giocatori = partita.getGiocatore();
						itGioc = giocatori.iterator();			
						while(itGioc.hasNext()&&inizioSposta==null){
							supGioc = itGioc.next();
							itTerr = supGioc.territori.iterator();
							while(itTerr.hasNext() && inizioSposta==null){
								supTerr = itTerr.next();
								if(supTerr.getCodice().equals(startSposta)){
									System.out.println("VALORE DI STARTSPOSTA IN WHILE: "+startSposta);
									inizioSposta = supTerr;
								}
							}
							if(inizioSposta==null) System.out.println("INIZIOSPOSTA NULL");
						}
						System.out.println("PRIMA DI INSERIRE IN ARRAY INIZIOSPOSTA: "+inizioSposta.getCodice());
						spostabili.add(inizioSposta);
						ricercaSpostamenti(inizioSposta);
						spostabili.remove(inizioSposta);
						startSposta = "spostabili@";
						itTerr = spostabili.iterator();
						while(itTerr.hasNext()){
							supTerr = itTerr.next();
							startSposta+=supTerr.getCodice()+";";
							System.out.println("TERRITORIO: "+supTerr.getCodice()+"\t STRINGA: "+startSposta);
						}
						System.out.println("INVIO LA STRINGA: "+startSposta);
						
						//SOCKET comando: spostabili@[s1;s2]
						if (client != null) {
							writer.println(startSposta);
							writer.flush();
							
							System.out.println("VALORE DI SPOSTABILI: "+spostabili.size());
							if(spostabili.size()==0) {
								check = reader.readLine();
								System.out.println("VALORE DI CHECK IN IF: "+check);
								azionePartita = check;
							}
						}
						else {
							
							if (spostabili.size() == 0) {
								System.out.println("RMI: NESSUN TERRITORIO RAGGIUNGIBILE");
								//dovrei rifare turno, senza JOptionPane e attiva solo sposta
								result = clientInt.comandoDaEseguire("noSpostabili");
								while (!(result.startsWith("sposta"))) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								System.out.println("IF of RESULT --> "+result);
								azionePartita = result;
							}
							else {
								result = clientInt.comandoDaEseguire(startSposta);								
							}
							
						}
						spostabili.removeAll(territori);
					} while(azionePartita.startsWith("sposta"));
					
					itTerr = spostabili.iterator();
					System.out.println("TERRITORI RAGGIUNGIBILI: ");
					while(itTerr.hasNext()){
						supTerr = itTerr.next();
						System.out.println(supTerr.getCodice());
					}
					System.out.println("ARRIVA PRIMA DELLA LETTURA");
					
					//SOCKET
					if (client != null) {
						result = reader.readLine();
					}
					else {
						//RMI: gi� settata come valore di ritorno;
					}
					String supporto[] = new String[2];
					System.out.println("VALORE LETTO: "+result);
					result = result.substring(7);
					supporto = result.split(";");
					
					itTerr = territori.iterator();
					while(itTerr.hasNext()){
						supTerr = itTerr.next();
						System.out.println("VALORE DI SUPTERRCODICE: "+supTerr.getCodice()+"\t RISPOSTA: "+supporto[0]);
						if(supTerr.getCodice().equals(supporto[0])){
							fineSposta = supTerr;
						}
					}
					
					System.out.println("ARMATE TERRITORIO SORGENTE PRE SPOSTA: "+inizioSposta.getNumArmate());
					System.out.println("ARMATE TERRITORIO DESTINAZIONE PRE SPOSTA: "+fineSposta.getNumArmate());
					
					inizioSposta.setArmateTerritorio(inizioSposta.getNumArmate()-Integer.parseInt(supporto[1]));
					fineSposta.setArmateTerritorio(fineSposta.getNumArmate()+Integer.parseInt(supporto[1]));
										
					System.out.println("ARMATE TERRITORIO SORGENTE POST SPOSTA: "+inizioSposta.getNumArmate());
					System.out.println("ARMATE TERRITORIO DESTINAZIONE POST SPOSTA: "+fineSposta.getNumArmate());
					
					partita.getPosizioneTotale()[Integer.parseInt(inizioSposta.getCodice())-1] = inizioSposta.getNumArmate().toString();
					partita.getPosizioneTotale()[Integer.parseInt(fineSposta.getCodice())-1] = fineSposta.getNumArmate().toString();
					break;
				}
			}
			return inizioComando;
	}
	
	private void ricercaSpostamenti(Territorio startSposta) {
		
		Iterator<Territorio> confinanti = startSposta.getConfini().iterator();
		Territorio temp = null;
		
		while(confinanti.hasNext()){
			temp = confinanti.next();
			if(temp.getUser().equals(startSposta.getUser())){
				if(!spostabili.contains(temp)){
					spostabili.add(temp);
					ricercaSpostamenti(temp);
				}
			}
		}
	}

	private String[] vince(Integer[] dadoAttacca, Integer[] dadoDifesa, String numeroPedine, String difesaPedine){
		String vincitore[] = new String[2];
		int j = 0;
		int i = 0;
		int temp = 0;
			
		for(i = 0; i < Integer.parseInt(numeroPedine)-1; i++) {
			for (j = i+1; j < Integer.parseInt(numeroPedine); j++) {
				if(dadoAttacca[i]<dadoAttacca[j]){
					temp = dadoAttacca[i];
					dadoAttacca[i] = dadoAttacca[j];
					dadoAttacca[j] = temp;
				}
			}
		}
		
		for(i = 0; i < Integer.parseInt(difesaPedine)-1; i++) {
			for (j = i+1; j < Integer.parseInt(difesaPedine); j++) {
				if(dadoDifesa[i]<dadoDifesa[j]){
					temp = dadoDifesa[i];
					dadoDifesa[i] = dadoDifesa[j];
					dadoDifesa[j] = temp;
				}
			}
		}
		for(i = 0; i < Integer.parseInt(difesaPedine); i++){
			System.out.println("DADI DA CONFRONTARE: ATTACCO: "+dadoAttacca[i]+" \t DIFESA: "+dadoDifesa[i]);
			if(dadoAttacca[i]>dadoDifesa[i]){
				vincitore[i] = "attacco";
			}
			else {
				vincitore[i] = "difesa";
			}
		}
	return vincitore;
	}
			
			
			
			
			
			
	
	private String getTerritoriAttaccabili(String territorioAttaccante) {
		
		ArrayList<Territorio> confini = new ArrayList<Territorio>();
		Iterator<Territorio> terr = territori.iterator();
		Territorio supporto = null;
		String attaccabili = "attaccabili@";
		
		
		System.out.println("VALORE ATTACCNTE IN GETTERRITORI: "+territorioAttaccante);
		while(terr.hasNext()){
			supporto = terr.next();
			if(supporto.getCodice().equals(territorioAttaccante)){
				confini = supporto.getConfini();
			}
		}
		System.out.println("DOPO IL CICLO.. CONFINI: "+confini.toString());
		
		terr = confini.iterator();
		
		while(terr.hasNext()){
			supporto = terr.next();
			if(!supporto.getUser().equals(username))
				attaccabili += supporto.getCodice()+";";
		}
		
		System.out.println("STRINGA DEI TERRITORI ATTACCABILI DA: "+territorioAttaccante+" SONO: "+attaccabili);
		
		return attaccabili;
	}

	
	
	
	
	
	
	
	
	public ArrayList<Territorio> getLista(){
		return this.territori;
	}

	public void setArmate(int numeroArmate) {
		this.numeroArmate = numeroArmate;		
	}
	
	public Integer getArmate(){
		return this.numeroArmate;
	}
	
	public void posiziona(){
		//gestione del socket per il posizionamento... MANCA LA PARTE RMI
		if(client != null){
			
		}
		
	}


	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}
	
	public int getPerso(){
		return this.perso;
	}

	public String turno() {
		
		String comandoDaEseguire = null;
		//SOCKET
		if (client != null) {
			writer.println("turno");
			writer.flush();
		
			try {
				comandoDaEseguire = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//RMI
		else if (clientInt != null) {
			try {
				comandoDaEseguire = clientInt.comandoDaEseguire("turno");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return comandoDaEseguire;		
	}

}
