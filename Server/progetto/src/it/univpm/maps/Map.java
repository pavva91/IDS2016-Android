package it.univpm.maps;

import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Map {
	//attributi
	@XmlElement
	private String name;
	@XmlElement
	private ArrayList<Node> nodes = new ArrayList<Node>();
	@XmlElement
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	@XmlElement
	private ArrayList<String> images = new ArrayList<String>();
	@XmlElement
	private Date lastUpdateMap;

	//costruttore 
	public Map(String name){
		this.name=name;
	}
	//costruttore senza parametri
	public Map(){
		
	}
	
	//getters e setters
	public String getNome(){
		return this.name;
	}
	public void setNome(String name){
		this.name=name;
	}
	public ArrayList<Node> getNodi(){
		return this.nodes;		
	}
	public void setNodi(ArrayList<Node> nodes){
		this.nodes=nodes;
	}
	public ArrayList<Edge> getArchi(){
		return this.edges;		
	}
	public void setArchi(ArrayList<Edge> edges){
		this.edges=edges;
	}
	public void AggiungiNodo(Node n){
		this.nodes.add(n);
	}
	public void AggiungiArco(Edge e){
		this.edges.add(e);
	}
	public void AggiungiImmagine(String s){
		this.images.add(s);
	}
	public void setImmagini(ArrayList<String> images){
		this.images=images;
	}
	public ArrayList<String> getImmagini(){
		return this.images;
	}
	public void setLastUpdateMap(Date lastUpdateMap){
		this.lastUpdateMap=lastUpdateMap;
	}
	public Date getLastUpdateMap(){
		return this.lastUpdateMap;
	}
	
}
