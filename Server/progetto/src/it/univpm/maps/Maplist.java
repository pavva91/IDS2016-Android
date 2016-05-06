package it.univpm.maps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.spi.resource.Singleton;


@Path("maps")
@Singleton
public class Maplist{
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response CreaMappa(Mappa m){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			if(access.verificaToken(con, AccessDB.tokenAdmin))
				if(!access.CercaMappa(con, m.getNome())){
					access.inserisciMappa(con, m);
				}else{
					//access.cancellaMappa(con, m);
					//errore mappa già esistente
					return Response.status(Response.Status.CONFLICT).entity("ERRORE: Impossibile inserire mappa!").build();
				}	
			else
				//errore utente non autorizzato a caricare mappe
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato a caricare mappe!").build();
		}catch(SQLException esql){
			//errore inserimento mappa //restituire 409
			return Response.status(Response.Status.CONFLICT).entity(esql.getMessage()).build();
		}catch (Exception e){
			System.out.println(e);
		}
		return Response.ok(m, MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Path("{nome}")
	@Produces("application/json")
	public Response CancellaMappa(@PathParam("nome") String nome, @QueryParam("token")String token){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			Mappa m = new Mappa();
			m.setNome(nome);
			if(access.verificaToken(con, AccessDB.tokenAdmin))
				if(access.CercaMappa(con, nome)){
					access.cancellaMappa(con, m);
				}else{
					//errore mappa non trovata
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Nome mappa non trovata!").build();
				}	
			else
				//errore utente non autorizzato a caricare mappe
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
		}catch(SQLException esql){
			//errore cancellazione mappa //restituire 409
			return Response.status(Response.Status.CONFLICT).entity(esql.getMessage()).build();
		}catch (Exception e){
			System.out.println(e);
		}
		return Response.ok("Mappa '"+nome+"' cancellata!", MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Produces("application/json")
	public Response ElencaMappe(@QueryParam("token")String token){
		ArrayList<Mappa> listaMappe = new ArrayList<Mappa>();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			if(access.verificaToken(con, token)){
				listaMappe = access.getMappe(con);
			}else{
				//errore mappa non trovata //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non loggato!").build();
			}	
		}catch (Exception e){
			e.printStackTrace();
		}
		GenericEntity entity = new GenericEntity<ArrayList<Mappa>>(listaMappe) {};
		//return Response.ok(listaMappe, MediaType.APPLICATION_JSON).build();
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("{name}")
	@Produces("application/json")
	public Response getMappa(@PathParam("name") String nome, @QueryParam("token")String token){
		AccessDB access = new AccessDB();
		Mappa m=new Mappa(nome);
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			if(access.verificaToken(con, token)){
				if(access.CercaMappa(con, nome)){
					m=access.OttieniMappa(con, nome, token);
				}else{
					//errore mappa non trovata //restituire 404
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Mappa non trovata!").build();
				}
			}else{
				//errore mappa non trovata //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non loggato!").build();
			}
				
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return Response.ok(m, MediaType.APPLICATION_JSON).build();
		//return m;
	}
	

	
}