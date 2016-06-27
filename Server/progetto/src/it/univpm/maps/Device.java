package it.univpm.maps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Device {
	//attributi
	@XmlElement
	private String registrationID;
	
	//costruttore senza parametri
	public Device(){
		
	}
	
	//getter e setter
	public void setRegistrationID(String registrationID){
		this.registrationID=registrationID;
	}
	public String getRegistrationID(){
		return this.registrationID;
	}
}
