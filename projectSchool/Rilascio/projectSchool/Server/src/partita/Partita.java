package partita;

import java.util.ArrayList;

public abstract class Partita {
		public int contatore;
		public abstract void assegnaArmate();
		public abstract String posizionaArmate();
		public abstract void assegnaColore();
		public abstract String assegnaTerritori(ArrayList<Territorio> lista);
		public abstract ArrayList<Giocatore> getGiocatore();
		public abstract String[] getPosizioneTotale();
		public abstract Object getObject();
		public abstract int getContatore();
		public abstract void setContatore(int contatore);
		public abstract ArrayList<Giocatore> getClassifica();
}
