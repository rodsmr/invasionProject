package mappa;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import eccezione.FaultPositionArmate;
import partita.GestioneComandiPartita;

public class Mappa {

	private int errore = 0;
	
	private boolean timeOut = false;
	
	private String[] vecchiePedine = null;
	
//	private TaskTimer taskTimer = null;
	
	private Color coloreAttaccante = null;
	private Color coloreDifensore = null;
	
	private int pedineGiocata = 0;
	private int pedineAttaccante = 0;
	private int pedineDifensore = 0;
	private String statoAttacco = "null";
	private String difesa = "null";
	private String mossa = "null";
	
	private JLabel armateResidue = null;
	
	private Timer timer = null;
	private Integer numeroArmate = 0;
	
	private JFrame finestraMappa = null;
	private JLabel contenitoreImmagine = null;
	private JPanel contenitoreMosse = null;
	private JButton salva = null;
	
	private ArrayList<JTextField> caselle = null;
	private ArrayList<String> territori = null;
	private Iterator<String> terr = null;
	private Iterator<JTextField> iteratoreCaselle = null;	
	
	private String daInviare = null;
	private String stato = "null";
	
	private int contatoreTimer = 0;
	
	private JButton lancia = null;
	
	//sono modificabili sono in fase posizionameto, solo quelle che mi appartengono
	//aggiungo bottoni al centro il pi� grandi possibili visibili all'utente da cui attaccher�
	private JTextField uno = null;
	private JTextField due = null;
	private JTextField tre = null;
	private JTextField quattro = null;
	private JTextField cinque = null;
	private JTextField sei = null;
	private JTextField sette = null;
	private JTextField otto = null;
	private JTextField nove = null;
	private JTextField dieci = null;
	private JTextField undici = null;
	private JTextField dodici = null;
	private JTextField tredici = null;
	private JTextField quattordici = null;
	private JTextField quindici = null;
	private JTextField sedici = null;
	private JTextField diciassette = null;
	private JTextField diciotto = null;
	private JTextField diciannove = null;
	private JTextField venti = null;
	private JTextField vuno = null;
	private JTextField vdue = null;
	private JTextField vtre = null;
	private JTextField vquattro = null;
	private JTextField vcinque = null;
	private JTextField vsei = null;
	private JTextField vsette = null;
	private JTextField votto = null;
	private JTextField vnove = null;
	private JTextField trenta = null;
	private JTextField tuno = null;
	private JTextField tdue = null;
	private JTextField ttre = null;
	private JTextField tquattro = null;
	private JTextField tcinque = null;
	private JTextField tsei = null;
	private JTextField tsette = null;
	private JTextField totto = null;
	private JTextField tnove = null;
	private JTextField quaranta = null;
	private JTextField quno = null;
	private JTextField qdue = null;
	
	private String user = null;
	private String colore = null;

	private GestioneComandiPartita comandi = null;
	
	private JPanel salvataggioArmate = null;
	private JPanel mosse = null;
	private JPanel dadi = null;
	
	private JButton attacca = null;
	private JButton sposta = null;
	private JButton fineTurno =  null;
	
	private JTextField dAUno = null;
	private JTextField dADue = null;
	private JTextField dATre = null;
	
	private JTextField dDUno = null;
	private JTextField dDDue = null;
	
	private JComboBox attaccoDa = null;
	private JComboBox attaccoA = null;
	
	private JTextField pedineMossa = null;
	
	private JButton eseguiMossa = null;
	
	public Mappa(String user, String colore) {		
		
		armateResidue = new JLabel();
		
		timer = new Timer(0, new AscoltatoreTimer());
		
		vecchiePedine = new String[42];

		attacca = new JButton("Attacca");
		attacca.setBounds(50, 15, 90, 30);
		attacca.setVisible(true);
		attacca.setEnabled(false);
		attacca.addActionListener(new AscoltatoreTipoMossa());
		
		sposta = new JButton("Sposta");
		sposta.setBounds(150, 15, 90, 30);
		sposta.setVisible(true);
		sposta.setEnabled(false);
		sposta.addActionListener(new AscoltatoreTipoMossa());
		
		fineTurno = new JButton("Fine");
		fineTurno.setVisible(true);
		fineTurno.setEnabled(false);
		fineTurno.addActionListener(new AscoltatoreTipoMossa());
		
		salva = new JButton("Salva");
		salva.setEnabled(false);
		salva.addActionListener(new AscoltatoreSalvataggioArmate());
		
		salvataggioArmate = new JPanel(new GridLayout());
		salvataggioArmate.setBorder(new TitledBorder("Posizionamento: salva"));
		salvataggioArmate.setBounds(5, 15, 285, 65);
		
		mosse = new JPanel(new GridLayout(4, 1));
		mosse.setBorder(new TitledBorder("Mosse partita: attacchi o sposti?"));
		mosse.setBounds(5, 90, 285, 300);

		eseguiMossa = new JButton("Esegui");
		eseguiMossa.addActionListener(new AscoltatoreEseguiMossa());
		
		pedineMossa = new JTextField(2);
		pedineMossa.setBorder(new TitledBorder("Quante pedine vuoi?"));
		
		attaccoDa = new JComboBox();
		attaccoDa.setEditable(false);
		attaccoDa.setEnabled(false);
		attaccoDa.addItemListener(new AscoltatoreAttaccoDa());
		attaccoDa.setBorder(new TitledBorder("Attacco/Sposto da"));
		attaccoDa.addItem("");
		
		attaccoA = new JComboBox();
		attaccoA.setEditable(false);
		attaccoA.setEnabled(false);
		attaccoA.setBorder(new TitledBorder("Attacco/Sposto a"));
		attaccoA.addItemListener(new AscoltatoreAttaccoA());
		attaccoA.addItem("");

		eseguiMossa.setEnabled(false);
		pedineMossa.setEditable(false);
		
		mosse.add(attacca);
		mosse.add(sposta);
		mosse.add(fineTurno);
		mosse.add(attaccoDa);
		mosse.add(attaccoA);
		mosse.add(pedineMossa);
		mosse.add(eseguiMossa);
				
		dadi = new JPanel(new GridLayout(2, 2));
		dadi.setBorder(new TitledBorder("Dadi: sarai fortunato?"));
		dadi.setBounds(5, 400, 285, 155);

		dAUno = new JTextField();
		dAUno.setBackground(Color.RED);
		dAUno.setEditable(false);
		
		dADue = new JTextField();
		dADue.setBackground(Color.RED);
		dADue.setEditable(false);
		
		dATre = new JTextField();
		dATre.setBackground(Color.RED);
		dATre.setEditable(false);
		
		dDUno = new JTextField();
		dDUno.setBackground(Color.BLUE);
		dDUno.setEditable(false);
		
		dDDue = new JTextField();
		dDDue.setBackground(Color.BLUE);
		dDDue.setEditable(false);
		
		dadi.add(dAUno);
		dadi.add(dADue);
		dadi.add(dATre);
		dadi.add(dDUno);
		dadi.add(dDDue);
		
		caselle = new ArrayList<JTextField>();
		this.user = user;
		this.colore = colore;
		
		finestraMappa = new JFrame(user+": Giochiamo");
		finestraMappa.setBounds(50, 50, 1100, 588);
		finestraMappa.setResizable(false);
		finestraMappa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finestraMappa.addWindowListener(new AscoltatoreFinestra());
		finestraMappa.setLayout(null);
		
		contenitoreImmagine = new JLabel(new ImageIcon(".\\images\\map.jpg"));
		contenitoreImmagine.setBorder(new EtchedBorder());
		contenitoreImmagine.setBounds(0, 0, 800, 560);

		contenitoreMosse = new JPanel(null);
		contenitoreMosse.setBorder(new TitledBorder("Gestione match"));
		contenitoreMosse.setBounds(800, 0, 295, 560);
		
		contenitoreMosse.add(this.salvataggioArmate);
		contenitoreMosse.add(this.mosse);
		contenitoreMosse.add(this.dadi);
		
		salvataggioArmate.add(armateResidue);
		salvataggioArmate.add(this.salva);
		
		setCasellePedine();
		
		finestraMappa.getContentPane().add(contenitoreImmagine);
		finestraMappa.getContentPane().add(contenitoreMosse);
		
		finestraMappa.setVisible(false);
	}

