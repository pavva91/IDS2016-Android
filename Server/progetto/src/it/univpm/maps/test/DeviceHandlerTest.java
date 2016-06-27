package it.univpm.maps.test;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.Test;

import it.univpm.maps.Device;
import it.univpm.maps.DeviceHandler;
import it.univpm.maps.MapHandler;

public class DeviceHandlerTest {
	
	@Test
	public final void testInsertDevice() {
		DeviceHandler testClass = new DeviceHandler();
		Response res;
		int returnedStatus;
		//test inserimento device
		Device d = new Device();
		d.setRegistrationID(ConfigTest.deviceIDTest);
		res=testClass.insertDevice(d);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200 nel registrare un deviceID", returnedStatus==200);//lo status deve essere 200 OK
		JSONObject jsonobj = new JSONObject(res.getEntity());
		String returnedDeviceID = jsonobj.getString("registrationID");
		//System.out.println(res.getEntity().toString());;
		assertEquals("Il device Id non corrisponde a quanto atteso!",ConfigTest.returnedDeviceIDTest, returnedDeviceID);//il JSON ritornato deve corrispondere a quanto atteso
		//provo a reinserire di nuovo lo stesso deviceID e deve funzionare
		res=testClass.insertDevice(d);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200 nel registrare un deviceID già esistente", returnedStatus==200);//lo status deve essere 200 OK
		jsonobj = new JSONObject(res.getEntity());
		returnedDeviceID = jsonobj.getString("registrationID");
		assertEquals("Il device Id non corrisponde a quanto atteso!",ConfigTest.returnedDeviceIDTest, returnedDeviceID);//il JSON ritornato deve corrispondere a quanto atteso
	}
	
	@Test
	public final void testDeleteDevice() {
		DeviceHandler testClass = new DeviceHandler();
		Response res;
		int returnedStatus;
		//test cancellazione device
		res=testClass.deleteDevice(ConfigTest.deviceIDTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200 nel cancellare un deviceID", returnedStatus==200);//lo status deve essere 200 OK
		//provo a cancellare di nuovo lo stesso deviceID (quindi inesistente) e deve dare errore 500
		res=testClass.deleteDevice(ConfigTest.deviceIDTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 500 nel cancellare un deviceID inesistente", returnedStatus==500);//lo status deve essere 500 INTERNAL SERVER ERROR
	}


}
