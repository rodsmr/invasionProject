package partita;

import interfacceComunicazione.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class TaskAggiorna implements Runnable {

	private Socket client = null;
	private Partita partita = null;
	private Client clientInt = null;
	
	public TaskAggiorna(Socket client, PartitaTre partita){
		this.client = client;
		this.partita = partita;
	}
	
	public TaskAggiorna(Socket client, PartitaQuattro partita){
		this.client = client;
		this.partita = partita;
	}
	
	public TaskAggiorna(Socket client, PartitaCinque partita){
		this.client = client;
		this.partita = partita;
	}
	
	public TaskAggiorna(Socket client, PartitaSei partita){
		this.client = client;
		this.partita = partita;
	}
	
	public TaskAggiorna(Client clientInt, PartitaTre partita) {
		this.clientInt = clientInt;
		this.partita = partita;
	}
	
	public TaskAggiorna(Client clientInt, PartitaQuattro partita) {
		this.clientInt = clientInt;
		this.partita = partita;
	}
	
	public TaskAggiorna(Client clientInt, PartitaCinque partita) {
		this.clientInt = clientInt;
		this.partita = partita;
	}
	
	public TaskAggiorna(Client clientInt, PartitaSei partita) {
		this.clientInt = clientInt;
		this.partita = partita;
	}

	

	@Override
	public void run() {
		PrintWriter writer = null;
		String[] daInviare = null;
		String aggiornamento = "aggiorna@";
		BufferedReader reader = null;
		String result = null;
		Object lock = null;
		int contatore = 0;
		boolean attesa = false;
		
		daInviare = partita.getPosizioneTotale();
		for(int i = 0; i < daInviare.length; i++){
			aggiornamento+=daInviare[i] + ";";
		}
		
		//SOCKET
		if(client!=null){
			try {
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				writer = new PrintWriter(client.getOutputStream(),true);

				System.out.println("STRINGA DA INVIARE -->: "+aggiornamento);
				writer.println(aggiornamento);
				writer.flush();
				//mi metto in attesa di una risposta da un client
				System.out.println("AGGIORNA INVIATO");
				/*while(!reader.ready()){
					Thread.sleep(1000);
				}
				result = reader.readLine();
				System.out.println("-->TASK AGGIORNA: "+result);
				if(result.equals("aggiorna@true")){
					System.out.println("RICEVUTO COMANDO TRUE PER AGGIORNAMENTO");
					
					System.out.println("FINE AGGIORNAMENTO");
				}
				else System.out.println("Errore in ricezione aggiornamento");	*/
			} catch (IOException e) {
			e.printStackTrace();
			}
		}
		//RMI
		else if (clientInt != null) {
			try {
				System.out.println("Sto per inviare RMI: --> "+aggiornamento);
				result = clientInt.comandoDaEseguire(aggiornamento);
				System.out.println("TA: result --> "+result);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		lock = partita.getObject();
		synchronized(lock){
			contatore = partita.getContatore();
			contatore++;
			partita.setContatore(contatore);					
		}
	}
}
