package it.univpm.maps;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

@Path("users")
@Singleton
public class Userlist {

	//costruttore senza parametri
	public Userlist(){
		
	}
	
	
	@PUT
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
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response LoginUtente(Utente u){
		String token;
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			token = access.loginUtente(con, u);
		}catch (SQLException sqlex){
			return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Accesso al db non riuscito!").build();
		}catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE: Accesso al db non riuscito!").build();
		}
		u.setPassword("");
		u.setToken(token);
		return Response.ok(u, MediaType.APPLICATION_JSON).build();
	}
}
