package it.univpm.maps;

import it.univpm.maps.AccessDB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/maps/upload")
public class FileUpload {

	//@Context ServletContext context;
	private String SERVER_URL = "http://localhost:8080/";

	public FileUpload(){
		
	}
	
	@POST
	@Path("{mapName}/{quota}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response uploadFile(
			@PathParam("mapName")String mapName,
			@PathParam("quota")String quota,
			@QueryParam("token")String token,
			@Context HttpServletRequest request,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
		ServletContext context=request.getSession().getServletContext();
		String timeStamp =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String SERVER_UPLOAD_LOCATION_FOLDER = context.getRealPath("/../../docroot/");
		String filePath = SERVER_UPLOAD_LOCATION_FOLDER	 + "\\" + mapName + "\\" + mapName + "_" + quota + "_" + timeStamp +  "_" + contentDispositionHeader.getFileName();
		String fileUrl = SERVER_URL	+ "/" + mapName + "/" + mapName + "_" + quota + "_" + timeStamp +  "_" + contentDispositionHeader.getFileName();
		try{
			Database db = new Database();
			Connection con = db.getConnection();
			AccessDB access = new AccessDB();		
			java.nio.file.Path path = Paths.get(SERVER_UPLOAD_LOCATION_FOLDER);
			File mapDir = new File(path + "/" + mapName);
			if (!mapDir.exists())
			   mapDir.mkdir();
	
			//cancello vecchio file
			for(File f: mapDir.listFiles())
			    if(f.getName().startsWith(mapName + "_" + quota))
			        f.delete();
	
			// save the file to the server
			saveFile(fileInputStream, filePath);
			access.aggiornaImmagineMappa(con, mapName, Integer.parseInt(quota), fileUrl);
			
	    }catch(SecurityException se){
	    	return Response.status(Response.Status.CONFLICT).entity("ERRORE: Impossibile creare cartella per salvataggio immagini!").build();
		}catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(200).entity(fileUrl).build();
	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream,
			String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
