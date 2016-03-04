package partita;

import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JLabel;


/**
 * Classe Dado che simula un dado reale
 */
public class Dado extends JComponent {
	
	/** The random. */
	private Random random = null;
	
	/**
	 * Costruttore di dado
	 */
	public Dado() {
		random = new Random();
	}
	
	/**
	 * Funzione che simula il lancio di un dado
	 *
	 * @return un valore compreso tra 1 e 6
	 */
	public int lanciaDado() {
		return random.nextInt(6)+1;
	}

}
