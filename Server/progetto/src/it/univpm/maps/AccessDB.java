package it.univpm.maps;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.univpm.maps.Nodo.tiponodo;

public class AccessDB {
	static String tokenAdmin="todaqhn374hh22tac143c2rll0";
	static String defLos="IF (superficie/(num_persone+0.01)>=3.7, 0,"
			+"IF (superficie/(num_persone+0.01)<3.7 AND superficie/(num_persone+0.01)>=2.2, 0.33,"
			+"IF (superficie/(num_persone+0.01)<2.2 AND superficie/(num_persone+0.01)>=1.4, 0.6,"
			+"IF (superficie/(num_persone+0.01)<1.4 AND superficie/(num_persone+0.01)>=0.75, 1.0, 3.0))))";
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
	            throw new SQLException("Errore inserimento mappa! Nome mappa già esistente!");
	        }
	        for(Nodo n: m.getNodi()) //ciclo su ogni nodo in lista
	        	insertNodo(con, m, n); //inserisce nuovo nodo
	        for(Arco a: m.getArchi()) //ciclo su ogni arco in lista
	        	insertArco(con, m, a); //inserisce nuovo arco

	}
	
	public void insertNodo(Connection con, Mappa m, Nodo n) throws SQLException{
		int numRecord;
		String mappa = m.getNome();
		String codice = n.getCodice();
		String descrizione = n.getDescrizione();
		int quota = n.getQuota();
		int x = n.getX();
		int y = n.getY();
		double larghezza = n.getLarghezza();
		String tipo = n.getTipo().toString();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO nodi (mappa, codice, descrizione, quota, x, y, larghezza, tipo) VALUES(?,?,?,?,?,?,?,?)");
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
	}
	
	public void insertArco(Connection con, Mappa m, Arco a) throws SQLException{
		int numRecord;
		String mappa = m.getNome();
		String partenza = a.getPartenza();
		String destinazione = a.getDestinazione();
		double lunghezza = a.getLunghezza();
		double v = a.getV();
		double i = a.getI();
		double c = a.getC();
		double superficie = a.getSuperficie();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO archi (mappa, partenza, destinazione, lunghezza, v, i, c, superficie) VALUES(?,?,?,?,?,?,?,?)");
		stmt.setString(1, mappa);
		stmt.setString(2, partenza);
		stmt.setString(3, destinazione);
		stmt.setDouble(4, lunghezza);
		stmt.setDouble(5, v);
		stmt.setDouble(6, i);
		stmt.setDouble(7, c);
		stmt.setDouble(8, superficie);
		numRecord = stmt.executeUpdate();
        if (numRecord == 0) {
            throw new SQLException("Errore inserimento arco!");
        }
	}
	
	public void cancellaMappa(Connection con, Mappa m) throws SQLException{
		int numRecord;
		PreparedStatement stmt = con.prepareStatement("DELETE * FROM mappe where nome=?");
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
	
	public Mappa OttieniMappa(Connection con, String nome, String token) throws SQLException{
		
		PreparedStatement stmt;
		ResultSet rs;
		Mappa m=new Mappa(nome);
		int numRecord;
		stmt = con.prepareStatement("SELECT * FROM nodi");
		rs = stmt.executeQuery();
		while(rs.next()){
			Nodo n = new Nodo();
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
		stmt = con.prepareStatement("SELECT * FROM archi");
		rs = stmt.executeQuery();
		while(rs.next()){
			Arco a = new Arco();
			a.setMappa(m.getNome());
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
	
	public Boolean insertUtente(Connection con, Utente u) throws SQLException{
		int numRecord;
		String username = u.getUsername();
		String password = u.getPassword();
		String token = u.getToken();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO utenti (username, password, token) VALUES(?,?,?)");
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.setString(3, token);
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
			u.setPosizione(rs.getString("posizione"));
			u.setToken(rs.getString("token"));
			u.setAggiornamentoMappa(rs.getTimestamp("aggiornamento_mappa"));
			return u;
		}
		throw new SQLException("ERRORE: Utente non trovato!");
	}
	
	public void aggiornaPosizioneUtente(Connection con, Utente u, String nuovaPosizione) throws SQLException{
		PreparedStatement stmt;
		String mappa;
		String vecchiaPosizione = u.getPosizione();
		//se l'utente non aveva una posizione nota allora non devo rimuovere una persona dagli archi
		if(u.getPosizione()!=null){
			//rimuovo una persona da tutti gli archi adiacenti la vecchia posizione dell'utente e aggiorno il LOS
			stmt = con.prepareStatement("UPDATE archi SET num_persone=num_persone-1 ,los="+defLos+" WHERE partenza=? OR destinazione=?");
			stmt.setString(1, vecchiaPosizione);
			stmt.setString(2, vecchiaPosizione);
			stmt.executeUpdate();
		}
		//aggiungo una persona in tutti gli archi adiacenti la nuova posizione dell'utente e aggiorno il LOS
		stmt = con.prepareStatement("UPDATE archi SET num_persone=num_persone+1, los="+defLos+" WHERE partenza=? OR destinazione=?");
		stmt.setString(1, nuovaPosizione);
		stmt.setString(2, nuovaPosizione);
		stmt.executeUpdate();
		//aggiorno posizione utente su tabella utenti
		stmt = con.prepareStatement("UPDATE utenti SET posizione=? WHERE token=?");
		stmt.setString(1, nuovaPosizione);
		stmt.setString(2, u.getToken());
		stmt.executeUpdate();
		//ricavo mappa da codice nodo
		stmt = con.prepareStatement("SELECT mappa FROM nodi WHERE codice=?");
		stmt.setString(1, nuovaPosizione);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next()){
			new SQLException("ERRORE: il nodo fa riferimento ad una mappa non trovata!");
		}
		mappa=rs.getString("mappa");
		//aggiorno data mappa
		stmt = con.prepareStatement("UPDATE mappe SET data_aggiornamento=NOW() WHERE nome=?");
		stmt.setString(1, mappa);
		stmt.executeUpdate();
	}
	
}
