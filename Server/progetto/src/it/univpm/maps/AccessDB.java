package it.univpm.maps;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import it.univpm.maps.Node.NodeType;

public class AccessDB {

	//costruttore
	public AccessDB(){
		
	}
	
	//medodo che restituisce l'elenco di mappe (nomi univoci) presenti sul DB
	public ArrayList<Map> getMapList(Connection con) throws SQLException{
		ArrayList<Map> mapList = new ArrayList<Map>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM mappe");
		ResultSet rs = stmt.executeQuery();
		try{
			while(rs.next()){
				Map m = new Map();
				m.setName(rs.getString("nome"));
				m.setLastUpdateMap(rs.getTimestamp("data_aggiornamento"));
				mapList.add(m);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return mapList;
	}
	
	//metodo che inserisce una mappa nel DB
	public void insertMap(Connection con, Map m) throws SQLException{
		int numRecord;
		//inserisco mappa
		PreparedStatement stmt = con.prepareStatement("INSERT INTO mappe (nome, data_aggiornamento) VALUES (?,NOW())");
		stmt.setString(1, m.getName());
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
        	//se non si riesce ad inserire la mappa allora genero un'eccezione
            throw new SQLException("Errore inserimento mappa!");
        }
        //inserisco i nodi
        for(Node n: m.getNodeList()) //ciclo su ogni nodo in lista
        	n = insertNode(con, m, n); //inserisce nuovo nodo
        //inserisco gli archi
        for(Edge a: m.getEdgeList()) //ciclo su ogni arco in lista
        	a = insertEdge(con, m, a); //inserisce nuovo arco
        //recupero data aggiornamento mappa
        stmt = con.prepareStatement("SELECT data_aggiornamento FROM mappe WHERE nome=?");
		stmt.setString(1, m.getName());
		ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
        	m.setLastUpdateMap(Date.valueOf(rs.getDate("data_aggiornamento").toString()));
        }else
        	//se non esiste la mappa allora genero un'eccezione
        	throw new SQLException("Errore inserimento mappa!");
	}
	