	public void setColoreAttaccante(String colore) {	
		this.coloreAttaccante = this.changeFormatColor(colore);
	}
	
	public void setColoreDifensore(String colore) {
		this.coloreDifensore = this.changeFormatColor(colore);
	}
	
	public void setTimeout(boolean timeOut) {
		this.timeOut = timeOut;
	}
	
	//Inserisco i territori nel men� a tendina di Attacca/Sposta a
	public void setAttaccoA(String spostabili) {
		attaccoA.setEnabled(true);
		attaccoDa.setEnabled(false);
		System.out.println("Spostabili: "+spostabili);
		
		//Se posso fare qualcosa
		if (spostabili.contains(";")) {
			errore = 0;
			System.out.println("Contiene ; --> n territori");
			
			String[] dovePossoAndare = spostabili.split(";");
			int i = 0;
			
			for (i = 0; i < dovePossoAndare.length; i++) {
					System.out.println("Dove: "+dovePossoAndare[i]);
					attaccoA.addItem(dovePossoAndare[i]);
			}
		}
		//Se non ho territori con cui fare qualcosa
		else {
			System.out.println("Non contiene ; --> 0 territori");
			JOptionPane.showMessageDialog(finestraMappa, "Nessuno stato raggiungibile, nuova operazione", "Errore selezione stato", JOptionPane.WARNING_MESSAGE);
			errore = 1;
			attaccoA.setEnabled(false);
			attaccoDa.setEnabled(true);
		}
	}
	
	//Inserisco i territori nel men� a tendina di Attacca/Sposta Da
	//@Overloading od @Override
	public void setTerritoriGiocata() {
		
		iteratoreCaselle = caselle.iterator();
		JTextField temp = null;
		
		while (iteratoreCaselle.hasNext()) {
			temp = iteratoreCaselle.next();
			if (temp.getBackground().equals(changeFormatColor(colore))) {
				attaccoDa.addItem(temp.getName());
			}
		}
	}
	
	//Ritorna la finestra della Mappa
	public JFrame getFinestraMappa() {
		return this.finestraMappa;
	}
	
	private void setCasellePedine() {
	
		setNorthAmerica();
		setSouthAmerica();
		setEuropa();
		setAsia();
		setOceania();
		setAfrica();
	}

	private void setAfrica() {
		
		tsette = new JTextField(1);
		tsette.setName("37");
		tsette.setBounds(448, 485, 20, 20);
		tsette.setEditable(false);
		finestraMappa.getContentPane().add(tsette);
		caselle.add(tsette);
		
		totto = new JTextField(1);
		totto.setName("38");
		totto.setBounds(371, 463, 20, 20);
		totto.setEditable(false);
		finestraMappa.getContentPane().add(totto);
		caselle.add(totto);
		
		tnove = new JTextField(1);
		tnove.setName("39");
		tnove.setBounds(415, 410, 20, 20);
		tnove.setEditable(false);
		finestraMappa.getContentPane().add(tnove);
		caselle.add(tnove);
		
		quaranta = new JTextField(1);
		quaranta.setName("40");
		quaranta.setBounds(371, 423, 20, 20);
		quaranta.setEditable(false);
		finestraMappa.getContentPane().add(quaranta);
		caselle.add(quaranta);
		
		quno = new JTextField(1);
		quno.setName("41");
		quno.setBounds(290, 330, 20, 20);
		quno.setEditable(false);
		finestraMappa.getContentPane().add(quno);
		caselle.add(quno);
		
		qdue = new JTextField(1);
		qdue.setName("42");
		qdue.setBounds(372, 338, 20, 20);
		qdue.setEditable(false);
		finestraMappa.getContentPane().add(qdue);
		caselle.add(qdue);
	}

