package it.univpm.maps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Image {
	@XmlElement
	private String url;
	@XmlElement
	private int quota;
	@XmlElement
	private String map;
	
	//costruttore senza parametri
	public Image(){
		
	}
	
	//getter e setter
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}


}
