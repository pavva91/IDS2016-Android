package it.univpm.maps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import com.sun.jersey.spi.resource.Singleton;

import it.univpm.maps.Nodo.tiponodo;


@Path("maps")
@Singleton
public class Maplist{
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String CreaMappa(Mappa m){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.insertMappa(con, m);
		}catch(SQLException esql){
			return esql.getMessage();
		}catch (Exception e){
			System.out.println(e);
		}
		return m.getNome();
	}
	

	@GET
	@Produces("application/json")
	public ArrayList<Mappa> ElencaMappe(){
		ArrayList<Mappa> listaMappe = new ArrayList<Mappa>();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			listaMappe = access.getMappe(con);
		}catch (Exception e){
			e.printStackTrace();
		}
		return listaMappe;
	}
	
	@GET
	@Path("{name}")
	@Produces("application/json")
	public Mappa getMappa(@PathParam("name") String nome){
		Mappa m=new Mappa(nome);
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			PreparedStatement stmt;
			ResultSet rs;
			if(access.cercaMappa(con, nome)){
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
					n.setTipo(tiponodo.values()[rs.getInt("tipo")]);
					m.AggiungiNodo(n);
				}
				stmt = con.prepareStatement("SELECT * FROM nodi");
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
				
			}else{
				//errore mappa non trovata //restituire 404
			}
				
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		return m;
	}
}