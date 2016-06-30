package it.univpm.maps;


import java.sql.Connection;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("devices/")
public class DeviceHandler {
	
	//costruttore senza parametri
	public DeviceHandler(){
		
	}
	
	//metodo che inserisce un device
	//prende in input il registration id e lo restituisce se tutto ok
	//ritorna INTERNAL SERVER ERROR in caso di errore
	//ritorna OK se tutto ok
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertDevice(Device d){
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.insertDevice(con, d);
			con.close();
		}catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE: Impossibile registrare dispositivo!").build();
		}
		return Response.ok(d, MediaType.APPLICATION_JSON).build();	
	}
	
	//metodo che cancella un device dalla lista
	//prende in input il registration id
	//ritorna INTERNAL SERVER ERROR in caso di errore
	//ritorna OK se tutto ok
	@DELETE
	@Path("/{registrationID}")
	public Response deleteDevice(@PathParam("registrationID")String registrationID){
		Device d=new Device();
		d.setRegistrationID(registrationID);
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();
			access.deleteDevice(con, d);
			con.close();
		}catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERRORE: Impossibile cancellare device!").build();
		}
		return Response.status(Response.Status.OK).entity("OK: Device cancellato correttamente!").build();
	}
	
	
}
