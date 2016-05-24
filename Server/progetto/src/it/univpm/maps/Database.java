package it.univpm.maps;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	//costruttore senza parametri
	public Database(){
		
	}
	
	//parametri di connessione al DB MySQL
	private static final String usernameDB = "univpm";
	private static final String passwordDB = "univpm";
	private static final String ipDB = "localhost";
	private static final String portDB = "3306";
	private static final String urlDB = "/progetto";
	
	//metodo che consente di collegarsi al DB, restituisce la connessione
	public Connection getConnection() throws Exception{
		String connectionURL = "jdbc:mysql://"+ipDB+":"+portDB+urlDB;
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, usernameDB, passwordDB);
		return connection;
	}
}