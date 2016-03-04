package partita;

import interfacceComunicazione.Client;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import comunicazione.GestioneDatabase;
import prepartita.Prepartita;
import login.TaskGestioneComandi;

public class PartitaSei extends Partita implements Runnable {

	private GestioneDatabase gestDatabase = null;
	private ArrayList<Giocatore> classifica = null;
	private	final int numGiocatori = 6;
	private	final int punteggio1 = 40;
	private	final int punteggio2 = 10;
	private ArrayList<Giocatore> giocatori = null;
	private  String[] colori = new String[6];
	private final Integer armateGioc = 40;
	private Object lock = null;
	private Client clientInt = null;
	public String[] posizioneTotale = null;
	public int contatore = 0;
	private Mappa map = null;
	
	public ArrayList<Giocatore> istance6(){
		classifica = new ArrayList<Giocatore>();
		lock = new Object();
		if(giocatori == null)
			giocatori = new ArrayList<Giocatore>();
		return giocatori;
	}

	private void setColori(){
		colori[0] = "azzurro";
		colori[1] = "verde";
		colori[2] = "giallo";
		colori[3] = "bianco";
		colori[4] = "magenta";
		colori[5] = "arancio";
	}

	@Override
	public void run() {
		map = new Mappa();
		Giocatore user = null;
		map.creaMappa();
		ArrayList<Territorio> lista = map.getLista();
		String result = null;
		gestDatabase = new GestioneDatabase();
		int ricaricaPedine = 0;
		
		if(inserisciGiocatori()){
			Iterator<Giocatore> it = giocatori.iterator();

			assegnaColore();
			System.out.println("Sono in run pre assegnaTerritori");
			result = assegnaTerritori(lista);
			System.out.println("VALORE DI ASSEGNATERRITORI IN PARTITA6: "+result);
			if (posizionaArmate().equals("true")) {
				try {
					while(contatore != 6){
						System.out.println("VALORE CONTATORE: "+contatore);
						Thread.sleep(5000);
					}
					visualizzaTerritori();
					//RMI OK
					contatore = 0;
					aggiorna();
					while(contatore != 6){
						Thread.sleep(5000);
					}
					
					//INIZIA PARTITA VERA E PROPRIA
					int turno = 1;
					int i = 0;
					while(giocatori.size()>1){
						System.out.println("RIAZZERO");
						i = 0;
						it = giocatori.iterator();
						do{
							System.out.println("VALORE DI TURONO: "+turno);
							if(turno>1){
								turno = 2;
								System.out.println("TURNO UNO SUPERATO");
								user = it.next();
								System.out.println("P6,+MOSSA ---> "+result);
								ricaricaPedine = ricarica(user);
								System.out.println("ARMATE BONUS: "+ricaricaPedine);
								user.setAzioneComando("ricarica@"+ricaricaPedine);
								result = user.eseguiComandi(null, this);
								contatore = 0;
								aggiorna();
								while(contatore != 6){
									System.out.println("CONTATORE: "+contatore);
									Thread.sleep(2000);
								}
							}
							if(turno==1)
								user = it.next();
							do{
								System.out.println("TURNO DI: "+user.getUser());
								result = user.turno();
								System.out.println("P6,MOSSA ---> "+result);
								user.setAzioneComando(result);
								result = user.eseguiComandi(null,this);
								if(result.equals("fine")){
									System.out.println("FINE TURNO GIOCATORE "+user.getUser());
									System.out.println("ARRIVA");
								}
								if(result.equals("sposta")){
									result = "fine";
									contatore = 0;
									aggiorna();
									while(contatore != 6){
										System.out.println("CONTATORE: "+contatore);
										Thread.sleep(2000);
									}
								}
								else{
									contatore = 0;
									aggiorna();
									while(contatore != 6){
										System.out.println("CONTATORE: "+contatore);
										Thread.sleep(2000);
									}
								}
							}while(!result.equals("fine"));
							i++;	
							System.out.println("CAMBIO TURNO.");
						}while(i<6);
						turno++;
					}
					classifica.add(giocatori.get(0));
					gestDatabase.start();
					Connection connessione = gestDatabase.getConnessione();
					Statement statement = connessione.createStatement();
					String query = "INSERT INTO partita(num_giocatori,primo,secondo,data) VALUES("+numGiocatori+",'"+classifica.get(2)+"','"+classifica.get(1)+"',CURRENT_TIMESTAMP)";
					ResultSet risultato = null;
					risultato = statement.executeQuery(query);		
					risultato.close();
					gestDatabase.chiudiRisorse();
				} catch (Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
	}	
	
	
	/*private void aggiornaPosizionetotale() {
		Iterator<Giocatore> giocatore = giocatori.iterator();
		Giocatore user = null;
		Iterator<Territorio> terr = map.getLista().iterator();
		int i = 0;
		
		while(terr.hasNext()){
			posizioneTotale[i] = terr.next().getNumArmate().toString();
			i++;
		}
		
	}*/

	private String aggiorna() {
		
		Iterator<Giocatore> giocatore = giocatori.iterator();
		Socket client = null;
		Giocatore user = null;
		
		while(giocatore.hasNext()){
			user = giocatore.next(); //prendo il giocatore
			client = user.getSocket();		//prendo il socket del giocatore per inviarlo a taskposizionaarmate
			//SOCKET
			if(client != null){
				TaskAggiorna aggiornamento = new TaskAggiorna(user.getSocket(), this);
				Thread thread = new Thread(aggiornamento);
				thread.start();		
			}
			else if(client == null){
				//RMI
				TaskAggiorna taskAggiorna = new TaskAggiorna(user.getClientInt(), this);
				Thread thread = new Thread(taskAggiorna);
				thread.start();
			}							
		}
		return "true";
	}

	public ArrayList<Giocatore> getGiocatore(){
		return this.giocatori;
	}
	
	@Override
	public void assegnaArmate() {
		Iterator<Giocatore> it = giocatori.iterator();
		Giocatore user = null;
		
		while(it.hasNext()){
			user = it.next();
			user.setArmate(armateGioc);
			System.out.println("Numero armate dopo setArmate: "+user.getArmate());
		}	
	}
	
	@Override
	public String posizionaArmate() {
		Iterator<Giocatore> it = giocatori.iterator(); //per scorrere i giocatori
		Giocatore user = null;
		Socket client = null;
		
		assegnaArmate();
		System.out.println("Arriva fino a dopo assegnaArmate in posizionaArmate");
		posizioneTotale = new String[42];
		while(it.hasNext()){
			user = it.next(); //prendo il giocatore
			client = user.getSocket();		//prendo il socket del giocatore per inviarlo a taskposizionaarmate
			//SOCKET
			if(client != null){
				TaskPosizionaArmate taskPosizione = new TaskPosizionaArmate(user,this);
				Thread thread = new Thread(taskPosizione);
				thread.start();				
			}
			//RMI
			else{
				clientInt = user.getClientInt();
				TaskPosizionaArmate taskPosizione = new TaskPosizionaArmate(clientInt,user,this);
				Thread thread = new Thread(taskPosizione);
				thread.start();
			}
		}
		return "true";
	}

	@Override
	public String assegnaTerritori(ArrayList<Territorio> lista) {
		System.out.println("Sono in assegnaTerritori");

		Random ran = new Random();
		Integer[] territori = new Integer[42];
		Integer numTerritorio = null;
		int flag = 0;
		int i = 0;
		int j = 0;
		Territorio supporto = null;

		
		
		Iterator<Giocatore> it = giocatori.iterator();
		Giocatore user = null;
		
		System.out.println("Sono nell'assegnamento dei territori");

		while(i < 42){
			numTerritorio = ran.nextInt(42)+1;
			while(j < i && flag == 0){
				if(numTerritorio == territori[j]){
					flag=1;
				}
				j++;
			}
			if(flag==0){
				territori[i]=numTerritorio;
				i++;
			}
			flag=0;
			j=0;
		}
		
		int trovato = 0;
		
		for(i = 0; i < territori.length; i++){
			trovato = 0;
			if(i == 0 || i == 14 || i == 28){
				user = it.next();
			}	
			Iterator<Territorio> terr = lista.iterator();
			while(terr.hasNext() && trovato==0){
				supporto = terr.next();
				System.out.println("Valore di Codice di supporto in AssegnaTerritori: "+supporto.getCodice());
				System.out.println("Valore di territori[i]: "+territori[i].toString());
				if(supporto.getCodice().equals(territori[i].toString())){
					user.setTerritori(supporto);
					supporto.setNewUser(user.getUser(), 0);
					trovato = 1;
				}
			}			
		}
		String esitoTerritori = null;
		Iterator<Giocatore> iter = giocatori.iterator();
		while(iter.hasNext()){
			user = iter.next();	
			if (user.inviaTerritori().equals("true")) {
				System.out.println("TERRITORI OK per "+user.getUser());
				esitoTerritori = "true";
			}
			else {
				esitoTerritori = "false";
			}
		}
		return esitoTerritori;
	}

	@Override
	public void assegnaColore() {
		//ipotesi: if (colori == null)
		setColori();
		Giocatore user = null;
		giocatori = istance6();
		
		Iterator<Giocatore> it = giocatori.iterator();
		while(it.hasNext()){
			user = it.next();
			user.setAzioneComando("colore");
			try {
				user.eseguiComandi(colori,this);
				//user.setAzioneComando("avvia");
				//user.eseguiComandi(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean inserisciGiocatori(){
		Giocatore user = null;
		giocatori = istance6();
		Iterator<Giocatore> it = new Prepartita().listaUtenti6.iterator();
		Iterator<Giocatore> ite = null;
		Prepartita prePart = new Prepartita();
		int trovato = 0;
		Giocatore temp = null;
		int i = 0;
		int j = 0;
		int[] daRimuovere = new int[6];
		boolean rimozione = true;
		boolean aggiunto = true;
		
		for(i = 0; i < 6; i++){
			user = it.next();
			if(!giocatori.add(user))
				aggiunto = false;
		}
		
		it = new Prepartita().listaUtenti6.iterator();
		System.out.println("PRIMA");
		while(it.hasNext()){
			System.out.println(it.next().getUser());
		}
		synchronized(lock){
			for (i = 1; i < 4; i++){
				new Prepartita().listaUtenti6.remove(0);
			}
		}
		it = new Prepartita().listaUtenti6.iterator();
		System.out.println("DOPO");
		while(it.hasNext()){
			System.out.println(it.next().getUser());
		}
		return (rimozione&&aggiunto);
	}
	
	
	private void visualizzaTerritori(){
		String user = null;
		int i = 0;
		for(i = 0;i < posizioneTotale.length; i++){
			System.out.println("TERRITORIO: "+(i+1)+"\t ARMATE: "+posizioneTotale[i]);
		}
	}

	public Object getObject() {
		return this.lock;
		}

	public String[] getPosizioneTotale() {
		return this.posizioneTotale;
	}

	public int getContatore() {
		return this.contatore;
	}

	public void setContatore(int contatore) {
		this.contatore = contatore;
	}
	
	public ArrayList<Giocatore> getClassifica(){
		return this.classifica;
	}
	
	private int ricarica(Giocatore user){
		
		Iterator<Giocatore> itGioc = giocatori.iterator();
		int sAmerica = 4;
		int nAmerica = 9;
		int europa = 7;
		int asia = 12;
		int oceania = 4;
		int africa = 6;
		int bonus = 0;
		Iterator<Territorio> itTerr = null;
		Territorio supTerr = null;
		
		itTerr = user.getLista().iterator();
		while(itTerr.hasNext()){
				supTerr = itTerr.next();
				System.out.println("VALORI CONTINENTI: "+supTerr.getContinente());
				if(supTerr.getContinente().equals("1")) nAmerica--;
				if(supTerr.getContinente().equals("2")) sAmerica--;
				if(supTerr.getContinente().equals("3")) europa--;
				if(supTerr.getContinente().equals("4"))	asia--;	
				if(supTerr.getContinente().equals("5")) oceania--;
				if(supTerr.getContinente().equals("6")) africa--;
				System.out.println("CONTATORI: "+nAmerica+" "+sAmerica+" "+europa+" "+asia+" "+oceania+" "+africa);
		}
		if(nAmerica==0) bonus++;
		if(sAmerica==0) bonus++;
		if(europa==0) bonus++;
		if(asia==0) bonus++;
		if(oceania==0) bonus++;
		if(africa==0) bonus++;
		
		System.out.println("BONUS: "+bonus);
		bonus = bonus*5;
		System.out.println("BONUS: "+bonus);
		bonus += Math.ceil(user.getLista().size()/3);
		System.out.println("BONUS: "+bonus);
	
	return bonus;
	}
}
