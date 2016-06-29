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


@Path("/maps")
public class MapHandler{
	
	//costruttore senza parametri
	public MapHandler(){
		
	}
	
	//metodo che carica una mappa sul DB
	//ritorna FORBIDDEN se l'utente non ha i privilegi necessari
	//ritorna CONFLICT se si tenta di caricare una mappa che già esiste sul DB
	//ritorna CONFLICT se si verificano problemi SQL
	//ritorna OK se la mappa viene caricata correttamente
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response createMap(Map m, @QueryParam("token")String token){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			if(access.getUser(con, token).getUsername().equals(Config.ADMINISTRATOR_USER))
				if(!access.searchMap(con, m.getName())){
					access.insertMap(con, m);
				}else{
					//access.cancellaMappa(con, m);
					//errore mappa già esistente
					return Response.status(Response.Status.CONFLICT).entity("ERRORE: Impossibile inserire mappa!").build();
				}	
			else
				//errore utente non autorizzato a caricare mappe
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
		}catch (Exception ex){
			//errore inserimento mappa //restituire 409
			return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
		}
		return Response.ok(m, MediaType.APPLICATION_JSON).build();
	}
	
	//metodo che cancella una mappa dal DB
	//ritorna FORBIDDEN se l'utente non ha i privilegi necessari
	//ritorna NOT FOUND se si tenta di cancellare una mappa che non esiste sul DB
	//ritorna CONFLICT se si verificano problemi SQL
	//ritorna OK se la mappa cancellata correttamente
	@DELETE
	@Path("/{mapName}")
	@Produces("application/json")
	public Response deleteMap(@PathParam("mapName") String mapName, @QueryParam("token")String token, @Context HttpServletRequest request){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			FileHandler fileHandler = new FileHandler();
			Map m = new Map();
			m.setName(mapName);
			if(access.getUser(con, token).getUsername().equals(Config.ADMINISTRATOR_USER))
				if(access.searchMap(con, mapName)){
					access.deleteMap(con, m);//cancello mappa
					fileHandler.deleteFolder(request, mapName);//cancello file immagine e cartella
				}else{
					//errore mappa non trovata
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Mappa non trovata!").build();
				}	
			else 
				//errore utente non autorizzato a cancellare mappe
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
		}catch (NullPointerException ex){
			//questa eccezione si verifica se non esiste la cartella contenente la mappa, viene ignorata per i test
			//altrimenti non si verifica mai
		}catch (Exception e){
			return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok("Mappa cancellata!").build();
	}

	//metodo che ritorna un elenco di mappe presenti nel DB
	//ritorna FORBIDDEN se l'utente è sconosciuto
	//ritorna OK se non ci sono problemi
	@GET
	@Produces("application/json")
	public Response listMaps(@QueryParam("token")String token){
		ArrayList<Map> listaMappe = new ArrayList<Map>();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			if(access.tokenExist(con, token)){
				listaMappe = access.getMapList(con);
			}else{
				//errore mappa non trovata //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non loggato!").build();
			}	
		}catch (Exception ex){
			//errore generico restituire 500
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE!").build();
		}
		GenericEntity entity = new GenericEntity<ArrayList<Map>>(listaMappe) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
		//return Response.ok(listaMappe, MediaType.APPLICATION_JSON).build();
	}
	
	//metodo che ritorna una mappa a partire dal suo nome e dal token utente
	//ritorna FORBIDDEN se l'utente è sconosciuto
	//ritorna NOT FOUND se la mappa non esiste sul DB
	//ritorna OK se non ci sono problemi
	@GET
	@Path("/{mapName}")
	@Produces("application/json")
	public Response getMap(@PathParam("mapName") String mapName, @QueryParam("token")String token){
		AccessDB access = new AccessDB();
		Map m=new Map(mapName);
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			if(access.tokenExist(con, token)){
				if(access.searchMap(con, mapName)){
					m=access.getMap(con, mapName, token);
				}else{
					//errore mappa non trovata //restituire 404
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Mappa non trovata!").build();
				}
			}else{
				//errore mappa non trovata //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
			}				
		}catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE!").build();
		}
		return Response.ok(m, MediaType.APPLICATION_JSON).build();
	}
	
	//metodo che permette di aggiornare i valori di peso di un arco
	//prende in input l'arco con i nuovi valori aggiornati e restituisce l'arco aggiornato con tutti i dati presenti sul DB
	//ritorna FORBIDDEN se l'utente non ha i privilegi necessari
	//ritorna NOT FOUND se l'arco non esiste
	//ritorns INTERNAL SERVER ERROR in caso di errore diverso dai precedenti
	//ritorna OK se l'arco viene aggiornato correttamente
	@PUT
	@Path("{mapName}/edges")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateEdge(Edge e, @PathParam("mapName") String mapName, @QueryParam("token")String token){
		try{
			AccessDB access = new AccessDB();
			Database db = new Database();
			Connection con = db.getConnection();
			if(access.getUser(con, token).getUsername().equals(Config.ADMINISTRATOR_USER)){
				int from = access.getNodeId(con, e.getFrom(), mapName);
				int to = access.getNodeId(con, e.getTo(), mapName);
				if(access.edgeExist(con, from, to)){
					e=access.updateEdge(con, e, from, to);//aggiorno arco
					access.updateMapDate(con, mapName);//cambio la data di aggiornamento mappa
				}else{
					//errore arco non trovato //restituire 404
					return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Arco non trovato!").build();
				}
			}else{
				//errore permessi insufficienti //restituire 403
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
			}	
		}catch (Exception ex){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE!").build();
		}
		return Response.ok(e, MediaType.APPLICATION_JSON).build();
	}
	
		//metodo che ritorna le informazioni su una mappa presente nel DB
		//ritorna FORBIDDEN se l'utente è sconosciuto
		//ritorna OK se non ci sono problemi
		@GET
		@Path("{mapName}/info")
		@Produces("application/json")
		public Response getMapInfo(@PathParam("mapName") String mapName, @QueryParam("token")String token){
			Map m=new Map();
			try{
				Database db = new Database();
				Connection con = db.getConnection();
				AccessDB access = new AccessDB();
				if(access.tokenExist(con, token)){
					if(access.searchMap(con, mapName))
						m = access.getMapInfo(con, mapName);
					else
						//errore mappa non trovata //restituire 403
						return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Mappa non trovata!").build();
				}else{
					//errore utente non autorizzato restituire 403
					return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non loggato!").build();
				}	
			}catch (Exception ex){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE!").build();
			}
			return Response.ok(m, MediaType.APPLICATION_JSON).build();
		}
	
		
		//metodo che permette di attivare o disattivare la modalità allarme
		//ritorna FORBIDDEN se l'utente non ha i privilegi necessari
		//ritorna NOT FOUND 404 se si la mappa non esiste
		//ritorna INTERNAL SERVER ERROR 500 se si ci sono stati problemi
		//ritorna OK se la mappa viene caricata correttamente
		@POST
		@Produces("application/json")
		@Path("{mapName}/emergency/{emergency}")
		public Response handleAlertMap(@PathParam("mapName") String mapName, @QueryParam("token")String token, @PathParam("emergency") Boolean emergency){
			Map m=new Map();
			try{
				Database db = new Database();
				Connection con = db.getConnection();
				AccessDB access = new AccessDB();
				if(access.getUser(con, token).getUsername().equals(Config.ADMINISTRATOR_USER))
					if(!access.searchMap(con, mapName)){
						//errore mappa non trovata
						return Response.status(Response.Status.NOT_FOUND).entity("ERRORE: Mappa non trovata!").build();
					}else{
						access.setEmergency(con, mapName, emergency);
						access.notifyEmergencyToDevice(con, emergency);
						access.updateMapDate(con, mapName);
						m = access.getMapInfo(con, mapName);
					}	
				else
					//errore utente non autorizzato a modificare mappe
					return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
			}catch (Exception ex){
				//errore generico restituire 500
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore!").build();
			}
			return Response.ok(m, MediaType.APPLICATION_JSON).build();
		}
		
		
		
}