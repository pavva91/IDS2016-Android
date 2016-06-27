package it.univpm.maps.test;

public class ConfigTest {

	static final String usernameTest = new String("testuser");//nome utente usato per i test sulla gestione utente
	static final String passwordTest = new String("testpassword");//password relativa all'utente di cui sopra
	static final String usernameAdminTest = new String("admin");//username utente con diritti di amministratore
	static final String passwordAdminTest = new String("univpm");//password utente di cui sopra
	static final String cryptedPasswordAdminTest = new String("6d6aa583f65ef71ab79b9f3e436fae483eddb7c1c31e5baa4919db2ad7f12");//password cifrata dell'utente admin, usata per test utente
	static final String tokenAdminTest = new String("12m2t7oc43godndv767tkj9hue");//token utente admin
	static final String tokenUserTest = new String("1k9alfidlgd28bu92gnjuhpvt9");//token utente valido ma senza diritti di admin
	static final String saltAdminTest = new String("4f6h99ta8fk27");//salt dell'utente admin
	static final String mapNameTest = new String("testmap");//mappa usata per testare inserimento e cancellazione mappa
	static final String mapNameValid = new String("univpm_non_toccare");//mappa usata per testare modifiche agli archi o valori di emergenza
	static final int mapNumberOfEdge = 70;//numero di archi presenti nella mappa "mapNameValid"
	static final int mapNumberOfNode = 70;//numero di nodi presenti nella mappa "mapNameValid"
	static final String deviceIDTest = new String("abcdEFGH12345");//registrationID di test 
	static final String returnedDeviceIDTest = new String("abcdEFGH12345");//registrationID di test 
	
	
	//costruttore
	public ConfigTest(){
		
	}
}
