package it.univpm.maps;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD) 
public class Nodo {

	//attributi
	@XmlElement
	private String mappa; //mappa a cui appartiene il nodo
	@XmlElement
	private int id; //codice indentificativo nodo
	@XmlElement
	private String codice; //codice nodo
	private String descrizione; //nome esteso nodo ***** serve ? **************
	private int quota; //quota in metri s.l.m.
	private int x; //coordinata x
	private int y; //coordinata y
	private double larghezza; //larghezza in metri
	public enum tiponodo {uscita, uscita_emergenza, altro};
	private tiponodo tipo;
	
	//costruttore
	public Nodo(String codice, String desc, int quota, int x, int y, double larg, tiponodo t){
		this.codice=codice;
		this.descrizione=desc;
		this.quota=quota;
		this.x=x;
		this.y=y;
		this.larghezza=larg;
		this.tipo=t;
	}
	//costruttore senza parametri
	public Nodo(){
		
	}
	

	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getCodice(){
		return this.codice;
	}
	public void setCodice(String codice){
		this.codice=codice;
	}
	public String getDescrizione(){
		return this.descrizione;
	}
	public void setDescrizione(String desc){
		this.descrizione=desc;
	}
	public int getQuota(){
		return this.quota;
	}
	public void setQuota(int q){
		this.quota=q;
	}
	public int getX(){
		return this.x;
	}
	public void setX(int x){
		this.x=x;
	}
	public int getY(){
		return this.y;
	}
	public void setY(int y){
		this.y=y;
	}
	public double getLarghezza(){
		return this.larghezza;
	}
	public void setLarghezza(double larghezza){
		this.larghezza=larghezza;
	}
	public void setY(double l){
		this.larghezza=l;
	}
	public tiponodo getTipo(){
		return this.tipo;
	}
	public void setTipo(tiponodo t){
		this.tipo=t;
	}
	public void setMappa(String mappa){
		this.mappa=mappa;
	}
	public String getMappa(){
		return this.mappa;
	}
	
	
}
