package it.univpm.maps;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Utente {

	//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //formato data-ora
	String username; //nome utente
	String password; //password utente
	String salt; //salt usato per crittografia password con funzione hashUtente
	String posizione; //posizione dell'utente (codice nodo)
	String token; //token da usare per la comunicazione con il server
	Date aggiornamentoMappa; //data e ora dell'ultimo invio mappa all'utente
	
	//costruttore senza parametri
	public Utente(){
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt(){
		return this.salt;
	}
	
	public void setSalt(String salt){
		this.salt=salt;
	}

	public String getPosizione() {
		return posizione;
	}

	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getAggiornamentoMappa() {
		return aggiornamentoMappa;
	}

	public void setAggiornamentoMappa(Date aggiornamentoMappa) {
		this.aggiornamentoMappa = aggiornamentoMappa;
	}
	
	
}