	private void setAsia() {
		
		vuno = new JTextField(1);
		vuno.setName("21");
		vuno.setBounds(480, 165, 20, 20);
		vuno.setEditable(false);
		finestraMappa.getContentPane().add(vuno);
		caselle.add(vuno);
		
		vdue = new JTextField(1);
		vdue.setName("22");
		vdue.setBounds(480, 205, 20, 20);
		vdue.setEditable(false);
		finestraMappa.getContentPane().add(vdue);
		caselle.add(vdue);
		
		vtre = new JTextField(1);
		vtre.setName("23");
		vtre.setBounds(480, 315, 20, 20);
		vtre.setEditable(false);
		finestraMappa.getContentPane().add(vtre);
		caselle.add(vtre);
		
		vquattro = new JTextField(1);
		vquattro.setName("24");
		vquattro.setBounds(520, 135, 20, 20);
		vquattro.setEditable(false);
		finestraMappa.getContentPane().add(vquattro);
		caselle.add(vquattro);
		
		vcinque = new JTextField(1);
		vcinque.setName("25");
		vcinque.setBounds(570, 60, 20, 20);
		vcinque.setEditable(false);
		finestraMappa.getContentPane().add(vcinque);
		caselle.add(vcinque);
		
		vsei = new JTextField(1);
		vsei.setName("26");
		vsei.setBounds(620, 70, 20, 20);
		vsei.setEditable(false);
		finestraMappa.getContentPane().add(vsei);
		caselle.add(vsei);
		
		vsette = new JTextField(1);
		vsette.setName("27");
		vsette.setBounds(700, 150, 20, 20);
		vsette.setEditable(false);
		finestraMappa.getContentPane().add(vsette);
		caselle.add(vsette);
		
		votto = new JTextField(1);
		votto.setName("28");
		votto.setBounds(570, 135, 20, 20);
		votto.setEditable(false);
		finestraMappa.getContentPane().add(votto);
		caselle.add(votto);
		
		vnove = new JTextField(1);
		vnove.setName("29");
		vnove.setBounds(620, 160, 20, 20);
		vnove.setEditable(false);
		finestraMappa.getContentPane().add(vnove);
		caselle.add(vnove);
		
		trenta = new JTextField(1);
		trenta.setName("30");
		trenta.setBounds(570, 230, 20, 20);
		trenta.setEditable(false);
		finestraMappa.getContentPane().add(trenta);
		caselle.add(trenta);
		
		tuno = new JTextField(1);
		tuno.setName("31");
		tuno.setBounds(570, 315, 20, 20);
		tuno.setEditable(false);
		finestraMappa.getContentPane().add(tuno);
		caselle.add(tuno);
		
		tdue = new JTextField(1);
		tdue.setName("32");
		tdue.setBounds(645, 295, 20, 20);
		tdue.setEditable(false);
		finestraMappa.getContentPane().add(tdue);
		caselle.add(tdue);
		
	}

	private void setEuropa() {
		
		quattordici = new JTextField(1);
		quattordici.setName("14");
		quattordici.setBounds(300, 230, 20, 20);
		quattordici.setEditable(false);
		finestraMappa.getContentPane().add(quattordici);
		caselle.add(quattordici);
		
		quindici = new JTextField(1);
		quindici.setName("15");
		quindici.setBounds(390, 245, 20, 20);
		quindici.setEditable(false);
		finestraMappa.getContentPane().add(quindici);
		caselle.add(quindici);
		
		sedici = new JTextField(1);
		sedici.setName("16");
		sedici.setBounds(288, 170, 20, 20);
		sedici.setEditable(false);
		finestraMappa.getContentPane().add(sedici);
		caselle.add(sedici);
		
		diciassette = new JTextField(1);
		diciassette.setName("17");
		diciassette.setBounds(385, 193, 20, 20);
		diciassette.setEditable(false);
		finestraMappa.getContentPane().add(diciassette);
		caselle.add(diciassette);
		
		diciotto = new JTextField(1);
		diciotto.setName("18");
		diciotto.setBounds(338, 90, 20, 20);
		diciotto.setEditable(false);
		finestraMappa.getContentPane().add(diciotto);
		caselle.add(diciotto);
		
		diciannove = new JTextField(1);
		diciannove.setName("19");
		diciannove.setBounds(360, 145, 20, 20);
		diciannove.setEditable(false);
		finestraMappa.getContentPane().add(diciannove);
		caselle.add(diciannove);
		
		venti = new JTextField(1);
		venti.setName("20");
		venti.setBounds(430, 170, 20, 20);
		venti.setEditable(false);
		finestraMappa.getContentPane().add(venti);
		caselle.add(venti);
		
	}

	private void setSouthAmerica() {
		
		dieci = new JTextField(1);
		dieci.setName("10");
		dieci.setBounds(53, 325, 20, 20);
		dieci.setEditable(false);
		finestraMappa.getContentPane().add(dieci);
		caselle.add(dieci);
		
		undici = new JTextField(1);
		undici.setName("11");
		undici.setBounds(92, 392, 20, 20);
		undici.setEditable(false);
		finestraMappa.getContentPane().add(undici);
		caselle.add(undici);
		
		dodici = new JTextField(1);
		dodici.setName("12");
		dodici.setBounds(160, 350, 20, 20);
		dodici.setEditable(false);
		finestraMappa.getContentPane().add(dodici);
		caselle.add(dodici);
		
		tredici = new JTextField(1);
		tredici.setName("13");
		tredici.setBounds(85, 440, 20, 20);
		tredici.setEditable(false);
		finestraMappa.getContentPane().add(tredici);
		caselle.add(tredici);
		
	}

	private void setOceania() {
		
		ttre = new JTextField(1);
		ttre.setName("33");
		ttre.setBounds(678, 355, 20, 20);
		ttre.setEditable(false);
		finestraMappa.getContentPane().add(ttre);
		caselle.add(ttre);
		
		tquattro = new JTextField(1);
		tquattro.setName("34");
		tquattro.setBounds(747, 390, 20, 20);
		tquattro.setEditable(false);
		finestraMappa.getContentPane().add(tquattro);
		caselle.add(tquattro);
		
		tcinque = new JTextField(1);
		tcinque.setName("35");
		tcinque.setBounds(712, 495, 20, 20);
		tcinque.setEditable(false);
		finestraMappa.getContentPane().add(tcinque);
		caselle.add(tcinque);
		
		tsei = new JTextField(1);
		tsei.setName("36");
		tsei.setBounds(680, 450, 20, 20);
		tsei.setEditable(false);
		finestraMappa.getContentPane().add(tsei);
		caselle.add(tsei);
	}
	
	private void setNorthAmerica() {
		
		uno = new JTextField(1);
		uno.setName("1");
		uno.setBounds(69, 90, 20, 20);	
		uno.setEditable(false);
		finestraMappa.getContentPane().add(uno);
		caselle.add(uno);
		
		due = new JTextField(1);
		due.setName("2");
		due.setBounds(97, 75, 20, 20);
		due.setEditable(false);
		finestraMappa.getContentPane().add(due);
		caselle.add(due);
		
		tre = new JTextField(1);
		tre.setName("3");
		tre.setBounds(165, 122, 20, 20);
		tre.setEditable(false);
		finestraMappa.getContentPane().add(tre);
		caselle.add(tre);

		quattro = new JTextField(1);
		quattro.setName("4");
		quattro.setBounds(69, 145, 20, 20);
		quattro.setEditable(false);
		finestraMappa.getContentPane().add(quattro);
		caselle.add(quattro);
		
		cinque = new JTextField(1);
		cinque.setName("5");
		cinque.setBounds(140, 165, 20, 20);
		cinque.setEditable(false);
		finestraMappa.getContentPane().add(cinque);
		caselle.add(cinque);
		
		sei = new JTextField(1);
		sei.setName("6");
		sei.setBounds(191, 192, 20, 20);
		sei.setEditable(false);
		finestraMappa.getContentPane().add(sei);
		caselle.add(sei);
		
		sette = new JTextField(1);
		sette.setName("7");
		sette.setBounds(49, 185, 20, 20);
		sette.setEditable(false);
		finestraMappa.getContentPane().add(sette);
		caselle.add(sette);
		
		otto = new JTextField(1);
		otto.setName("8");
		otto.setBounds(105, 227, 20, 20);
		otto.setEditable(false);
		finestraMappa.getContentPane().add(otto);
		caselle.add(otto);
		
		nove = new JTextField(1);
		nove.setName("9");
		nove.setBounds(48, 236, 20, 20);
		nove.setEditable(false);
		finestraMappa.getContentPane().add(nove);
		caselle.add(nove);
		
	}