	//metodo che inserisce un nodo nel DB
	public Node insertNode(Connection con, Map m, Node n) throws SQLException{
		int numRecord;
		String mapName = m.getName();
		String edgeCode = n.getCode();
		String nodeDescr = n.getDescr();
		int quote = n.getQuota();
		int x = n.getX();
		int y = n.getY();
		double width = n.getWidth();
		String type = String.valueOf(n.getType());
		PreparedStatement stmt = con.prepareStatement("INSERT INTO nodi (mappa, codice, descrizione, quota, x, y, larghezza, tipo) VALUES(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, mapName);
		stmt.setString(2, edgeCode);
		stmt.setString(3, nodeDescr);
		stmt.setInt(4, quote);
		stmt.setInt(5, x);
		stmt.setInt(6, y);
		stmt.setDouble(7, width);
		stmt.setString(8, type);
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
        	//se non è stato inserito nessun record allora genero un'eccezione
            throw new SQLException("Errore inserimento nodo!");
        }
        //recupero id del nodo appena inserito
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next())
        {
        	//setto id al nodo
            int id = rs.getInt(1);
            n.setId(id);
        }
        return n;
	}
	
	//metodo che inserisce un arco nel DB
	public Edge insertEdge(Connection con, Map m, Edge e) throws SQLException{
		int numRecord;
		int nodeFrom = getIdNode(con, m, e.getFrom());
		int nodeTo = getIdNode(con, m, e.getTo());
		double lenght = e.getLength();
		double v = e.getV();
		double i = e.getI();
		double c = e.getC();
		double area = e.getArea();
		//inserisco arco nel DB
		PreparedStatement stmt = con.prepareStatement("INSERT INTO archi (partenza, destinazione, lunghezza, v, i, c, superficie, los, costo_emg) VALUES(?,?,?,?,?,?,?,"+Config.DEF_LOS+","+Config.COST_EMG+")");
		stmt.setInt(1, nodeFrom);
		stmt.setInt(2, nodeTo);
		stmt.setDouble(3, lenght);
		stmt.setDouble(4, v);
		stmt.setDouble(5, i);
		stmt.setDouble(6, c);
		stmt.setDouble(7, area);
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
        	//se non è stato inserito nessun record allora genero un'eccezione
            throw new SQLException("Errore inserimento arco!");
        }
        return e;
	}
	//metodo che cancella una mappa dal DB
	public void deleteMap(Connection con, Map m) throws SQLException{
		int numRecord;
		PreparedStatement stmt = con.prepareStatement("DELETE FROM mappe where nome=?");
		stmt.setString(1, m.getName());
		numRecord = stmt.executeUpdate();;
        if (numRecord == 0) {
            throw new SQLException("Errore cancellazione mappa!");
        }
	}
	
	//metodo che cerca una mappa a partire dal suo nome
	//ritorna vero se viene trovata, falso altrimenti
	public Boolean searchMap(Connection con, String name) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT nome FROM mappe where nome=?");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			if(name.equals(rs.getString("nome"))) {
				return true;
			}
        }
		return false;
	}
	
	//metodo che recupera il salt di un utente a partire dal suo username
	public String getSaltUser(Connection con, String username) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT salt FROM utenti WHERE username=?");
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			return rs.getString("salt");
        }
		return null;
	}
	
	//metodo che recupera l'id di un nodo a partire dal suo nome (codice) e dalla mappa
	//ritorna id nodo, se nodo non esiste ritorna zero
	private int getIdNode(Connection con, Map m, String nome) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT id FROM nodi where codice=? AND mappa=?");
		stmt.setString(1, nome);
		stmt.setString(2, m.getName());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			return rs.getInt("id");
        }
		return 0;
	}
	
	//metodo che ritorna una mappa a partire dal suo nome
	//richiede anche il token utente e aggiorna la data di ultimo invio mappa all'utente
	public Map getMap(Connection con, String mapName, String token) throws SQLException{
		PreparedStatement stmt;
		ResultSet rs;
		Map m=new Map(mapName);
		//seleziono i nodi mappa
		stmt = con.prepareStatement("SELECT * FROM nodi WHERE mappa=?");
		stmt.setString(1, mapName);
		rs = stmt.executeQuery();
		//genero i nodi
		while(rs.next()){
			Node n = new Node();
			n.setId(rs.getInt("id"));
			n.setMap(m.getName());
			n.setCode(rs.getString("codice"));
			n.setDescr(rs.getString("descrizione"));
			n.setQuota(rs.getInt("quota"));
			n.setX(rs.getInt("x"));
			n.setY(rs.getInt("y"));
			n.setWidth(rs.getDouble("larghezza"));
			n.setType(NodeType.valueOf(rs.getString("tipo")));
			m.addNode(n);
		}
		//seleziono gli archi
		stmt = con.prepareStatement("SELECT n1.codice AS partenza, n2.codice AS destinazione, a.v, a.i, a.c,"
									+" a.los, a.lunghezza, a.superficie, a.costo_emg FROM archi AS a, nodi AS n1, nodi AS n2"
									+" WHERE a.partenza=n1.id AND a.destinazione=n2.id and n1.mappa=?");
		stmt.setString(1, mapName);
		rs = stmt.executeQuery();
		//genero gli archi
		while(rs.next()){
			Edge e = new Edge();
			e.setFrom(rs.getString("partenza"));
			e.setTo(rs.getString("destinazione"));
			e.setLength(rs.getDouble("lunghezza"));
			e.setV(rs.getDouble("v"));
			e.setI(rs.getDouble("i"));
			e.setC(rs.getDouble("c"));
			e.setArea(rs.getDouble("superficie"));
			e.setEmgCost(rs.getDouble("costo_emg"));
			m.addEdge(e);
		}
		//aggiorno data di ultimo invio mappa all'utente
		stmt = con.prepareStatement("UPDATE utenti SET aggiornamento_mappa=NOW() WHERE token=?");
		stmt.setString(1, token);
		stmt.executeUpdate();
		//seleziono le immagini mappa
		stmt = con.prepareStatement("SELECT * FROM immagini WHERE mappa=?");
		stmt.setString(1, mapName);
		rs = stmt.executeQuery();
		//creo la lista di URL di immagini
		while(rs.next()){
			Image img = new Image();
			img.setMap(rs.getString("mappa"));
			img.setQuota(rs.getInt("quota"));
			img.setUrl(rs.getString("url"));
			m.addImage(img);
		}
		//recupero data aggiornamento mappa e emergenza
		stmt = con.prepareStatement("SELECT * FROM mappe WHERE nome=?");
		stmt.setString(1, mapName);
		rs = stmt.executeQuery();
		if(rs.next()){
			m.setLastUpdateMap(rs.getTimestamp("data_aggiornamento"));
			m.setEmergency(rs.getBoolean("emergenza"));
		}
		return m;
	}
	
	//metodo che verifica l'esistenza di un token utente
	//ritorna vero se esiste, falso altrimenti
	public Boolean tokenExist(Connection con, String token) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM utenti where token=?");
		stmt.setString(1, token);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			return true;
        }
		return false;
	}
	
	//metodo che verifica l'esistenza di un token di un dato utente
	//ritorna vero se esiste, falso altrimenti
	public Boolean verifyToken(Connection con, String username, String token) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM utenti where token=? AND username=?");
		stmt.setString(1, token);
		stmt.setString(2, username);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			return true;
        }
		return false;
	}
	
	//metodo che verifica l'esistenza di una determinata quota di una mappa
	//ritorna vero se esiste, falso altrimenti
	public Boolean verifyQuote(Connection con, String mapName, int quote) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT quota FROM nodi WHERE mappa=? AND quota=?");
		stmt.setString(1, mapName);
		stmt.setInt(2, quote);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			return true;
        }
		return false;
	}
	
	//metodo che inserisce un utente nel DB, usato per la registrazione utente
	//ritorna vero se ha inserito un utente, falso altrimenti
	public void insertUser(Connection con, User u) throws SQLException{
		String username = u.getUsername();
		String password = u.getPassword();
		String token = u.getToken();
		String salt = u.getSalt();
		//inserisco utente
		PreparedStatement stmt = con.prepareStatement("INSERT INTO utenti (username, password, token, salt, posizione) VALUES(?,?,?,?, null)");
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.setString(3, token);
		stmt.setString(4, salt);
		stmt.executeUpdate();
	}
	
	//metodo che cancella un utente dal DB, usato per scopo di test
	public Boolean deleteUser(Connection con, User u) throws SQLException{
		int numRecord;
		String username = u.getUsername();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM utenti WHERE username=?");
		stmt.setString(1, username);
		numRecord = stmt.executeUpdate();
        if (numRecord == 1) {
            return true;
        }else{
        	return false;
        }
	}
	
	//metodo che prende in input username e password e ritorna il token dell'utente
	//usato per login utente
	public String loginUser(Connection con, String username, String password) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT token FROM utenti WHERE username=? AND password=?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			//se c'è un record allora username e password sono validi e ritorno il token
			return rs.getString("token");
		}
		//se sono qui allora vuol dire che username o password sono errati e genero un'eccezione
		throw new SQLException("Login non riuscito! Username o password errati!");
	}
	
	//metodo che dato un token recupera il corrispondente utente
	public User getUser(Connection con, String token) throws Exception{
		User u = new User();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM utenti WHERE token=?");
		stmt.setString(1, token);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			//se c'è un record creo un nuovo utente in memoria al quale assegno i valori recuperati dal DB
			u.setUsername(rs.getString("username"));
			u.setPassword(rs.getString("password"));
			if (rs.getInt("posizione")>=0){
				u.setPosition(rs.getInt("posizione"));
			}else{
				//se l'utente non si trova in nessun arco della mappa gli assegno la posizione -1
				u.setPosition(-1);
			}
			u.setToken(rs.getString("token"));
			u.setLastMapUpdate(rs.getTimestamp("aggiornamento_mappa"));
			return u;
		}
		throw new SQLException("ERRORE!");
	}
	
	//metodo che aggiorna la posizione dell'utente
	//ritorna utente aggiornato
	public User updatePositionUser(Connection con, User u, int newPosition) throws SQLException{
		PreparedStatement stmt;
		String mapName;
		ResultSet rs;
		int oldPosition=0;
		//recupero vecchia posizione utente
		stmt = con.prepareStatement("SELECT posizione FROM utenti WHERE username=?");
		stmt.setString(1, u.getUsername());
		rs = stmt.executeQuery();
		if(rs.next()){
			oldPosition = rs.getInt("posizione");
			if (oldPosition==0)
				oldPosition=-1;
		}
		//se l'utente non aveva una posizione nota allora...
		if(oldPosition!=-1){
			//rimuovo una persona da tutti gli archi adiacenti la vecchia posizione dell'utente e aggiorno il LOS
			stmt = con.prepareStatement("UPDATE archi SET num_persone=num_persone-1 ,los="+Config.DEF_LOS+", costo_emg="+Config.COST_EMG+" WHERE partenza=? OR destinazione=?");
			stmt.setInt(1, oldPosition);
			stmt.setInt(2, oldPosition);
			stmt.executeUpdate();
		}
		//aggiungo una persona in tutti gli archi adiacenti la nuova posizione dell'utente e aggiorno il LOS
		stmt = con.prepareStatement("UPDATE archi SET num_persone=num_persone+1, los="+Config.DEF_LOS+", costo_emg="+Config.COST_EMG+" WHERE partenza=? OR destinazione=?");
		stmt.setInt(1, newPosition);
		stmt.setInt(2, newPosition);
		stmt.executeUpdate();
		//aggiorno posizione utente su tabella utenti
		if(newPosition!=-1){
			stmt = con.prepareStatement("UPDATE utenti SET posizione=? WHERE token=?");
			stmt.setInt(1, newPosition);
			stmt.setString(2, u.getToken());
		}
		//se la nuova posizione non è nota allora la setto NULL sul DB
		if(newPosition==-1){
			stmt = con.prepareStatement("UPDATE utenti SET posizione=null WHERE token=?");
			stmt.setString(1, u.getToken());
		}
		stmt.executeUpdate();
		//ricavo nome mappa da codice nodo
		stmt = con.prepareStatement("SELECT mappa FROM nodi WHERE id=?");
		stmt.setInt(1, newPosition);
		rs = stmt.executeQuery();
		if(!rs.next()){
			//se il nodo non esiste genero un'eccezione
			new SQLException("ERRORE: nodo nullo o non trovato!");
		}
		mapName=rs.getString("mappa");
		//aggiorno data mappa
		updateMapDate(con, mapName);
		return u;
	}
	
	//metodo che inserisce o aggiorna i record di immagine relativa ad un piano della mappa sul DB
	public void updateMapImage(Connection con, String mapName, int quote, String url) throws SQLException{
		PreparedStatement stmt;
		Boolean imageExist = false;
		//verifico se esiste già un record per la mappa e la quota passati come parametri
		stmt = con.prepareStatement("SELECT * FROM immagini WHERE mappa=? AND quota=?");
		stmt.setString(1, mapName);
		stmt.setInt(2, quote);
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			imageExist=true;
		if(imageExist){
			//se l'immagine esiste già allora faccio un update
			stmt = con.prepareStatement("UPDATE immagini SET url=? WHERE mappa=? AND quota=?");
			stmt.setString(1, url);
			stmt.setString(2, mapName);
			stmt.setInt(3, quote);
			stmt.executeUpdate();
		}if(!imageExist){
			//se l'immagine non esiste allora la inserisco	
			stmt = con.prepareStatement("INSERT INTO immagini (url, mappa, quota) VALUES (?,?,?)");
			stmt.setString(1, url);
			stmt.setString(2, mapName);
			stmt.setInt(3, quote);
			stmt.executeUpdate();	
		}
	}

	//metodo che aggiorna i valori di un arco, usato principalmente per variare i parametri 
	//prende in input l'arco e gli id dei nodi di partenza e di arrivo e restituisce l'arco
	public Edge updateEdge(Connection con, Edge e, int edgeFrom, int edgeTo) throws SQLException {
		PreparedStatement stmt;
		Double v=e.getV();
		Double i=e.getI();
		Double c=e.getC();
		int numPersone=e.getNumPers();
		stmt = con.prepareStatement("UPDATE archi SET v=?, i=?, c=?, num_persone=? ,los="+Config.DEF_LOS+", costo_emg="+Config.COST_EMG+ " WHERE partenza=? AND destinazione=?");
		stmt.setDouble(1, v);
		stmt.setDouble(2, i);
		stmt.setDouble(3, c);
		stmt.setInt(4, numPersone);
		stmt.setInt(5, edgeFrom);
		stmt.setInt(6, edgeTo);
		stmt.executeUpdate();
		e=getEdge(con, edgeFrom, edgeTo);
		return e;
	}

	//metodo che ritorna l'id di un nodo dato il nome del nodo e il nome della mappa
	//ritorna -1 in caso di nodo non trovato
	public int getNodeId(Connection con, String nodeName, String mapName) throws SQLException {
		PreparedStatement stmt;
		stmt = con.prepareStatement("SELECT id FROM nodi WHERE mappa=? AND codice=?");
		stmt.setString(1, mapName);
		stmt.setString(2, nodeName);
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			return rs.getInt("id");
		else
			return -1;
	}
	
	//metodo che verifica l'esistenza di un arco dato il nodo di partenza e il nodo di arrivo
	//ritorna falso in caso di arco non trovato, ritorna vero se l'arco esiste
	public Boolean edgeExist(Connection con, int from, int to) throws SQLException {
		PreparedStatement stmt;
		stmt = con.prepareStatement("SELECT * FROM archi WHERE partenza=? AND destinazione=?");
		stmt.setInt(1, from);
		stmt.setInt(2, to);
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			return true;
		else
			return false;
	}
	
	//metodo che recupera un arco a partire dai nodi di partenza e destinazione
	public Edge getEdge (Connection con, int from, int to) throws SQLException {
		Edge e = new Edge();
		PreparedStatement stmt;
		//stmt = con.prepareStatement("SELECT * FROM archi, nodi WHERE partenza=? AND destinazione=? AND (archi.partenza=nodi.id OR archi.destinazione=nodi.id)");
		stmt = con.prepareStatement("SELECT n1.codice AS partenza, n2.codice AS destinazione, a.v, a.i, a.c,"
									+" a.los, a.lunghezza, a.superficie, a.num_persone, a.costo_emg  FROM archi AS a, nodi AS n1, nodi AS n2"
									+" WHERE a.partenza=n1.id AND a.destinazione=n2.id AND a.partenza=? AND a.destinazione=?");
		stmt.setInt(1, from);
		stmt.setInt(2, to);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			e.setFrom(rs.getString("partenza"));
			e.setTo(rs.getString("destinazione"));
			e.setLength(rs.getDouble("lunghezza"));
			e.setV(rs.getDouble("v"));
			e.setI(rs.getDouble("i"));
			e.setC(rs.getDouble("c"));
			e.setLos(rs.getDouble("los"));
			e.setArea(rs.getDouble("superficie"));
			e.setNumPers(rs.getInt("num_persone"));
			e.setEmgCost(rs.getDouble("costo_emg"));
		}else{
			new SQLException("ERRORE: arco non trovato!");
		}
		return e;
	}
	
	//metodo che aggiorna settando a NOW la data di aggiornamento mappa
	public void updateMapDate(Connection con, String mapName) throws SQLException {
		PreparedStatement stmt;
		stmt = con.prepareStatement("UPDATE mappe SET data_aggiornamento=NOW() WHERE nome=?");
		stmt.setString(1, mapName);
		stmt.executeUpdate();	
	}
	
	//metodo che recupera le informazioni su una mappa (nome e data ultimo aggiornamento)
	public Map getMapInfo(Connection con, String mapName) throws SQLException{
		Map m = new Map();
		PreparedStatement stmt;
		//recupero data aggiornamento mappa
		stmt = con.prepareStatement("SELECT * FROM mappe WHERE nome=?");
		stmt.setString(1, mapName);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			m.setName(mapName);
			m.setLastUpdateMap(rs.getTimestamp("data_aggiornamento"));
			m.setEmergency(rs.getBoolean("emergenza"));
		}else{
			new Exception("ERRORE: mappa non trovata!");
		}
		return m;
	}
	
	//medodo che restituisce l'elenco degli utenti presenti sul DB
	public ArrayList<User> getUsersList(Connection con) throws SQLException{
		ArrayList<User> userList = new ArrayList<User>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM utenti");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			User u = new User();
			u.setUsername(rs.getString("username"));
			u.setPassword(rs.getString("password"));
			u.setSalt(rs.getString("salt"));
			u.setToken(rs.getString("token"));
			userList.add(u);
		}
		return userList;
	}
	
	//metodo che inserisce un nodo nel DB
	public void insertDevice(Connection con, Device d) throws SQLException{
		//verifico eventuale esistenza del device che devo inserire
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM dispositivi WHERE registrationID=?");
		stmt.setString(1, d.getRegistrationID());
		ResultSet rs = stmt.executeQuery();
		//se non esiste allora lo inserico
		if(!rs.next()){
			stmt = con.prepareStatement("INSERT INTO dispositivi (registrationID) VALUES(?)");
			stmt.setString(1, d.getRegistrationID());
			stmt.executeUpdate();
		}
	}
	
	//metodo che inserisce un nodo nel DB
	public void deleteDevice(Connection con, Device d) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("DELETE FROM dispositivi where registrationID=?");
		stmt.setString(1, d.getRegistrationID());
		int numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
        	//se non è stato cancellato nessun record allora genero un'eccezione
            throw new SQLException("Errore cancellazione registrationID!");
        }
	}

	//metodo che setta o resetta la modalità emergenza
	public void setEmergency(Connection con, String mapName, Boolean emergency) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("UPDATE mappe SET emergenza=? where nome=?");
		stmt.setBoolean(1, emergency);
		stmt.setString(2, mapName);
		int numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
        	//se non è stato aggiornato nessun record allora genero un'eccezione
            throw new SQLException("Errore nel settaggio condizione di emergenza!");
        }
	}
	
	//metodo che invia una notifica a tutti i dispositivi presenti nel DB
	public void notifyEmergencyToDevice(Connection con, Boolean emergency) throws Exception{
		PushNotificationHandler pushhandler = new PushNotificationHandler();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM dispositivi");
		ResultSet rs = stmt.executeQuery();
		//Notifico a tutti la nuova situazione di allarme
		while(rs.next()){
			if(emergency)
				pushhandler.sendPushNotification(rs.getString("registrationID"), emergency, Config.MESSAGE_EMERGENCY);
			else
				pushhandler.sendPushNotification(rs.getString("registrationID"), emergency, Config.MESSAGE_NOT_EMERGENCY);
		}
	}
}
