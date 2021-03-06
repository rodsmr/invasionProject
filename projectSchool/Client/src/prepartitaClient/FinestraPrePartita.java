package prepartitaClient;

import interfacceComunicazione.Client;
import interfacceComunicazione.Server;
import interfacceComunicazione.ServerAccept;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.rmi.RemoteException;

import javax.swing.*;

import login.ClientImpl;
import login.GestioneFinestre;
import analisiComandi.AnalisiLogin;

public class FinestraPrePartita {
	
	private GestioneFinestre gestioneFinestra = null;
	
	private char tipoPartita = '3';
	private AnalisiLogin analisilog = null;
	private JFrame finestraPartita = null;

	private JPanel scegliPartita = null;
	private JPanel attendiPartita = null;
	
	private String username = null;
	private ServerAccept stub = null;
	private JToolBar barra = null;
	
	private JLabel numeroGiocatori = null;
	private JLabel attesaGiocatori = null;
	
	private JButton logout = null;
	private JButton trePlayer = null;
	private JButton quattroPlayer = null;
	private JButton cinquePlayer = null;
	private JButton seiPlayer = null;
	private JButton annulla = null;
	
	private String tipoComunicazione = null;
	
	private Server server = null;
	
	private Socket clientLoggato = null;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private String comandoDelServer = null;

	private ClientImpl clientInt = null;
	
	public FinestraPrePartita() {
		
		gestioneFinestra = new GestioneFinestre();
		
		this.scegliPartita = new JPanel(null);
		this.scegliPartita.setBounds(0, 0, 415, 200);
		
		this.attendiPartita = new JPanel(null);
		this.attendiPartita.setBounds(0, 0, 415, 200);
		
		this.finestraPartita = new JFrame("Partita");
		this.finestraPartita.setBounds(350, 200, 415, 200);
		this.finestraPartita.setResizable(false);
		this.finestraPartita.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.finestraPartita.getContentPane().setLayout(null);
		this.finestraPartita.addWindowListener(new AscoltatoreFinestra());
		
		setBottoneLogout();
		setLabelNumeroGiocatori();
		setBottoniNumeroGiocatori();
		setBarraCaratteristiche();
		setScegliPartita();
		
		setAttesaGiocatori();
		setBottoneAnnulla();
		setAttendiPartita();
		
		this.finestraPartita.getContentPane().add(this.scegliPartita);
		
		this.finestraPartita.setVisible(true);
	}
	
	private void setAttesaGiocatori() {

		this.attesaGiocatori = new JLabel("Attendi");
		this.attesaGiocatori.setBounds(125, 55, 300, 30);
	}

	private void setBottoneAnnulla() {
		
		this.annulla = new JButton("Annulla");
		this.annulla.addActionListener(new AscoltatoreBottoneAnnulla());
		this.annulla.setBounds(105, 80, 80, 30);
	}

	private void setAttendiPartita() {
		
		this.attendiPartita.add(attesaGiocatori);
		this.attendiPartita.add(annulla);
	}
	
	private void setScegliPartita() {
		
		this.scegliPartita.add(barra);
		this.scegliPartita.add(logout);
		this.scegliPartita.add(numeroGiocatori);
		this.scegliPartita.add(trePlayer);
		this.scegliPartita.add(quattroPlayer);
		this.scegliPartita.add(cinquePlayer);
		this.scegliPartita.add(seiPlayer);
		
	}
	
	private void setBarraCaratteristiche() {
		
		this.barra = new JToolBar();
		this.barra.setBounds(0, 0, 415, 30);
		this.barra.setEnabled(false);
	}

	private void setLabelNumeroGiocatori() {
		
		numeroGiocatori = new JLabel("Scegli il numero di giocatori per la tua partita");
		numeroGiocatori.setBounds(125, 55, 300, 30);
		numeroGiocatori.setVisible(true);
		
	}

