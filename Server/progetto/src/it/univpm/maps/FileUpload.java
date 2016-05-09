package it.univpm.maps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public Response uploadFile(
			@PathParam("mapName")String mapName,
			@PathParam("quota")String quota,
			@QueryParam("token")String token,
			@Context HttpServletRequest request,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
		String timeStamp =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		ServletContext context=request.getSession().getServletContext();
		String SERVER_UPLOAD_LOCATION_FOLDER = context.getRealPath("/../../docroot");
		String filePath = SERVER_UPLOAD_LOCATION_FOLDER	 + "\\" + mapName + "_" + quota + "_" + timeStamp +  "_" + contentDispositionHeader.getFileName();
		String fileUrl = SERVER_URL	+ mapName + "_" + quota + "_" + timeStamp +  "_" + contentDispositionHeader.getFileName();

		// save the file to the server
		saveFile(fileInputStream, filePath);

		String output =  fileUrl;

		return Response.status(200).entity(output).build();

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