	//Cambio il formato del colore
	private Color changeFormatColor(String colore) {
		
		Color c = null;
		
		switch (colore) {
			case "azzurro":
				c = Color.CYAN;
				break;
			case "verde":
				c = Color.GREEN;
				break;
			case "giallo":
				c = Color.YELLOW;
				break;
			case "bianco":
				c = Color.WHITE;
				break;
			case "magenta":
				c = Color.MAGENTA;
				break;
			case "arancio":
				c = Color.ORANGE;
				break;
		}
		
		return c;
	}
	
	//Coloro i territori
	public void coloraMappa(ArrayList<String> territori) {
		this.territori = territori;
		terr = this.territori.iterator();;
		Iterator<String> it = territori.iterator();
		Color coloreScelto = changeFormatColor(colore);
		
		System.out.println("Colore di "+user+": "+coloreScelto);
		while (it.hasNext()) {
			switch (it.next()) {
			case "1":
				uno.setBackground(coloreScelto);
				uno.setEditable(true);
				break;
			case "2":
				due.setBackground(coloreScelto);
				due.setEditable(true);
				break;
			case "3":
				tre.setBackground(coloreScelto);
				tre.setEditable(true);
				break;
			case "4":
				quattro.setBackground(coloreScelto);
				quattro.setEditable(true);
				break;
			case "5":
				cinque.setBackground(coloreScelto);
				cinque.setEditable(true);
				break;
			case "6":
				sei.setBackground(coloreScelto);
				sei.setEditable(true);
				break;
			case "7":
				sette.setBackground(coloreScelto);
				sette.setEditable(true);
				break;
			case "8":
				otto.setBackground(coloreScelto);
				otto.setEditable(true);
				break;
			case "9":
				nove.setBackground(coloreScelto);
				nove.setEditable(true);
				break;
			case "10":
				dieci.setBackground(coloreScelto);
				dieci.setEditable(true);
				break;
			case "11":
				undici.setBackground(coloreScelto);
				undici.setEditable(true);
				break;
			case "12":
				dodici.setBackground(coloreScelto);
				dodici.setEditable(true);
				break;
			case "13":
				tredici.setBackground(coloreScelto);
				tredici.setEditable(true);
				break;
			case "14":
				quattordici.setBackground(coloreScelto);
				quattordici.setEditable(true);
				break;
			case "15":
				quindici.setBackground(coloreScelto);
				quindici.setEditable(true);
				break;
			case "16":
				sedici.setBackground(coloreScelto);
				sedici.setEditable(true);
				break;
			case "17":
				diciassette.setBackground(coloreScelto);
				diciassette.setEditable(true);
				break;
			case "18":
				diciotto.setBackground(coloreScelto);
				diciotto.setEditable(true);
				break;
			case "19":
				diciannove.setBackground(coloreScelto);
				diciannove.setEditable(true);
				break;
			case "20":
				venti.setBackground(coloreScelto);
				venti.setEditable(true);
				break;
			case "21":
				vuno.setBackground(coloreScelto);
				vuno.setEditable(true);
				break;
			case "22":
				vdue.setBackground(coloreScelto);
				vdue.setEditable(true);
				break;
			case "23":
				vtre.setBackground(coloreScelto);
				vtre.setEditable(true);
				break;
			case "24":
				vquattro.setBackground(coloreScelto);
				vquattro.setEditable(true);
				break;
			case "25":
				vcinque.setBackground(coloreScelto);
				vcinque.setEditable(true);
				break;
			case "26":
				vsei.setBackground(coloreScelto);
				vsei.setEditable(true);
				break;
			case "27":
				vsette.setBackground(coloreScelto);
				vsette.setEditable(true);
				break;
			case "28":
				votto.setBackground(coloreScelto);
				votto.setEditable(true);
				break;
			case "29":
				vnove.setBackground(coloreScelto);
				vnove.setEditable(true);
				break;
			case "30":
				trenta.setBackground(coloreScelto);
				trenta.setEditable(true);
				break;
			case "31":
				tuno.setBackground(coloreScelto);
				tuno.setEditable(true);
				break;
			case "32":
				tdue.setBackground(coloreScelto);
				tdue.setEditable(true);
				break;
			case "33":
				ttre.setBackground(coloreScelto);
				ttre.setEditable(true);
				break;
			case "34":
				tquattro.setBackground(coloreScelto);
				tquattro.setEditable(true);
				break;
			case "35":
				tcinque.setBackground(coloreScelto);
				tcinque.setEditable(true);
				break;
			case "36":
				tsei.setBackground(coloreScelto);
				tsei.setEditable(true);
				break;
			case "37":
				tsette.setBackground(coloreScelto);
				tsette.setEditable(true);
				break;
			case "38":
				totto.setBackground(coloreScelto);
				totto.setEditable(true);
				break;
			case "39":
				tnove.setBackground(coloreScelto);
				tnove.setEditable(true);
				break;
			case "40":
				quaranta.setBackground(coloreScelto);
				quaranta.setEditable(true);
				break;
			case "41":
				quno.setBackground(coloreScelto);
				quno.setEditable(true);
				break;
			case "42":
				qdue.setBackground(coloreScelto);
				qdue.setEditable(true);
				break;
			}
		}
	}
	
	
	public void setArmate(Integer numeroArmate){
		this.numeroArmate = numeroArmate;
		armateResidue.setText("Disponibili: "+this.numeroArmate+" armate");
	}
	
