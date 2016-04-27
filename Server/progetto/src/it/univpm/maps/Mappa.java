package it.univpm.maps;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Mappa {
	//attributi
	@XmlElement
	private String nome;
	@XmlElement
	private ArrayList<Nodo> nodi = new ArrayList<Nodo>();
	@XmlElement
	private ArrayList<Arco> archi = new ArrayList<Arco>();

	//costruttore 
	public Mappa(String nome){
		this.nome=nome;
	}
	//costruttore senza parametri
	public Mappa(){
		
	}

	public String getNome(){
		return this.nome;
	}
	public void setNome(String nome){
		this.nome=nome;
	}
	public ArrayList<Nodo> getNodi(){
		return this.nodi;		
	}
	public void setNodi(ArrayList<Nodo> nodi){
		this.nodi=nodi;
	}
	public ArrayList<Arco> getArchi(){
		return this.archi;		
	}
	public void setArchi(ArrayList<Arco> archi){
		this.archi=archi;
	}
	public void AggiungiNodo(Nodo n){
		this.nodi.add(n);
	}
	public void AggiungiArco(Arco a){
		this.archi.add(a);
	}
	
}
