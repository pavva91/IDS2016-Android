package it.univpm.maps;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	//costruttore senza parametri
	public Database(){
		
	}
	
	//parametri di connessione al DB MySQL
	private static final String USERNAME_DB = "root";
	private static final String PASSWORD_DB = "pwdroot";
	private static final String IP_DB = "localhost";
	private static final String PORT_DB = "3306";
	private static final String URL_DB = "/progetto";
	
	//metodo che consente di collegarsi al DB, restituisce la connessione
	public Connection getConnection() throws Exception{
		String connectionURL = "jdbc:mysql://"+IP_DB+":"+PORT_DB+URL_DB;
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, USERNAME_DB, PASSWORD_DB);
		return connection;
	}
}