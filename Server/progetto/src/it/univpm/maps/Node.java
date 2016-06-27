package it.univpm.maps;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD) 
public class Node {

	//attributi
	private String mapName; //mappa a cui appartiene il nodo
	private int id; //codice indentificativo nodo
	private String code; //codice nodo
	private String descr; //nome esteso nodo
	private int quota; //quota in metri s.l.m.
	private int x; //coordinata x
	private int y; //coordinata y
	private double width; //larghezza in metri
	public static enum NodeType {exit, emergency_exit, other};
	private NodeType type;
	
	//costruttore senza parametri
	public Node(){
		
	}
	
	//getters e setters
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code=code;
	}
	public String getDescr(){
		return this.descr;
	}
	public void setDescr(String desc){
		this.descr=desc;
	}
	public int getQuota(){
		return this.quota;
	}
	public void setQuota(int quota){
		this.quota=quota;
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
	public double getWidth(){
		return this.width;
	}
	public void setWidth(double width){
		this.width=width;
	}
	public NodeType getType(){
		return this.type;
	}
	public void setType(NodeType type){
		this.type=type;
	}
	public void setMap(String mapName){
		this.mapName=mapName;
	}
	public String getMap(){
		return this.mapName;
	}	
}
