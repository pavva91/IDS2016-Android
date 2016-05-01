package it.univpm.maps;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

@Path("users")
@Singleton
public class Userlist {

	//costruttore senza parametri
	public Userlist(){
		
	}
	
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response CreaUtente(Utente u){
		SecureRandom random = new SecureRandom();
		String newToken = new String(new BigInteger(130, random).toString(32));
		u.setToken(newToken);
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.insertUtente(con, u);
		}catch (Exception e){
			return Response.status(Response.Status.CONFLICT).entity("ERRORE: Nome utente duplicato!").build();
		}
		return Response.ok(u.getToken(), MediaType.APPLICATION_JSON).build();	
	}

	@GET
	@Path("{username}/login")
	@Produces("text/plain")
	public Response LoginUtente(@PathParam("username")String username, @QueryParam("password")String password){
		String token;
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			token = access.loginUtente(con, username, password);
		}catch (SQLException sqlex){
			return Response.status(Response.Status.FORBIDDEN).entity(sqlex.toString()).build();
		}catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE: Accesso al db non riuscito!").build();
		}
		return Response.ok(token, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Consumes("application/json")
	@Path("{username}/posizione")
	@Produces("application/json")
	public Response posizioneUtente(String nuovaPosizione, @QueryParam("token")String token){
		Utente u= new Utente();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.verificaToken(con, token);
			u = access.getUtente(con, token); //recupero utente dal DB
			access.aggiornaPosizioneUtente(con, u, nuovaPosizione); //aggiorno posizione utente
			u = access.getUtente(con, token); //recupero utente aggiornato dal DB
		}catch (Exception e){
			return Response.status(Response.Status.CONFLICT).entity("ERRORE: Aggiornamento posizione utente impossibile!").build();
		}
		return Response.ok(u, MediaType.APPLICATION_JSON).build();	
	}
	
	
}
