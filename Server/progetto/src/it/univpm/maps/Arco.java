package it.univpm.maps;

public class Arco {

	//attributi
	private int id; //identificativo univoco dell'arco
	private String mappa; //nome mappa (foreign key)
	private double v; //vulnerabilità del tratto
	private double i; //rischi per la vita
	private double los; //presenza di persone
	private double c; //dati variabili
	private double lunghezza; //lunghezza arco in metri
	private String partenza; //id nodo partenza (foreign key)
	private String destinazione; //id nodo destinazione (foreign key)
	private double superficie; //superficie arco in mq
	
	
	//costruttore
	public Arco(String mappa, double v, double i, double los, double c, double lunghezza, String partenza, String destinazione, double superficie){
		this.mappa=mappa;
		this.v=v;
		this.c=c;
		this.i=i;
		this.los=los;
		this.lunghezza=lunghezza;
		this.partenza=partenza;
		this.destinazione=destinazione;
		this.superficie=superficie;
	}
	//costruttore senza parametri
	public Arco(){
		
	}

	public int getId() {
		return this.id;
	}
	public String getMappa() {
		return this.mappa;
	}
	public void setMappa(String mappa) {
		this.mappa = mappa;
	}
	public double getV() {
		return this.v;
	}
	public void setV(double v) {
		this.v = v;
	}
	public double getI() {
		return this.i;
	}
	public void setI(double i) {
		this.i = i;
	}
	public double getLos() {
		return this.los;
	}
	public void setLos(double los) {
		this.los = los;
	}
	public double getC() {
		return this.c;
	}
	public void setC(double c) {
		this.c = c;
	}
	public double getLunghezza() {
		return this.lunghezza;
	}
	public void setLunghezza(double lunghezza) {
		this.lunghezza = lunghezza;
	}
	public String getPartenza() {
		return this.partenza;
	}
	public void setPartenza(String partenza) {
		this.partenza = partenza;
	}
	public String getDestinazione() {
		return this.destinazione;
	}
	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}
	public double getSuperficie() {
		return this.superficie;
	}
	public void setSuperficie(double superficie) {
		this.superficie=superficie;
	}

	
	
	
}
