package it.univpm.maps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("maps/")
public class MapHandler{
	
	//costruttore senza parametri
	public MapHandler(){
		
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response CreaMappa(Map m, @QueryParam("token")String token){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			if(access.getUtente(con, token).getUsername().equals(Config.ADMINISTRATOR_USER))
				if(!access.searchMap(con, m.getNome())){
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
	public Response CancellaMappa(@PathParam("nome") String nome, @QueryParam("token")String token, @Context HttpServletRequest request){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			FileUpload fileHandler = new FileUpload();
			Map m = new Map();
			m.setNome(nome);
			if(access.getUtente(con, token).getUsername().equals(Config.ADMINISTRATOR_USER))
				if(access.searchMap(con, nome)){
					access.deleteMap(con, m);
					fileHandler.deleteFolder(request, nome);
				}else{
					//errore mappa non trovata
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Mappa non trovata!").build();
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
		return Response.ok("Mappa cancellata!").build();
	}

	@GET
	@Produces("application/json")
	public Response ElencaMappe(@QueryParam("token")String token){
		ArrayList<Map> listaMappe = new ArrayList<Map>();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			if(access.verifyToken(con, token)){
				listaMappe = access.getMapList(con);
			}else{
				//errore mappa non trovata //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non loggato!").build();
			}	
		}catch (Exception e){
			e.printStackTrace();
		}
		GenericEntity entity = new GenericEntity<ArrayList<Map>>(listaMappe) {};

		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("{name}")
	@Produces("application/json")
	public Response getMappa(@PathParam("name") String nome, @QueryParam("token")String token){
		AccessDB access = new AccessDB();
		Map m=new Map(nome);
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			if(access.getUtente(con, token).getUsername().equals(Config.ADMINISTRATOR_USER)){
				if(access.searchMap(con, nome)){
					m=access.obtainMap(con, nome, token);
				}else{
					//errore mappa non trovata //restituire 404
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Mappa non trovata!").build();
				}
			}else{
				//errore mappa non trovata //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
			}
				
		}catch (Exception e){
			e.printStackTrace();
		}
		return Response.ok(m, MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("{name}/edges")
	@Consumes("application/json")
	@Produces("application/json")
	public Response UpdateEdge(Edge e, @PathParam("name") String nome, @QueryParam("token")String token){
		try{
			AccessDB access = new AccessDB();
			Database db = new Database();
			Connection con = db.getConnection();
			if(access.verifyToken(con, token)){
				int edgeFrom = access.getNodeId(con, e.getPartenza(), nome);
				int edgeTo = access.getNodeId(con, e.getDestinazione(), nome);
				if(access.edgeExist(con, edgeFrom, edgeTo)){
					e=access.updateEdge(con, e, edgeFrom, edgeTo);
				}else{
					//errore arco non trovato //restituire 404
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Arco non trovato!").build();
				}
			}else{
				//errore arco non trovato //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non loggato!").build();
			}
				
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return Response.ok(e, MediaType.APPLICATION_JSON).build();
	}
	

	
}