package it.univpm.maps;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public void insertMappa(Connection con, Mappa m) throws SQLException{
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
	public Boolean cercaMappa(Connection con, String nome) throws SQLException{
		int numRecord;
		PreparedStatement stmt = con.prepareStatement("SELECT nome FROM mappe where nome=?");
		stmt.setString(1, nome);
		numRecord = stmt.executeUpdate();
        if (numRecord == 1) {
            return true;
        }else{
        	return false;
        }
	}
}
