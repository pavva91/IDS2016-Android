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
	String posizione; //posizione dell'utente (codice nodo)
	String token; //token da usare per la comunicazione con il server
	Date aggiornamentoMappa; //data e ora dell'ultimo invio mappa all'utente
	
	//costruttore senza parametri
	public Utente(){
		
	}
	
	//costruttore con parametri
	public Utente(String username, String password,	String posizione, String token,	Date aggiornamentoMappa){
		this.username=username;
		this.password=password;
		this.posizione=posizione;
		this.token=token;
		this.aggiornamentoMappa=aggiornamentoMappa;
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
