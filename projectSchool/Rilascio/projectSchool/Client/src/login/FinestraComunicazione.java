package login;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FinestraComunicazione {

	private JFrame finestraComunicazione = null;
	private JRadioButton socket = null;
	private JRadioButton rmi = null;
	private JButton avanti = null;
	private ButtonGroup scegliComunicazione = null;
	private GestioneFinestre gestioneFinestre = null;
	private String comunicazione = null;
	
	/** Costruttore */
	public FinestraComunicazione() {
		
		this.gestioneFinestre = new GestioneFinestre();
		
		//Finestra
		this.finestraComunicazione = new JFrame("Comunicazione");
		this.finestraComunicazione.setBounds(400, 200, 300, 60);
		this.finestraComunicazione.setResizable(false);
		this.finestraComunicazione.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.finestraComunicazione.getContentPane().setLayout(new GridLayout(1, 3));
		this.finestraComunicazione.addWindowListener(new AscoltatoreFinestra());
	
		//Socket
		this.socket = new JRadioButton("Socket");
		this.socket.addActionListener(new AscoltatoreRadioButton());
		
		//RMI
		this.rmi = new JRadioButton("RMI");
		this.rmi.addActionListener(new AscoltatoreRadioButton());

		//Esclusività
		this.scegliComunicazione = new ButtonGroup();
		this.scegliComunicazione.add(socket);
		this.scegliComunicazione.add(rmi);
		
		//Bottone
		this.avanti = new JButton("Login");
		this.avanti.setEnabled(false);
		this.avanti.addActionListener(new AscoltatoreButton());

		//Aggiunta contenuti alla finestra
		this.finestraComunicazione.getContentPane().add(socket);
		this.finestraComunicazione.getContentPane().add(rmi);
		this.finestraComunicazione.getContentPane().add(avanti);
		
		//Visibilità della finestra
		this.finestraComunicazione.setVisible(true);
	}

	/**
	 * 
	 * @return JFrame
	 */
	public JFrame getFinestraComunicazione() {
		
		return this.finestraComunicazione;
	}
	
	//Ascoltatore JRadioButton
	class AscoltatoreRadioButton implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent tipoComunicazione) {
			avanti.setEnabled(true);
			comunicazione = tipoComunicazione.getActionCommand();
		}
	}

	//Ascoltatore JButton
	class AscoltatoreButton implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent prosegui) {	
			gestioneFinestre.setCorrectWindow(null, null,prosegui.getActionCommand(), finestraComunicazione, comunicazione, null, null, null);
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
