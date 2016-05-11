package it.univpm.maps;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import it.univpm.maps.Nodo.tiponodo;

public class AccessDB {

	//costruttore
	public AccessDB(){
		
	}
	
	public ArrayList<Mappa> getMappe(Connection con) throws SQLException{
		ArrayList<Mappa> listaMappe = new ArrayList<Mappa>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM mappe");
		ResultSet rs = stmt.executeQuery();
		try{
			while(rs.next()){
				Mappa m = new Mappa();
				m.setNome(rs.getString("nome"));
				listaMappe.add(m);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return listaMappe;
	}
	
	public void inserisciMappa(Connection con, Mappa m) throws SQLException{
		int numRecord;
		PreparedStatement stmt = con.prepareStatement("INSERT INTO mappe (nome) VALUES (?)");
		stmt.setString(1, m.getNome());
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
            throw new SQLException("Errore inserimento mappa!");
        }        
        for(Nodo n: m.getNodi()) //ciclo su ogni nodo in lista
        	n = insertNodo(con, m, n); //inserisce nuovo nodo
        for(Arco a: m.getArchi()) //ciclo su ogni arco in lista
        	a = insertArco(con, m, a); //inserisce nuovo arco
        

	}
	
	public Nodo insertNodo(Connection con, Mappa m, Nodo n) throws SQLException{
		int numRecord;
		String mappa = m.getNome();
		String codice = n.getCodice();
		String descrizione = n.getDescrizione();
		int quota = n.getQuota();
		int x = n.getX();
		int y = n.getY();
		double larghezza = n.getLarghezza();
		String tipo = n.getTipo().toString();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO nodi (mappa, codice, descrizione, quota, x, y, larghezza, tipo) VALUES(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, mappa);
		stmt.setString(2, codice);
		stmt.setString(3, descrizione);
		stmt.setInt(4, quota);
		stmt.setInt(5, x);
		stmt.setInt(6, y);
		stmt.setDouble(7, larghezza);
		stmt.setString(8, tipo);
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
            throw new SQLException("Errore inserimento nodo!");
        }
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next())
        {
            int id = rs.getInt(1);
            n.setId(id);
        }
        return n;
	}
	
	public Arco insertArco(Connection con, Mappa m, Arco a) throws SQLException{
		int numRecord;
		int partenza = GetIdNodo(con, m, a.getPartenza());
		int destinazione = GetIdNodo(con, m, a.getDestinazione());
		double lunghezza = a.getLunghezza();
		double v = a.getV();
		double i = a.getI();
		double c = a.getC();
		double superficie = a.getSuperficie();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO archi (partenza, destinazione, lunghezza, v, i, c, superficie) VALUES(?,?,?,?,?,?,?)");
		stmt.setInt(1, partenza);
		stmt.setInt(2, destinazione);
		stmt.setDouble(3, lunghezza);
		stmt.setDouble(4, v);
		stmt.setDouble(5, i);
		stmt.setDouble(6, c);
		stmt.setDouble(7, superficie);
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
            throw new SQLException("Errore inserimento arco!");
        }
        return a;
	}
	
	public void cancellaMappa(Connection con, Mappa m) throws SQLException{
		int numRecord;
		PreparedStatement stmt = con.prepareStatement("DELETE FROM mappe where nome=?");
		stmt.setString(1, m.getNome());
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
            throw new SQLException("Errore cancellazione mappa!");
        }
	}
	
	public Boolean CercaMappa(Connection con, String nome) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT nome FROM mappe where nome=?");
		stmt.setString(1, nome);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			if(nome.equals(rs.getString("nome"))) {
				return true;
			}
        }
		return false;
	}
	public String getSaltUtente(Connection con, String nome) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT salt FROM utenti WHERE username=?");
		stmt.setString(1, nome);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			return rs.getString("salt");
        }
		return null;
	}
	
	//metodo che recupera l'id di un nodo a partire dal suo nome (codice) e dalla mappa
	//ritorna id nodo, se nodo non esiste ritorna zero
	public int GetIdNodo(Connection con, Mappa m, String nome) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT id FROM nodi where codice=? AND mappa=?");
		stmt.setString(1, nome);
		stmt.setString(2, m.getNome());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			return rs.getInt("id");
        }
		return 0;
	}
	
	public Mappa OttieniMappa(Connection con, String nome, String token) throws SQLException{
		PreparedStatement stmt;
		ResultSet rs;
		Mappa m=new Mappa(nome);
		int numRecord;
		stmt = con.prepareStatement("SELECT * FROM nodi WHERE mappa=?");
		stmt.setString(1, nome);
		rs = stmt.executeQuery();
		while(rs.next()){
			Nodo n = new Nodo();
			n.setId(rs.getInt("id"));
			n.setMappa(m.getNome());
			n.setCodice(rs.getString("codice"));
			n.setDescrizione(rs.getString("descrizione"));
			n.setQuota(rs.getInt("quota"));
			n.setX(rs.getInt("x"));
			n.setY(rs.getInt("y"));
			n.setLarghezza(rs.getDouble("larghezza"));
			n.setTipo(tiponodo.valueOf(rs.getString("tipo")));
			m.AggiungiNodo(n);
		}
		stmt = con.prepareStatement("SELECT n1.codice AS partenza, n2.codice AS destinazione, a.v, a.i, a.c,"
									+" a.los, a.lunghezza, a.superficie  FROM archi AS a, nodi AS n1, nodi AS n2"
									+" WHERE a.partenza=n1.id AND a.destinazione=n2.id and n1.mappa=?");
		stmt.setString(1, nome);
		rs = stmt.executeQuery();
		while(rs.next()){
			Arco a = new Arco();
			a.setPartenza(rs.getString("partenza"));
			a.setDestinazione(rs.getString("destinazione"));
			a.setLunghezza(rs.getDouble("lunghezza"));
			a.setV(rs.getDouble("v"));
			a.setI(rs.getDouble("i"));
			a.setC(rs.getDouble("c"));
			a.setSuperficie(rs.getDouble("superficie"));
			m.AggiungiArco(a);
		}
		stmt = con.prepareStatement("UPDATE utenti SET aggiornamento_mappa=NOW() WHERE token=?");
		stmt.setString(1, token);
		numRecord = stmt.executeUpdate();
		if (numRecord!=1)
			new SQLException("ERRORE: sono stati trovati "+numRecord+" utenti con lo stesso token!");
		
		
		stmt = con.prepareStatement("SELECT * FROM immagini WHERE mappa=?");
		stmt.setString(1, nome);
		rs = stmt.executeQuery();
		while(rs.next()){
			m.AggiungiImmagine(rs.getString("url"));
		}
		return m;
	}
	
	public Boolean verificaToken(Connection con, String token) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM utenti where token=?");
		stmt.setString(1, token);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			return true;
        }
		return false;
	}
	
	public Boolean verificaQuota(Connection con, String mapName, int quota) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT quota FROM nodi WHERE mappa=? AND quota=?");
		stmt.setString(1, mapName);
		stmt.setInt(2, quota);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			return true;
        }
		return false;
	}
	
	public Boolean insertUtente(Connection con, Utente u) throws SQLException{
		int numRecord;
		String username = u.getUsername();
		String password = u.getPassword();
		String token = u.getToken();
		String salt = u.getSalt();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO utenti (username, password, token, salt) VALUES(?,?,?,?)");
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.setString(3, token);
		stmt.setString(4, salt);
		numRecord = stmt.executeUpdate();
        if (numRecord == 1) {
            return true;
        }else{
        	return false;
        }
	}
	
	public String loginUtente(Connection con, String username, String password) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("SELECT token FROM utenti WHERE username=? AND password=?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			return rs.getString("token");
		}
		throw new SQLException("Login non riuscito! Username o password errati!");
	}
	
	public Utente getUtente(Connection con, String token) throws SQLException{
		Utente u = new Utente();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM utenti WHERE token=?");
		stmt.setString(1, token);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			u.setUsername(rs.getString("username"));
			u.setPassword(rs.getString("password"));
			if (rs.getInt("posizione")>=0)
				u.setPosizione(rs.getInt("posizione"));
			else
				u.setPosizione(-1);
			u.setToken(rs.getString("token"));
			u.setAggiornamentoMappa(rs.getTimestamp("aggiornamento_mappa"));
			return u;
		}
		throw new SQLException("ERRORE!");
	}
	
	public Utente aggiornaPosizioneUtente(Connection con, Utente u, int nuovaPosizione) throws SQLException{
		PreparedStatement stmt;
		String mappa;
		int vecchiaPosizione = u.getPosizione();
		//se l'utente non aveva una posizione nota allora...
		if(vecchiaPosizione==-1){
			//rimuovo una persona da tutti gli archi adiacenti la vecchia posizione dell'utente e aggiorno il LOS
			stmt = con.prepareStatement("UPDATE archi SET num_persone=num_persone-1 ,los="+Config.DEF_LOS+" WHERE partenza=? OR destinazione=?");
			stmt.setInt(1, vecchiaPosizione);
			stmt.setInt(2, vecchiaPosizione);
			stmt.executeUpdate();
		
		//se l'utente va in una posizione nota allora...
		}else{
			//aggiungo una persona in tutti gli archi adiacenti la nuova posizione dell'utente e aggiorno il LOS
			stmt = con.prepareStatement("UPDATE archi SET num_persone=num_persone+1, los="+Config.DEF_LOS+" WHERE partenza=? OR destinazione=?");
			stmt.setInt(1, nuovaPosizione);
			stmt.setInt(2, nuovaPosizione);
			stmt.executeUpdate();
		}
		//aggiorno posizione utente su tabella utenti
		
		if(nuovaPosizione!=-1){
			stmt = con.prepareStatement("UPDATE utenti SET posizione=? WHERE token=?");
			stmt.setInt(1, nuovaPosizione);
			stmt.setString(2, u.getToken());
		}
		if(nuovaPosizione==-1){
			stmt = con.prepareStatement("UPDATE utenti SET posizione=null WHERE token=?");
			stmt.setString(1, u.getToken());
		}
		stmt.executeUpdate();
		//ricavo mappa da codice nodo
		stmt = con.prepareStatement("SELECT mappa FROM nodi WHERE id=?");
		stmt.setInt(1, nuovaPosizione);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next()){
			new SQLException("ERRORE: nodo nullo o non trovato!");
		}
		mappa=rs.getString("mappa");
		//aggiorno data mappa
		stmt = con.prepareStatement("UPDATE mappe SET data_aggiornamento=NOW() WHERE nome=?");
		stmt.setString(1, mappa);
		stmt.executeUpdate();
		return u;
	}
	
	public void aggiornaImmagineMappa(Connection con, String mapName, int quota, String url) throws SQLException{
		PreparedStatement stmt;
		Boolean imageExist = false;
		//verifico se esiste già un record per la mappa e la quota passati come parametri
		stmt = con.prepareStatement("SELECT * FROM immagini WHERE mappa=? AND quota=?");
		stmt.setString(1, mapName);
		stmt.setInt(2, quota);
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			imageExist=true;
		if(imageExist){
			//se l'immagine esiste già allora faccio un update
			stmt = con.prepareStatement("UPDATE immagini SET url=? WHERE mappa=? AND quota=?");
			stmt.setString(1, url);
			stmt.setString(2, mapName);
			stmt.setInt(3, quota);
			stmt.executeUpdate();
		}else{
			//se l'immagine non esiste allora la inserisco	
			stmt = con.prepareStatement("INSERT INTO immagini (url, mappa, quota) VALUES (?,?,?)");
			stmt.setString(1, url);
			stmt.setString(2, mapName);
			stmt.setInt(3, quota);
			stmt.executeUpdate();	
		}
		stmt.close();

		
	}
	
}
