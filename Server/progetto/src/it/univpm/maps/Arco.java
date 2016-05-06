package it.univpm.maps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Arco {

	//attributi
	private double v; //vulnerabilità del tratto
	private double i; //rischi per la vita
	private double los; //presenza di persone
	private double c; //dati variabili
	private double lunghezza; //lunghezza arco in metri
	String partenza; //id nodo partenza (foreign key)
	String destinazione; //id nodo destinazione (foreign key)
	private double superficie; //superficie arco in mq
	
	
	//costruttore senza parametri
	public Arco(){
		
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
