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
	private ArrayList<Image> images = new ArrayList<Image>();
	@XmlElement
	private Date lastUpdateMap;
	@XmlElement
	private Boolean emergency;

	//costruttore 
	public Map(String name){
		this.name=name;
	}
	//costruttore senza parametri
	public Map(){
		
	}
	
	//getters e setters
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	public ArrayList<Node> getNodeList(){
		return this.nodes;		
	}
	public void setNodeList(ArrayList<Node> nodes){
		this.nodes=nodes;
	}
	public ArrayList<Edge> getEdgeList(){
		return this.edges;		
	}
	public void setEdgeList(ArrayList<Edge> edges){
		this.edges=edges;
	}
	public void addNode(Node n){
		this.nodes.add(n);
	}
	public void addEdge(Edge e){
		this.edges.add(e);
	}
	public void addImage(Image img){
		this.images.add(img);
	}
	public void setImageList(ArrayList<Image> images){
		this.images=images;
	}
	public ArrayList<Image> getImageList(){
		return this.images;
	}
	public void setLastUpdateMap(Date lastUpdateMap){
		this.lastUpdateMap=lastUpdateMap;
	}
	public Date getLastUpdateMap(){
		return this.lastUpdateMap;
	}
	public Boolean getEmergency(){
		return this.emergency;
	}
	public void setEmergency(Boolean emergency){
		this.emergency=emergency;
	}
	
}
