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


@Path("users")
public class UserHandler {

	//costruttore senza parametri
	public UserHandler(){
		
	}
	
	//metodo che crea un utente
	//prende in input un utente e restituisce l'utente appena inserito nel DB
	//ritorna CONFLICT in caso di errore
	//ritorna OK se tutto ok
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response createUser(User u){
		String newPassword;
		SecureRandom random = new SecureRandom();
		String newToken = new String(new BigInteger(128, random).toString(32));
		String salt = new String(new BigInteger(64, random).toString(32));//genero salt
		u.setToken(newToken);
		u.setSalt(salt);
		newPassword=cryptPassword(u.getPassword(), salt);//cifro la password
		u.setPassword(newPassword);//setto password cifrata
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.insertUser(con, u);
		}catch (Exception e){
			return Response.status(Response.Status.CONFLICT).entity("ERRORE: Impossibile registrare utente!").build();
		}
		return Response.ok(u.getToken(), MediaType.APPLICATION_JSON).build();	
	}

	//metodo che effettua il login utente verificando username e password restituendo il token utente
	//ritorna CONFLICT in caso di errori SQL
	//ritorna INTERNAL SERVER ERROR se non riesce ad accedere al DB
	//ritorna OK se il login avviene correttamente
	@GET
	@Path("{username}/login")
	@Produces("text/plain")
	public Response userLogin(@PathParam("username")String username, @QueryParam("password")String password){
		String token;
		String salt;
		Database db;
		Connection con;
		AccessDB access;
		try{
			db = new Database();
			con = db.getConnection();
			access = new AccessDB();
			salt = access.getSaltUser(con, username);
			password=cryptPassword(password, salt);
			token = access.loginUser(con, username, password);
		}catch (SQLException sqlex){
			return Response.status(Response.Status.CONFLICT).entity(sqlex.toString()).build();
		}catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE: Accesso al db non riuscito!").build();
		}
		return Response.ok(token, MediaType.APPLICATION_JSON).build();
	}
	
	//metodo che modifica la posizione di un utente e ritorna l'utente aggiornato
	//ritorna CONFLICT se non è stato possibile aggiornare la posizione dell'utente
	//ritorna EXPECTATION FAILED se riesce a cifrare la password
	//ritorna OK se l'aggiornamento avviene correttamente
	@POST
	@Consumes("application/json")
	@Path("{username}/position")
	@Produces("application/json")
	public Response updateUserPosition(User u, @QueryParam("token")String token){;
		int newPosition=u.getPosition();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.verifyToken(con, token);
			u = access.updatePositionUser(con, u, newPosition); //aggiorno posizione utente
		}catch (NoSuchAlgorithmException algex){	
			return Response.status(Response.Status.EXPECTATION_FAILED).entity("ERRORE: Errore, algoritmo crittografico non supportato!").build();
		}catch (Exception ex){
			return Response.status(Response.Status.CONFLICT).entity("ERRORE: Aggiornamento posizione utente impossibile!").build();
		}
		return Response.ok(u, MediaType.APPLICATION_JSON).build();	
	}
	
	//metodo che cripta la password e ritorna la password cifrata
	//prende come parametri la password e il salt
	public String cryptPassword(String password, String salt) {
	   	password=password.concat(salt);
	   	MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte arrayDigest[] = messageDigest.digest(password.getBytes());     
			StringBuffer hexString = new StringBuffer();
			for (int i=0; i<arrayDigest.length; i++)
			    hexString.append(Integer.toHexString(0xFF & arrayDigest[i]));
		    password=hexString.toString();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
	    return password;
	}
}