	public String checkPosArmate(GestioneComandiPartita comandi){
		
		this.comandi = comandi;
		//Gestir� inserimento in casella
		
		System.out.println("SONO IN CHECKPOSARMATE");
		//chiedi ad Antonio cosa gli faceva fare
		if(comandi.getSocket() == null){
			int val = 0;
			while(val == 0){
				try{
					Thread.sleep(5000);
					if(getStato().compareTo("null")!=0){
						val = 1;
					}
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		return stato;
	}
	
	private String getStato() {
		return stato;
	}
	
	//Aggiorna le caselle con le nuove armate
	public String aggiorna(String comandoDaEseguire) {
		
		String[] supporto = new String[42];
		iteratoreCaselle = caselle.iterator();
		System.out.println("COMANDO DA ESEGUIRE IN AGGIORNA: "+comandoDaEseguire);
		supporto = comandoDaEseguire.split(";");
		int i = 0;
		
		while(iteratoreCaselle.hasNext()){
			iteratoreCaselle.next().setText(supporto[i]);
			i++;
		}
		
		return "true";	
	}

	//Posizionamento delle armate
	public String posizionaArmate(GestioneComandiPartita comandi) {
		
		System.out.println("----> SONO IN POSIZIONA ARMATE <----");
		this.comandi = comandi;
		salva.setEnabled(true);
		JOptionPane.showMessageDialog(finestraMappa, "60sec per posizionare");
		//timer.start();
		
//		taskTimer = new TaskTimer(this, 60);
//		Thread thread = new Thread(taskTimer);
//		thread.start();
		
//		while (timeOut == false) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		taskTimer.stoppaTimer();
//		salva.doClick();
		
		return "true";
	}
	
	//Attacco
	public String territorioDaAttaccare(GestioneComandiPartita comandi) {
		
		this.comandi = comandi;
		
		//qua far� partite il timer dei 30sec
//		taskTimer = new TaskTimer(this, 30);
//		Thread thread = new Thread(taskTimer);
//		thread.start();
//		
//		while (timeOut == false) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		verificaContatore();
		
		return "true";
	}
	
	//Setto il men� a tendina d'attacco
	public String setTerritoriGiocata(String territori) {
		attaccoA.setEnabled(true);
		attaccoDa.setEnabled(false);
		String[] temp = new String[42];
		temp = territori.split(";");
		int i = 0;
		
		for (i = 0; i < temp.length; i++) {
			attaccoA.addItem(temp[i]);
		}
		
		
		return "true";
	}
	
	//Controllo validit� pedine con cui attacco, dato lo statoAttaccante
	private int checkPedine(String statoAttacco, int pedineGiocata) {
		iteratoreCaselle = caselle.iterator();
		JTextField temp = null;
		int ritorno = 0;
		while (iteratoreCaselle.hasNext()) {
			temp = iteratoreCaselle.next();
			if (temp.getName().equals(statoAttacco)) {
				if (Integer.parseInt(temp.getText()) - pedineGiocata >= 1) {
					ritorno = pedineGiocata;
				}
				else {
					ritorno = 0;
				}
				/*else {
					if (Integer.parseInt(temp.getText()) > 3) {
						ritorno = 3;
					}
					else if (Integer.parseInt(temp.getText()) == 3) {
						ritorno = 2;
					}
					else if (Integer.parseInt(temp.getText()) == 2) {
						ritorno = 1;
					}
					else if (Integer.parseInt(temp.getText()) == 1) {
						ritorno = 0;
					}
				}*/
			}
		}
		pedineAttaccante = ritorno;
		return ritorno;
	}
	
	//Controllo validit� pedine con cui difendo, dato lo statoDifensore
	public int checkValiditaArmateDifesa(int pedineDifesa, String dif) {
		
		this.difesa = dif;
		
		JTextField temp = null;

		System.out.println("Difesa: "+difesa);
		
		iteratoreCaselle = caselle.iterator();
		
		while (iteratoreCaselle.hasNext()) {
			temp = iteratoreCaselle.next();
			if (temp.getName().equals(difesa)) {
				if (Integer.parseInt(temp.getText()) < pedineDifesa) {
					System.out.println("Mine: "+pedineDifesa);
					System.out.println("Old: "+temp.getText());
					return 1;
				}
			}
		}
		
		return pedineDifesa;
	}
	
	//Prendo la scelta delle pedine del difensore
	public int difenditi(int pedineAttaccante) {
		this.pedineAttaccante = pedineAttaccante;
		String[] pedineDifesa = {"1", "2"};
		int n = JOptionPane.showOptionDialog(finestraMappa, "Sotto attacco da: "+pedineAttaccante+" pedine.\nScegli la tua difesa:", "Pedine difesa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, pedineDifesa, pedineDifesa[0]);
		//se n == 0 -> ritorno 1;
		//se n == -1 -> ritorno 1;
		//se n == 1 --> ritorno 2;
		if (n == 0) {
			pedineDifensore = 1;
			return 1;
		}
		if (n == 1) {
			pedineDifensore = 2;
			return 2;
		}
		if (n == -1) {
			pedineDifensore = 1;
		}
		return 1;
	}
	
	/*private int calcolaMassimo(String[] valoreDadi) {

		int i = 0;
		
		int max = Integer.parseInt(valoreDadi[0]);
		
		for (i = 1; i < valoreDadi.length; i++) {
			if (max < Integer.parseInt(valoreDadi[i])) {
				max = Integer.parseInt(valoreDadi[i]);
			}
		}
		
		for (i = 0; i < valoreDadi.length; i++) {
			if (max == Integer.parseInt(valoreDadi[i])) {
				valoreDadi[i] = "0";
			}
		}
		
		return max;
	}*/
	
	
	//Disegna i dadi
	public void valoreDadi(String[] dadiAttacco, String[] dadiDifesa) {
		
		Font font = new Font("Courier", Font.BOLD, 48);

		dAUno.setForeground(Color.WHITE);
		dADue.setForeground(Color.WHITE);
		dATre.setForeground(Color.WHITE);
		dDUno.setForeground(Color.WHITE);
		dDDue.setForeground(Color.WHITE);
		
		dAUno.setText(dadiAttacco[0]);
		dADue.setText(dadiAttacco[1]);
		dATre.setText(dadiAttacco[2]);
		
		dDUno.setText(dadiDifesa[0]);
		dDDue.setText(dadiDifesa[1]);
		
		dAUno.setFont(font);
		dADue.setFont(font);
		dATre.setFont(font);
		dDUno.setFont(font);
		dDDue.setFont(font);

		dAUno.setHorizontalAlignment(JTextField.CENTER);
		dADue.setHorizontalAlignment(JTextField.CENTER);
		dATre.setHorizontalAlignment(JTextField.CENTER);
		dDUno.setHorizontalAlignment(JTextField.CENTER);
		dDDue.setHorizontalAlignment(JTextField.CENTER);
		
	}
	
	//Resetto i colori alle soluzioni originarie
	/*public void resetColor(String indiceStato, String colore) {
		
		iteratoreCaselle = caselle.iterator();
		JTextField temp = null;
		int trovato = 0;
		
		while (iteratoreCaselle.hasNext() && trovato == 0) { 
			temp = iteratoreCaselle.next();
			if (temp.getName().equals(indiceStato)) {
				if (colore != null)
					temp.setBackground(this.changeFormatColor(colore));
				else 
					temp.setBackground(null);
			}
		}
	}*/
	
	
	/* 
	 * LATO ATTACCO: se ritorna ->true<- so che HO CONQUISTATO
	 * LATO DIFESA: se ritorna ->true<- so che HO PERSO
	 */
	public String esitoAttacco(String doveSono, String comando, int pedineAttaccante, int pedineDifensore, String colore) {
		
		Color c = this.changeFormatColor(colore);
		String conquista = "false";
		
		System.out.println("-->SONO DENTRO esitoAttacco. Difesa: "+difesa+" Attacco: "+statoAttacco+" Colore: "+c.toString());
		System.out.println("PATT: "+pedineAttaccante);
		System.out.println("PDIF: "+pedineDifensore);
		//Sono l'attaccante
		this.pedineAttaccante = pedineAttaccante;
		this.pedineDifensore = pedineDifensore;
				
		int vittorieAttacco = 0;
		int i = 0;
		String[] esito = comando.split(";");
		
		//Conto il numero di volte che c'� la parola attacco
		for (i = 0; i < pedineDifensore; i++) {
			if (esito[i].equals("attacco")) {
				vittorieAttacco++;
			}
		}
		
		JTextField temp = null;
		int trovato = 0;
		
		System.out.println("VATT: "+vittorieAttacco);
		
		if (vittorieAttacco == 0) {
			JOptionPane.showMessageDialog(finestraMappa, "Attaccante: hai perso "+(pedineDifensore)+" armate.\nDifensore: hai perso 0 armate.");
		}
		else if (vittorieAttacco == 1) {
			if (pedineDifensore == 1) {
				//Conquisto
				JOptionPane.showMessageDialog(finestraMappa, "Attaccante: hai perso 0 armate.\nDifensore: hai perso "+(pedineDifensore)+" armate.");
				iteratoreCaselle = caselle.iterator();
				while (iteratoreCaselle.hasNext()) {
					temp = iteratoreCaselle.next();
					if (doveSono.equals("attacco")) {
						if (temp.getName().equals(difesa)) {
							if ((Integer.parseInt(temp.getText()) - 1) <= 0) {
								conquista = "true";
								temp.setEditable(true);
								temp.setBackground(c);
								temp.setBackground(c);
								temp.setEditable(false);
							}
						}
						if (temp.getName().equals(statoAttacco)) {
							temp.setEditable(true);
							temp.setBackground(c);
							temp.setBackground(c);
							temp.setEditable(false);
						}
					}
					else if (doveSono.equals("difesa")) {
						if (temp.getName().equals(difesa)) {
							if ((Integer.parseInt(temp.getText()) - 1) <= 0) {
								conquista = "true";
								temp.setEditable(true);
								temp.setBackground(null);
								temp.setBackground(null);
								temp.setEditable(false);
							}
						}
						if (temp.getName().equals(statoAttacco)) {
							temp.setEditable(true);
							temp.setBackground(null);
							temp.setBackground(null);
							temp.setEditable(false);
						}
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(finestraMappa, "Attaccante: hai perso "+(pedineAttaccante-1)+" armate.\nDifensore: hai perso"+(pedineDifensore-1)+"armate.");
			}
		}
		else {
			JOptionPane.showMessageDialog(finestraMappa, "Attaccante: hai perso 0 armate.\nDifensore: hai perso "+(pedineDifensore)+" armate.");
			iteratoreCaselle = caselle.iterator();
			while (iteratoreCaselle.hasNext()) {
				temp = iteratoreCaselle.next();
				if (doveSono.equals("attacco")) {
					if (temp.getName().equals(difesa)) {
						if ((Integer.parseInt(temp.getText()) - 2) <= 0) {
							conquista = "true";
							temp.setEditable(true);
							temp.setBackground(c);
							temp.setBackground(c);
							temp.setEditable(false);
						}
						else {
							temp.setEditable(true);
							temp.setBackground(null);
							temp.setBackground(null);
							temp.setEditable(false);
						}
					}
					if (temp.getName().equals(statoAttacco)) {
						temp.setEditable(true);
						temp.setBackground(c);
						temp.setBackground(c);
						temp.setEditable(false);
					}
				}
				if  (doveSono.equals("difesa")) {
					if (temp.getName().equals(difesa)) {
						if ((Integer.parseInt(temp.getText()) - 2) <= 0) {
							conquista = "true";
							temp.setEditable(true);
							temp.setBackground(null);
							temp.setBackground(null);
							temp.setEditable(false);
						}
						else {
							temp.setEditable(true);
							temp.setBackground(c);
							temp.setBackground(c);
							temp.setEditable(false);
						}
					}	
					if (temp.getName().equals(statoAttacco)) {
						temp.setEditable(true);
						temp.setBackground(null);
						temp.setBackground(null);
						temp.setEditable(false);
					}				
				}
			}
		}
			
		System.out.println("-->SONO FUORI esitoAttacco");
		
		return conquista;
	}
	
	//Colora gli stati in base agli stati passati come parametro	
	public void coloraMappaMossa(String chiAttacca, String chiDifende) {
		iteratoreCaselle = caselle.iterator();
		this.difesa = chiDifende;
		this.statoAttacco = chiAttacca;
		JTextField temp = null;
		
		//CHI ATTACCA
		int trovato = 0;
		while (iteratoreCaselle.hasNext() && trovato == 0) {
			temp = iteratoreCaselle.next();
			if (chiAttacca.equals(temp.getName())) {
				temp.setBackground(Color.RED);
				trovato = 1;
			}
		}
		
		//CHI DIFENDE
		trovato = 0;
		iteratoreCaselle = caselle.iterator();
		while (iteratoreCaselle.hasNext() && trovato == 0) {
			temp = iteratoreCaselle.next();
			if (chiDifende.equals(temp.getName())) {
				temp.setBackground(Color.BLUE);
				trovato = 1;
			}
		}
	}
	
	//Settin iniziale dei radioButton
	public void interazioneMosse() {

//		taskTimer.stoppaTimer();
		
		JOptionPane.showMessageDialog(finestraMappa, "� il tuo turno, 30sec per fare la mossa");
		
//		verificaContatore();
		

		
		
		
		attacca.setEnabled(true);
		//attacca.setSelected(false);
		
		sposta.setEnabled(true);
		//sposta.setSelected(false);
		
		fineTurno.setEnabled(true);
		
		//fineTurno.setSelected(false);
		
		
		
	}
	
	//Reset della grafica d'attacco
	
	public void resetDadi() {
		
		dAUno.setText("");
		dADue.setText("");
		dATre.setText("");
		dDUno.setText("");
		dDDue.setText("");
		
	}
	
	public void reSetBottoneSposta() {
		
		attacca.setEnabled(false);
		fineTurno.setEnabled(false);
		sposta.setEnabled(true);
	}
	
	public void resetMosse() {
		
		attaccoDa.removeAllItems();
		attaccoDa.addItem("");
		this.setTerritoriGiocata();
		
		attaccoA.removeAllItems();
		attaccoA.addItem("");
		
		pedineMossa.setText("");
		pedineMossa.setBorder(new TitledBorder("Quante pedine vuoi?"));
		pedineMossa.setEditable(false);
		
		eseguiMossa.setEnabled(false);
	
	}
		//qua far� partite il timer dei 30sec
//		taskTimer = new TaskTimer(this, 30);
//		Thread thread = new Thread(taskTimer);
//		thread.start();
	
	/*public void verificaContatore() {
//		
//		timeOut = false;
//		
//		while (timeOut == false) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
//		taskTimer.stoppaTimer();
//		mossa = "fine";
//		eseguiMossa.doClick();
		
//	}*/
	
	
	/*private void sottoAttacco(String sottoAttacco) {
		iteratoreCaselle = caselle.iterator();
		JTextField temp = null;
		System.out.println("Territorio che attacca: "+sottoAttacco);
		System.out.println("Territorio che difende: "+difesa);
		//CHI ATTACCA
		int trovato = 0;
		while (iteratoreCaselle.hasNext() && trovato == 0) {
			temp = iteratoreCaselle.next();
			if (sottoAttacco.equals(temp.getName())) {
				temp.setBackground(Color.RED);
				trovato = 1;
			}
		}
		
		//CHI DIFENDE
		trovato = 0;
		iteratoreCaselle = caselle.iterator();
		while (iteratoreCaselle.hasNext() && trovato == 0) {
			temp = iteratoreCaselle.next();
			if (difesa.equals(temp.getName())) {
				temp.setBackground(Color.BLUE);
				trovato = 1;
			}
		}
	}*/
	
	
	class AscoltatoreEseguiMossa implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
//			taskTimer.stoppaTimer();
			// TODO Auto-generated method stub
			if (!(mossa.startsWith("fine"))) {			
				try {
					pedineGiocata = Integer.parseInt(pedineMossa.getText());	
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(finestraMappa, "Selezionare un numero valido, grazie.", "Errore selezione pedine", JOptionPane.ERROR_MESSAGE);
				}
				
				if (pedineGiocata >= 1) {
					pedineMossa.setBorder(null);
					if (pedineGiocata <= 3) {
						pedineMossa.setBorder(null);
						if (checkPedine(statoAttacco, pedineGiocata) >= 1) {
							pedineMossa.setBorder(null);
							pedineMossa.setEditable(false);
							comandi.setPedine(checkPedine(statoAttacco, pedineGiocata));
							//sottoAttacco(statoAttacco);
							eseguiMossa.setEnabled(false);
							if (mossa.equalsIgnoreCase("attacco")) 
								comandi.setResult("attacco@"+statoAttacco+";"+difesa+";"+pedineGiocata);
							else if (mossa.equalsIgnoreCase("sposta"))
								comandi.setResult("sposta@"+difesa+";"+pedineGiocata);
							else 
								comandi.setResult("fine@");
								
						}
						else {
							JOptionPane.showMessageDialog(finestraMappa, "Deve rimanere una pedina\na difesa del territorio");
							pedineMossa.setBorder(new EtchedBorder(Color.RED, Color.RED));
						} 
					}
					else {
						JOptionPane.showMessageDialog(finestraMappa, "Troppe pedine");
						pedineMossa.setBorder(new EtchedBorder(Color.RED, Color.RED));
					}
				}
				else {
					JOptionPane.showMessageDialog(finestraMappa, "Poche pedine");
					pedineMossa.setBorder(new EtchedBorder(Color.RED, Color.RED));
				}
			}
			else {
				JOptionPane.showMessageDialog(finestraMappa, "Fine turno!");
				
				dAUno.setText("");
				dADue.setText("");
				dATre.setText("");
				dDUno.setText("");
				dDDue.setText("");
				eseguiMossa.setEnabled(false);
				comandi.setTerritorioPerMossa("fine@");
			}
		}
		
	}

	class AscoltatoreAttaccoDa implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			// TODO Auto-generated method stub
			//considero il codice di ci� che ho selezionato nel
			//menu a tendina
			//System.out.println("Mossa: "+mossa);
			attacca.setEnabled(false);
			sposta.setEnabled(false);
			fineTurno.setEnabled(false);
			
			if (!event.getItem().toString().equals("")) {	
				if (event.getStateChange() == ItemEvent.SELECTED) {
					//System.out.println("Evento: "+event.getItem());
					
					String statoScelto = event.getItem().toString();
					
					iteratoreCaselle = caselle.iterator();
					JTextField temp = null;
					boolean prosegui = true;
					int trovato = 0;
					
					if (!(mossa.startsWith("fine"))) {
						while (iteratoreCaselle.hasNext() && trovato == 0) {
							temp = iteratoreCaselle.next();
							if (temp.getName().equals(statoScelto)) {
								if (Integer.parseInt(temp.getText()) == 1) {
									prosegui = false;
									trovato = 1;
								}
							}
						}
					}
					
					if (prosegui) {
						System.out.println("Ho selezionato: "+mossa);
						/* A questo punto devo inviare il territtorio */
						if (mossa.startsWith("attacca")) {
							statoAttacco = event.getItem().toString();
							if (errore == 1) { //significa che non ci sono stati su cui posso sviluppare l'attacco
								System.out.println("Nuova selezione --> "+"attacca@"+event.getItem());
								comandi.getScrittore().println("attacca@"+event.getItem());
								comandi.getScrittore().flush();

								try {
									//dovrei leggereSpostabili
									setAttaccoA(comandi.getReader().readLine().substring(11));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}								
							}
							else {
								comandi.setTerritorioPerMossa("attacca@"+event.getItem());
							}
						}
						else if (mossa.startsWith("sposta")) {
							statoAttacco = event.getItem().toString();
							if (errore == 1) { //significa che non ci sono stati su cui spostare
								System.out.println("Nuova selezione --> "+"sposta@"+event.getItem());
								comandi.getScrittore().println("sposta@"+event.getItem());
								comandi.getScrittore().flush();

								try {
									//dovrei leggereSpostabili
									setAttaccoA(comandi.getReader().readLine().substring(11));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}	
							}
							else {
								comandi.setTerritorioPerMossa("sposta@"+event.getItem());
							}
						}
						errore = 0;
					}
					else {
						JOptionPane.showMessageDialog(finestraMappa, "Territorio inagibile in quanto non\navresti armate in difesa", "Errore selezione pedine", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		}
		
	}
	
	
	class AscoltatoreAttaccoA implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			// TODO Auto-generated method stub
			//considero il codice di ci� che ho selezionato nel
			//menu a tendina
			if (!event.getItem().toString().equals("")) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Evento: "+event.getItem());
					/* A questo punto devo inviare il territtorio */
					difesa = event.getItem().toString();
					comandi.setDifesa(event.getItem().toString());
			    }
			}
			
			attaccoA.setEnabled(false);
			pedineMossa.setEditable(true);
			eseguiMossa.setEnabled(true);
		}
	}
	
	//ASCOLTATORE TIMER
	
	class AscoltatoreTimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent timerScattato) {
			try {
				Thread.sleep(1000);
				contatoreTimer++;
				if (contatoreTimer > 60) {
					try {
						throw new FaultPositionArmate(timer);
					} catch (FaultPositionArmate e) {
						timer.stop();
						JOptionPane.showMessageDialog(finestraMappa, "Tempo scaduto");
						System.out.println(e.getMessage());
						System.out.println("Comandi: "+comandi);
						System.out.println("Timer: "+timer);
						salva.setEnabled(false);
						
						if(comandi.getSocket()!=null){
							comandi.getScrittore().println("posizionate@false");
							comandi.getScrittore().flush();							
						}
					}
				}
			} catch (InterruptedException e1) {
				System.out.println("Sleep: "+e1.getMessage());
			}
		}
		
	}
	
