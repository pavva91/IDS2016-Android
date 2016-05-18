package it.univpm.maps;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class Progetto extends ResourceConfig{

	public Progetto(){
		packages("it.univpm.maps");
		register(MultiPartFeature.class);
	}

}



	

	
	
	


