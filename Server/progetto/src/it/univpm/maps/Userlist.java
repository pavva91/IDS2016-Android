package it.univpm.maps;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
		String newPassword;
		SecureRandom random = new SecureRandom();
		String newToken = new String(new BigInteger(128, random).toString(32));
		String salt = new String(new BigInteger(64, random).toString(32));
		u.setToken(newToken);
		u.setSalt(salt);
		newPassword=cryptPassword(u.getPassword(), salt);
		u.setPassword(newPassword);
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.insertUtente(con, u);
		}catch (Exception e){
			return Response.status(Response.Status.CONFLICT).entity("ERRORE: Impossibile registrare utente!").build();
		}
		return Response.ok(u.getToken(), MediaType.APPLICATION_JSON).build();	
	}

	@GET
	@Path("{username}/login")
	@Produces("text/plain")
	public Response LoginUtente(@PathParam("username")String username, @QueryParam("password")String password){
		String token;
		String salt;
		Database db;
		Connection con;
		AccessDB access;
		try{
			db = new Database();
			con = db.getConnection();
			access = new AccessDB();
			salt = access.getSaltUtente(con, username);
			password=cryptPassword(password, salt);
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
	public Response posizioneUtente(Utente u, @QueryParam("token")String token){
		//Utente u= new Utente();
		int nuovaPosizione=u.getPosizione();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.verificaToken(con, token);
			//u = access.getUtente(con, token); //recupero utente dal DB
			u = access.aggiornaPosizioneUtente(con, u, nuovaPosizione); //aggiorno posizione utente
			//u = access.getUtente(con, token); //recupero utente aggiornato dal DB
		}catch (Exception e){
			return Response.status(Response.Status.CONFLICT).entity("ERRORE: Aggiornamento posizione utente impossibile!").build();
		}
		return Response.ok(u, MediaType.APPLICATION_JSON).build();	
	}
	
	   public String cryptPassword(String password, String salt) {
		   	password=password.concat(salt);
		   	MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
				byte arrayDigest[] = messageDigest.digest(password.getBytes());     
				StringBuffer hexString = new StringBuffer();
				for (int i=0;i<arrayDigest.length;i++) {
				    hexString.append(Integer.toHexString(0xFF & arrayDigest[i]));
				}
			    password=hexString.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		    return password;
		}
	
}