	class AscoltatoreSalvataggioArmate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent casellaRiempita) {
			
			salva.setEnabled(false);
			
			//timer.stop();
			
//			taskTimer.stoppaTimer();
			
			iteratoreCaselle = caselle.iterator();
			JTextField temp = null;
			Boolean e1 = true,e2 = true;
			int valoreCasella = 0;
			int sommaTotale = 0;
			
			if(casellaRiempita.getActionCommand().equalsIgnoreCase("salva")){
				daInviare = "posizionate@";
				//aggiungere i valori booleani e1 ed e2
				//gestire l'eccezione correttamente
				while (iteratoreCaselle.hasNext()) {
					temp = iteratoreCaselle.next();
					if (temp.isEditable()) {
						try{
							valoreCasella = Integer.parseInt(temp.getText());
						}
						catch(NumberFormatException e){
							//JOptionPane.showMessageDialog(finestraMappa, "Ci vuole almeno una pedina per stato");
							e1 = false;
						}
						if (valoreCasella < 1) {
							e1 = false;
						}
						else {
							sommaTotale += valoreCasella;
							if (sommaTotale > numeroArmate) {
								e2 = false;
							}
							else {
								daInviare = daInviare+valoreCasella+";";
							}
						}
						temp.setEditable(false);
					}
					else {
						daInviare = daInviare+"0;";
					}
				}
				System.out.println("DA INVIARE: "+daInviare);
				
				if(!e1) JOptionPane.showMessageDialog(finestraMappa, "Inseriti valori negativi e/o mancata copertura di tutti i territori");
				if(!e2) JOptionPane.showMessageDialog(finestraMappa, "Troppe armate inserite");
				if(!e1||!e2) JOptionPane.showMessageDialog(finestraMappa, "Posizionamento automatico");
				if(e1&&e2){
					if(comandi.getSocket()!=null){
						comandi.getScrittore().println(daInviare);
						comandi.getScrittore().flush();
					}
					stato = daInviare;
				}
				else {
					if(comandi.getSocket()!=null){
						comandi.getScrittore().println("posizionate@false");
						comandi.getScrittore().flush();
					}
					stato = "posizionate@false";
				}
				comandi.setResult(stato);
				salva.setText("Ricarica");
			}
			else{
				int somma = 0;
				int i = 0;
				String risposta = "ricaricate@";
				iteratoreCaselle = caselle.iterator();
				
				
				for(i = 0; i < 42; i++){
					temp = iteratoreCaselle.next();
					if(temp.isEditable()){
						System.out.println("temp.getText(): "+temp.getText()+"\t vecchiePedine["+i+"]"+vecchiePedine[i]);
						somma+= Integer.parseInt(temp.getText())-Integer.parseInt(vecchiePedine[i]);	
						risposta+=temp.getText()+";";
					}
					else{
						risposta+="0;";
					}
				}
				if(somma>numeroArmate){
					risposta = "ricaricate@";
					JOptionPane.showMessageDialog(finestraMappa, "Inserite troppe armate\nRicarica persa", "Ricarica persa", JOptionPane.WARNING_MESSAGE);
					for(i = 0; i < 42; i++){
						risposta+=vecchiePedine[i]+";";
					}
				}

				System.out.println("Ricaricate --> "+risposta);
				
				if(comandi.getSocket()!=null){							//CASO SOCKET
					comandi.getScrittore().println(risposta);
					comandi.getScrittore().flush();
				}	
				
					//INVIO CASO RMI
				comandi.setResultFittizio(risposta);	
				
			}
			System.out.println("CAMBIO VALORE ESITO");
			comandi.esito = "true";
			System.out.println("VALORE ESITO: "+comandi.esito);
		}
	}

	class AscoltatoreTipoMossa implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent mossaSelezionata) {
			// TODO Auto-generated method stub
			mossa = mossaSelezionata.getActionCommand();
			mossa = mossa.toLowerCase();
			System.out.println("-->: "+mossa);
			attacca.setEnabled(false);
			sposta.setEnabled(false);
			fineTurno.setEnabled(false);
			
			if (mossa.startsWith("fi")) {
				attaccoDa.setEnabled(false);
				eseguiMossa.setEnabled(true);
			}
			else {
				setTerritoriGiocata();
				attaccoDa.setEnabled(true);
			}
		}
		
	}

	public String[] getPedine() {
		iteratoreCaselle = caselle.iterator();
		String[] supporto = new String[42];
		JTextField temp = null;
		int i = 0;
		
		while(iteratoreCaselle.hasNext()){
			temp = iteratoreCaselle.next();
			if(temp.isEditable()){
				supporto[i] = temp.getText();
			}
			else{
				supporto[i] = "0";
			}
			i++;
		}
		return supporto;
	}

	public void setArmate(int numeroArmate, String[] vecchiaPosizione) {
		this.salva.setEnabled(true);
		this.numeroArmate = numeroArmate;
		this.vecchiePedine = vecchiaPosizione;
		armateResidue.setText("Disponibili: "+this.numeroArmate+" armate");
	}

	public ArrayList<JTextField> getCaselle() {
		
		return this.caselle;
	}
	
	class AscoltatoreFinestra implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			//rimozione dalla lista della partita
			
			//chiudere la partita
			//esecuzione query DB
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
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
