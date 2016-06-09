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
	private String from; //id nodo partenza (foreign key)
	private String to; //id nodo destinazione (foreign key)
	private double area; //superficie arco in mq
	private int numpers;//numero di persone presenti
	private double emgcost;//costo arco in caso di emergenza
	
	
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
	public void setLength(double lenght) {
		this.length = lenght;
	}
	public String getFrom() {
		return this.from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return this.to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public double getArea() {
		return this.area;
	}
	public void setArea(double area) {
		this.area=area;
	}
	public int getNumPers(){
		return this.numpers;
	}
	public void setNumPers(int numPers){
		this.numpers=numPers;
	}
	public double getEmgCost() {
		return this.emgcost;
	}
	public void setEmgCost(double cost) {
		this.emgcost=cost;
	}
}
