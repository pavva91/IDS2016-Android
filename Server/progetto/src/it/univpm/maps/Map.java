package it.univpm.maps;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Map {
	//attributi
	@XmlElement
	private String nome;
	@XmlElement
	private ArrayList<Node> nodi = new ArrayList<Node>();
	@XmlElement
	private ArrayList<Edge> archi = new ArrayList<Edge>();
	@XmlElement
	private ArrayList<String> immagini = new ArrayList<String>();

	//costruttore 
	public Map(String nome){
		this.nome=nome;
	}
	//costruttore senza parametri
	public Map(){
		
	}
	public String getNome(){
		return this.nome;
	}
	public void setNome(String nome){
		this.nome=nome;
	}
	public ArrayList<Node> getNodi(){
		return this.nodi;		
	}
	public void setNodi(ArrayList<Node> nodi){
		this.nodi=nodi;
	}
	public ArrayList<Edge> getArchi(){
		return this.archi;		
	}
	public void setArchi(ArrayList<Edge> archi){
		this.archi=archi;
	}
	public void AggiungiNodo(Node n){
		this.nodi.add(n);
	}
	public void AggiungiArco(Edge a){
		this.archi.add(a);
	}
	public void AggiungiImmagine(String s){
		this.immagini.add(s);
	}
	public void setImmagini(ArrayList<String> immagini){
		this.immagini=immagini;
	}
	public ArrayList<String> getImmagini(){
		return this.immagini;
	}
	
}
