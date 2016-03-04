package partita;
//ciao
import interfacceComunicazione.Client;
import interfacceComunicazione.Server;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import login.ClientImpl;
import mappa.Mappa;

public class FinestraColori {
	
	private Partita match = null;
	
	private Socket client = null;
	private Server server = null;
	
	private String username = null;
	
	private JFrame finestraColore = null;
	private ButtonGroup scelto = null;
	private JRadioButton cyan = null;
	private JRadioButton green = null;
	private JRadioButton yellow = null;
	private JRadioButton white = null;
	private JRadioButton magenta = null;
	private JRadioButton orange = null;
	
	private PrintWriter scrittore = null;
	
	private JButton leggiColore = null;
	
	private String coloreDeciso = null;

	private ClientImpl clientInt;
	
	public FinestraColori(String coloriLiberi) {
		
		finestraColore = new JFrame();
		finestraColore.setBounds(100, 100, 500, 200);
		finestraColore.setLayout(new FlowLayout());
		
		scelto = new ButtonGroup();
		
		leggiColore = new JButton("Ho deciso");
		leggiColore.setEnabled(false);
		leggiColore.addActionListener(new AscoltatoreInviaComando());
		
		cyan = new JRadioButton("Azzurro");
		cyan.setEnabled(false);
		cyan.addActionListener(new AscoltatoreColoreScelto());
		
		green = new JRadioButton("Verde");
		green.setEnabled(false);
		green.addActionListener(new AscoltatoreColoreScelto());
		
		yellow = new JRadioButton("Giallo");
		yellow.setEnabled(false);
		yellow.addActionListener(new AscoltatoreColoreScelto());
		
		white = new JRadioButton("Bianco");
		white.setEnabled(false);
		white.addActionListener(new AscoltatoreColoreScelto());
		
		magenta = new JRadioButton("Magenta");
		magenta.setEnabled(false);
		magenta.addActionListener(new AscoltatoreColoreScelto());
		
		orange = new JRadioButton("Arancio");
		orange.setEnabled(false);
		orange.addActionListener(new AscoltatoreColoreScelto());
		
		setColoriLiberi(coloriLiberi);
		
		scelto.add(cyan);
		scelto.add(green);
		scelto.add(yellow);
		scelto.add(white);
		scelto.add(magenta);
		scelto.add(orange);
	
		finestraColore.getContentPane().add(cyan);
		finestraColore.getContentPane().add(green);
		finestraColore.getContentPane().add(yellow);
		finestraColore.getContentPane().add(white);
		finestraColore.getContentPane().add(magenta);
		finestraColore.getContentPane().add(orange);
		finestraColore.getContentPane().add(leggiColore);
		finestraColore.addWindowListener(new AscoltatoreFinestra());
		finestraColore.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		finestraColore.setVisible(true);
	
	}
	
	private void setColoriLiberi(String coloriLiberi) {
		System.out.println(coloriLiberi);

		if (coloriLiberi.contains("azzurro")) {
			cyan.setEnabled(true);
		}
		if (coloriLiberi.contains("verde")) {
			green.setEnabled(true);
		}
		if (coloriLiberi.contains("giallo")) {
			yellow.setEnabled(true);
		}
		if (coloriLiberi.contains("bianco")) {
			white.setEnabled(true);
		}
		if (coloriLiberi.contains("magenta")) {
			magenta.setEnabled(true);
		}
		if (coloriLiberi.contains("arancio")) {
			orange.setEnabled(true);
		}
		
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	private void setColoreDeciso(String colore) {
		coloreDeciso = colore;
	}
	
	public String getColoreDeciso() {
		return coloreDeciso;
	}
	
	public JFrame getFinestraColore() {
		return this.finestraColore;
	}
	
	class AscoltatoreColoreScelto implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent coloreScelto) {
			setColoreDeciso(coloreScelto.getActionCommand());
			leggiColore.setEnabled(true);
		}
	}
	
	class AscoltatoreInviaComando implements ActionListener {
		
		String c = null;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			setColoreDeciso(getColoreDeciso().toLowerCase()); //questa è la stringa da inviare
			c = getColoreDeciso();
			System.out.println("FINCOLORI --> Ho scelto: "+c);
			//Socket
			if (client != null) {
				try {
					scrittore = new PrintWriter(client.getOutputStream(), true);
					//System.out.println("Colore: "+c);
					scrittore.println(c);
					scrittore.flush();
					//Si avvia correttamente
					match = new Partita(client, username, c);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//RMI
			else {
				System.out.println("Valore di colore in FinestraColori nell'else: "+c);
				clientInt.setColore(c);
				//setColoreDeciso(c);
			}
			
			leggiColore.setEnabled(false);
			finestraColore.setVisible(false);
		}
	}

	public void setSocket(Socket clientSocket) {
		this.client = clientSocket;
	}

	public void setRMI(Server server) {
		this.server = server;		
	}

	public void setClientInt(ClientImpl clientImpl) {
		// TODO Auto-generated method stub
		this.clientInt = clientImpl;
	}
	
	class AscoltatoreFinestra implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(finestraColore, "Devi selezionare almeno un colore", "Errore selezione colore", JOptionPane.ERROR_MESSAGE);
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(finestraColore, "Devi selezionare almeno un colore", "Errore selezione colore", JOptionPane.ERROR_MESSAGE);
				
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