	private void setBottoniNumeroGiocatori() {

		trePlayer = new JButton("3");
		trePlayer.setBounds(105, 80, 55, 30);
		trePlayer.addActionListener(new AscoltatoreGiocatori());
		trePlayer.setVisible(true);
		
		quattroPlayer = new JButton("4");
		quattroPlayer.setBounds(170, 80, 80, 30);
		quattroPlayer.addActionListener(new AscoltatoreGiocatori());
		quattroPlayer.setVisible(true);

		cinquePlayer = new JButton("5");
		cinquePlayer.setBounds(260, 80, 75, 30);
		cinquePlayer.addActionListener(new AscoltatoreGiocatori());
		cinquePlayer.setVisible(true);

		seiPlayer = new JButton("6");
		seiPlayer.setBounds(345, 80, 55, 30);
		seiPlayer.addActionListener(new AscoltatoreGiocatori());
		seiPlayer.setVisible(true);
		
	}

	private void setBottoneLogout() {
		
		logout = new JButton("Logout");
		logout.setBounds(10, 80, 75, 30);
		logout.addActionListener(new AscoltatoreLogout());
		logout.setVisible(true);
		
	}
	
	private void passaAdAnnulla() {	
		
		finestraPartita.getContentPane().remove(scegliPartita);
		finestraPartita.getContentPane().repaint();
		finestraPartita.getContentPane().validate();
		finestraPartita.getContentPane().add(attendiPartita);
		finestraPartita.getContentPane().repaint();
		finestraPartita.getContentPane().validate();		
	}
	
	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione.toLowerCase();
		System.out.println("tipoComunicazionePartita: "+tipoComunicazione);
	}
	
	public void setUsername(String username) {
		this.username = username;
		this.barra.add(new JLabel("Username: "+ username));
	}
	
	public JFrame getFinestraPartita() {
		return this.finestraPartita;
	}
	
	//per RMI
	public void setServer(Server server){
		this.server = server;
		System.out.println("Valore server FinestraPartita: "+server);
	}
	
	//avr� un metodo setSocket
	public void setSocket(Socket clientLoggato) {
		this.clientLoggato = clientLoggato;
	}

	public void setClientInt(ClientImpl client) {
		this.clientInt = client;	
		System.out.println("Funziona clientInt?:"+clientInt);
	}
	
	//SISTEMARE
	class AscoltatoreBottoneAnnulla implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			//devo rimuovere il giocatore dalla lista
			
			if (tipoComunicazione.equals("socket")) {
				
				AnalisiLogin analisilog = new AnalisiLogin();
				PrintWriter writer = null;
				BufferedReader reader = null;
				
				try {
					writer = new PrintWriter(clientLoggato.getOutputStream(),true);
					username = analisilog.inviaUser(username);
					String comando = "prepartitaA@"+username+";"+tipoPartita;
					System.out.println("-->ANNULLA: "+comando);
					//da gestire la ricezione del comando socket e l'inserimento in lista
					writer.println(comando);
					reader = new BufferedReader(new InputStreamReader(clientLoggato.getInputStream()));
					//leggo la risposta dell'inserimento
					String result = reader.readLine();
					System.out.println("-->ANNULLA result: "+result);
					/*giocatoriPresenti = result.charAt(result.length()-1);
					result = result.substring(0, result.length() - 1);
					*/
					if(result.equals("true")) {
						System.out.println("Utente rimosso correttamente");
					}
					else {
						System.out.println("Rimozione fallito");
					}
				} catch (IOException e) {
					System.out.println("Errore creazione reader/writer FinestraPrePartita");
					System.out.println("Message: "+e.getMessage());
				}
				
			}
			else if(tipoComunicazione.equals("rmi")) {
				
				String esito = null;
				
				try {
					esito = server.rimuoviInLista(username, tipoPartita+"", "rmi");
//					giocatoriPresenti = esito.charAt(esito.length()-1);
//					esito = esito.substring(0, esito.length()-1);
				} catch (RemoteException e) {
					System.out.println("Finestra partita: problema con prepartita");
					System.out.println("Message prePartita: "+e.getMessage());
				}
				if(esito.equals("true")) System.out.println("Esito rimozione:"+esito);
				else System.out.println("Errore rimozione: "+esito);
				
			}

			finestraPartita.getContentPane().remove(attendiPartita);
			finestraPartita.getContentPane().repaint();
			finestraPartita.getContentPane().validate();
			finestraPartita.getContentPane().add(scegliPartita);
			finestraPartita.getContentPane().repaint();
			finestraPartita.getContentPane().validate();
		 	
		}
	}

	class AscoltatoreGiocatori implements ActionListener {

		/* quando clicco sul pulsante passo al server anche il nome dell'utente che ha fatto
		 * quella richiesta per aggiungerlo alla lista dei giocatori. Devo anche ricordarmi
		 * di dare ad ogni singolo thread il nome dell'utente che l'ha creato
		 */

		@Override
		public void actionPerformed(ActionEvent giocatori) {
			
			tipoPartita = giocatori.getActionCommand().charAt(0);
			
			if(tipoComunicazione.equals("socket")){
				analisilog = new AnalisiLogin();
				
				try {
					writer = new PrintWriter(clientLoggato.getOutputStream(),true);
					String comando = "prepartita@"+analisilog.inviaUser(username)+";"+giocatori.getActionCommand();	
					//da gestire la ricezione del comando socket e l'inserimento in lista
					writer.println(comando);
					writer.flush();
					reader = new BufferedReader(new InputStreamReader(clientLoggato.getInputStream()));
//					//leggo la risposta dell'inserimento
					String result = reader.readLine();
					//giocatoriPresenti = result.charAt(result.length()-1);
					//result = result.substring(0, result.length() - 1);
					
					if(result.equals("true")) {
						System.out.println("Utente inserito correttamente");

						passaAdAnnulla();
						//a questo punto sono stato aggiunto in lista, devo attendere che questa
						//lista si riempia per consentirmi di giocare contro altri utenti
						//dovr� ricevere il comando "colore@...";
						/* Mi blocca tutto e non fa funzionare nemmeno lo switch del pannello */


						System.out.println("Attesa lettura");
						
						comandoDelServer = reader.readLine();

						if (comandoDelServer.startsWith("colore")) {
							gestioneFinestra.setCorrectWindow(clientLoggato, null, "colore", finestraPartita, "socket", username, comandoDelServer.substring(7), null);
							//new FinestraColori(comandoDelServer.substring(7), clientLoggato);
						}
					}
					else {
						System.out.println("Inserimento fallito");
					}
				} catch (IOException e) {
					System.out.println("Errore creazione reader/writer FinestraPrePartita");
					System.out.println("Message: "+e.getMessage());
				}
			}
			//RMI
			else if(tipoComunicazione.equals("rmi")){
				
				String esito = null;
				
				try {
					esito = server.inserimentoListaPrepartita(username, giocatori.getActionCommand(), "rmi");
					//giocatoriPresenti = esito.charAt(esito.length()-1);
					//esito = esito.substring(0, esito.length()-1);
					if(esito.equals("true")) {
						System.out.println("Esito aggiunta in coda andata (FinestraPartita) andata a buon fine");
						passaAdAnnulla();
						clientInt.setUser(username);
						clientInt.setServer(server);
						/* una volta che ho inserito in lista devo aspettare che RMI mi dica
						 * di gestire il colore
						 * RMI mi dir� questo appoggiandosi a clientInt.setComando(String comando) e io da qui lo leggo
						 * e se � colore tramite gestioneFinestra devo passare alla finestra apposita a cui passer�
						 * il valore del server. Ora la gestione si sposta in finestraColore.
						 */
						
						//--> SPOSTO TUTTO IN ClientImpl
						
						/*
						String callbackComando = null;
						
						System.out.println("Prepartita Client: entro nel while");
						while (callbackComando == null) {
							callbackComando = clientInt.getComandoDaEseguire();
						}
						System.out.println("Prepartita Client: esco dal while");
						
						//Mi d� prima l'ultimo inserito
						System.out.println("\t\tCallback:"+callbackComando+" per "+username);
						
						if (callbackComando.startsWith("colore")) {
							gestioneFinestra.setCorrectWindow(null, server, "colore", finestraPartita, "rmi", username, callbackComando.substring(7), clientInt);
						}*/
					}
					else {
						System.out.println("Errore aggiunta in coda (FinestraPartita)");
					}
				} catch (RemoteException e) {
					System.out.println("Finestra partita: problema con prepartita");
					System.out.println("Message prePartita: "+e.getMessage());
				}
			}
			
			//attesaGiocatori.setText("Ci sono: "+giocatoriPresenti+"/"+giocatori.getActionCommand()+". Sei stufo? CLOSE WINDOW");

		}
	}

	class AscoltatoreLogout implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if (tipoComunicazione.equalsIgnoreCase("socket")) {
				//final String HOST = "127.0.0.1";
				//final int PORT = 4500;
				BufferedReader reader  = null;
				PrintWriter scrittore = null;
				try {
				//	client = new Socket(HOST, PORT);
					String result = null;
					reader = new BufferedReader(new InputStreamReader(clientLoggato.getInputStream()));
					scrittore = new PrintWriter(clientLoggato.getOutputStream(), true);
					
					scrittore.println("logout@"+username);
					scrittore.flush();
					
					result = reader.readLine();
					System.out.println("Ho letto dal socket: "+result);
					if(result.startsWith("ok")){
						JOptionPane.showMessageDialog(finestraPartita, "Logout di "+username+" corretto");
						new GestioneFinestre().setCorrectWindow(null, null, "comunicazione", finestraPartita, "socket", null, null, null);
						clientLoggato.close();
					}
					else if (result.startsWith("ko")){
						JOptionPane.showMessageDialog(finestraPartita, "Logout di "+username+" fallito");
					}
				} catch (IOException e) {
					System.out.println("Errore creazione reader in FinestraPartita");
				} finally {
					try {
						reader.close();
						scrittore.close();
					} catch (IOException e) {
						System.out.println("Impossibile chiudere reader/scrittore dopo logout");
					}
				}
			}
			else if (tipoComunicazione.equalsIgnoreCase("rmi")) {
				try {
					stub = clientInt.getStub();
					if (stub == null) {
						System.out.println("Logout--> stub null");
					}
					else {
						System.out.println("Logout--> stub not null");
					}
					if (stub.logoutRMI(username)) {
						JOptionPane.showMessageDialog(finestraPartita, "Logout corretto");
						new GestioneFinestre().setCorrectWindow(null, null, "comunicazione", finestraPartita, "rmi", null, null, null);
					}
					else {
						JOptionPane.showMessageDialog(finestraPartita, "Logout fallito");
					}
				} catch (RemoteException e) {
					System.out.println("Problemi con lo stub");
				}
			}
		}
	}
	
	class AscoltatoreFinestra implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			if (tipoComunicazione.equalsIgnoreCase("socket")) {
				try {
					writer = new PrintWriter(clientLoggato.getOutputStream(),true);
					String comando = "prepartita@"+analisilog.inviaUser(username)+";"+tipoPartita;	
					//da gestire la ricezione del comando socket e l'inserimento in lista
					writer.println(comando);
					writer.flush();
					
					writer.println("logout@"+username);
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else if (tipoComunicazione.equalsIgnoreCase("rmi")) {
			try {
				server.rimuoviInLista(username, tipoPartita+"", "rmi");
				stub.logoutRMI(username);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		System.exit(1);
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
