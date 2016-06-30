package it.univpm.maps;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class PushNotificationHandler {

	public PushNotificationHandler(){	
	}
	
	public void sendPushNotification(String deviceID, Boolean emergency, String message) throws Exception{	
		message="{ \"data\":{\"emergency\": \""+emergency+"\",\"message\": \""+message+"\"},\"to\" :\""+deviceID+"\"}";
		byte[] postData = message.getBytes(StandardCharsets.UTF_8);
	//	int    postDataLength = postData.length;
		URL url = new URL(Config.NOTIFICATION_URL);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");           
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Type", "application/json"); 
	//	conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Authorization", "key="+Config.APIKEY);
		conn.setUseCaches(false);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.write(postData);
		wr.close();
		//risposta che non gestisco, SENZA QUESTE RIGHE NON ARRIVANO LE NOTIFICHE AL CLIENT
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
		//String line; 
		while ((rd.readLine()) != null) { 
		    // Process line... 
			//System.out.println(line);
		} 
		rd.close(); 
		
	}
	
}
