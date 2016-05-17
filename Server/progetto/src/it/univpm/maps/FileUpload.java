package it.univpm.maps;

import it.univpm.maps.AccessDB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition.FormDataContentDispositionBuilder;
import org.glassfish.jersey.media.multipart.FormDataParam;

//import org.glassfish.jersey.media.multipart.FormDataContentDisposition.FormDataContentDispositionBuilder;



//import org.glassfish.jersey.media.multipart.FormDataContentDisposition.FormDataContentDispositionBuilder;
//import org.glassfish.jersey.media.multipart.FormDataParam;
//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;




@Path("/maps/upload")
public class FileUpload {

	//@Context ServletContext context;
	private String SERVER_URL = "http://localhost:8080/";

	//costruttore senza parametri
	public FileUpload(){
		
	}
	
	@POST

	@Path("{mapName}/{quota}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response uploadFile(
			@PathParam("mapName")String mapName,
			@PathParam("quota")int quota,
			@QueryParam("token")String token,
			@Context HttpServletRequest request,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDispositionBuilder fileDetail) {
		
		ServletContext context=request.getSession().getServletContext();
		String timeStamp =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String SERVER_UPLOAD_LOCATION_FOLDER = context.getRealPath("/../../docroot/");
		String filePath = SERVER_UPLOAD_LOCATION_FOLDER	 + "\\" + mapName + "\\" + mapName + "_" + quota + "_" + timeStamp +  "_" + fileDetail.build().getFileName();
		String fileUrl = SERVER_URL	 + mapName + "/" + mapName + "_" + quota + "_" + timeStamp +  "_" + fileDetail.build().getFileName();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();		
			//se l'utente non è l'amministratore
			if(!access.getUtente(con, token).getUsername().equals(Config.ADMINISTRATOR_USER))
				//errore utente non autorizzato a caricare mappe
				return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non autorizzato!").build();
			//se non esiste nel DB mappa con nome e quota da parametri allora restituisco errore
			if (!access.verificaQuota(con, mapName, quota))
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ERRORE: Quota non trovata o mappa non presente!").build();
			
			//creo cartella con nome mappa se non esiste
			java.nio.file.Path path = Paths.get(SERVER_UPLOAD_LOCATION_FOLDER);
			File mapDir = new File(path + "/" + mapName);
			if (!mapDir.exists())
			   mapDir.mkdir();
	
			//cancello vecchio file
			for(File f: mapDir.listFiles())
			    if(f.getName().startsWith(mapName + "_" + quota))
			        f.delete();
	
			//salvo file sul server
			saveFile(fileInputStream, filePath);
			access.aggiornaImmagineMappa(con, mapName, quota, fileUrl);
			
	    }catch(SecurityException se){
	    	return Response.status(Response.Status.CONFLICT).entity("ERRORE: Impossibile creare cartella per salvataggio immagini!").build();
	    }catch(SQLException esql){
	    	return Response.status(Response.Status.FORBIDDEN).entity("ERRORE: Utente non trovato o non autorizzato!").build();
	    }catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.CREATED).entity(fileUrl).build();
	}
	
	
	

	//funzione che salva file nel filesystem
	private void saveFile(InputStream uploadedInputStream, String serverLocation) {
		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1)
				outpuStream.write(bytes, 0, read);
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//metodo che cancella una cartella (comprese sottocartelle e file) dal filesystem
	public void deleteFolder(HttpServletRequest request, String folderName) {
		ServletContext context=request.getSession().getServletContext();
		String SERVER_UPLOAD_LOCATION_FOLDER = context.getRealPath("/../../docroot/");
		File folder = new File(SERVER_UPLOAD_LOCATION_FOLDER + "\\" + folderName);
		String[]entries = folder.list();
		for(String s: entries){
			File currentFile = new File(folder.getPath(),s);
		    currentFile.delete();
		}
		folder.delete();
	}
	
	
	
}
