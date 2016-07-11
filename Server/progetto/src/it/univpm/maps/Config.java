package it.univpm.maps;

//classe che contiene solo stringhe di configurazione
public final class Config {
	
	//costruttore senza parametri
	public Config(){
		
	}
	//definizione di username con alto livello di privilegio
	static final String ADMINISTRATOR_USER="admin";
	//definizione del LOS
	static final String DEF_LOS="IF (superficie/(num_persone+0.01)>=3.7, 0,"
								+"IF (superficie/(num_persone+0.01)<3.7 AND superficie/(num_persone+0.01)>=2.2, 0.33,"
								+"IF (superficie/(num_persone+0.01)<2.2 AND superficie/(num_persone+0.01)>=1.4, 0.6,"
								+"IF (superficie/(num_persone+0.01)<1.4 AND superficie/(num_persone+0.01)>=0.75, 1.0, 3.0))))";
	//definizione del peso di un arco in caso di emergenza
	static final String COST_EMG="(0.07*v + 0.56*i + 0.19*c + 0.17*los)";
	
	//URL del server
	//static final String SERVER_URL = "http://localhost:8080/";//solo per debug locale
	static final String SERVER_URL = "http://123.456.789.123/";//per server di produzione
	
	//API KEY Google usata per inviare le push notification
	static final String APIKEY = "AIz3443737373rgrhdfhdfhjdf";
	
	//URL al quale inviare le push notification
	static final String NOTIFICATION_URL = "https://gcm-http.googleapis.com/gcm/send";

	//messaggio da inviare ai client in caso di attivazione modalità di emergenza
	static final String MESSAGE_EMERGENCY = "Attenzione, è in corso un emergenza!";
	
	//messaggio da inviare ai client in caso di attivazione della modalità normale (non emergenza)
	static final String MESSAGE_NOT_EMERGENCY = "Cessata emergenza, ora è tutto normale.";
}