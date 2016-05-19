package it.univpm.maps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class Edge {

	//attributi
	private double v; //vulnerabilità del tratto
	private double i; //rischi per la vita
	private double los; //presenza di persone
	private double c; //dati variabili
	private double length; //lunghezza arco in metri
	private String nodeFrom; //id nodo partenza (foreign key)
	private String nodeTo; //id nodo destinazione (foreign key)
	private double area; //superficie arco in mq
	private int numPers;//numero di persone presenti
	
	
	//costruttore senza parametri
	public Edge(){
		
	}
	//getters e setters
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
	public double getLength() {
		return this.length;
	}
	public void setLunghezza(double lunghezza) {
		this.length = lunghezza;
	}
	public String getPartenza() {
		return this.nodeFrom;
	}
	public void setPartenza(String partenza) {
		this.nodeFrom = partenza;
	}
	public String getDestinazione() {
		return this.nodeTo;
	}
	public void setDestinazione(String destinazione) {
		this.nodeTo = destinazione;
	}
	public double getSuperficie() {
		return this.area;
	}
	public void setSuperficie(double superficie) {
		this.area=superficie;
	}
	public int getNumPersone(){
		return this.numPers;
	}
	public void setNumPersone(int numPersone){
		this.numPers=numPersone;
	}
}
