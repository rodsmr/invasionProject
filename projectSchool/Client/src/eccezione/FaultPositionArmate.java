package eccezione;

import javax.swing.Timer;

public class FaultPositionArmate extends Exception {

	public FaultPositionArmate(Timer timer) {
		super("Posizionamento automatico");
		timer.stop();
	}
}
