package it.univpm.maps;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class User {

	//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //formato data-ora
	String username; //nome utente
	String password; //password utente
	String salt; //salt usato per crittografia password con funzione hashUtente
	int position; //posizione dell'utente (id codice nodo)
	String token; //token da usare per la comunicazione con il server
	Date lastMapUpdate; //data e ora della mappa
	
	//costruttore senza parametri
	public User(){
		
	}
	
	//getters e setters
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getLasMapUpdate() {
		return lastMapUpdate;
	}
	public void setLastMapUpdate(Date lastMapUpdate) {
		this.lastMapUpdate = lastMapUpdate;
	}
}
