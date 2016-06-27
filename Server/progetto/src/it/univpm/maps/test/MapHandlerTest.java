package it.univpm.maps.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import it.univpm.maps.AccessDB;
import it.univpm.maps.Database;
import it.univpm.maps.Edge;
import it.univpm.maps.Image;
import it.univpm.maps.Map;
import it.univpm.maps.MapHandler;
import it.univpm.maps.Node;
import it.univpm.maps.Node.NodeType;
import static org.mockito.Mockito.*;


public class MapHandlerTest {

	
	
	@Test
		public final void testCreateMap() {
			MapHandler testClass = new MapHandler();
			Response res;
			//creo mappa
			Map m = new Map();
			m.setName(ConfigTest.mapNameTest);
			m.setEmergency(false);
			ArrayList<Node> nodeList = new ArrayList<Node>();
			//creo nodi
			Node n1 = new Node();
			Node n2 = new Node();
			Node n3 = new Node();
			n1.setCode("n1");
			n2.setCode("n2");
			n3.setCode("n3");
			n1.setDescr("desc n1");
			n2.setDescr("desc n2");
			n3.setDescr("desc n3");
			n1.setQuota(1);
			n2.setQuota(2);
			n3.setQuota(3);
			n1.setType(NodeType.emergency_exit);
			n2.setType(NodeType.exit);
			n3.setType(NodeType.other);
			n1.setWidth(10);
			n2.setWidth(20);
			n3.setWidth(30);
			n1.setX(11);
			n2.setX(22);
			n3.setX(33);
			n1.setY(44);
			n2.setY(55);
			n3.setY(66);
			nodeList.add(n1);
			nodeList.add(n2);
			nodeList.add(n3);
			m.setNodeList(nodeList);
			//creo archi
			ArrayList<Edge> edgeList = new ArrayList<Edge>();
			Edge e1 = new Edge();
			Edge e2 = new Edge();
			Edge e3 = new Edge();
			e1.setFrom(n1.getCode());//arco da n1 a n2
			e1.setTo(n2.getCode());
			e2.setFrom(n2.getCode());//arco da n2 a n3
			e2.setTo(n3.getCode());
			e3.setFrom(n3.getCode());//arco da n3 a n1
			e3.setTo(n1.getCode());
			e1.setArea(101);
			e2.setArea(201);
			e3.setArea(301);
			e1.setLength(10);
			e2.setLength(11);
			e3.setLength(12);
			e1.setV(0.33);
			e2.setV(0.66);
			e3.setV(1.0);
			e1.setI(0.33);
			e2.setI(0.66);
			e3.setI(1.0);
			e1.setC(0.33);
			e2.setC(0.66);
			e3.setC(1.0);
			edgeList.add(e1);
			edgeList.add(e2);
			edgeList.add(e3);
			m.setEdgeList(edgeList);
			//creo immagini
			ArrayList<Image> imgList = new ArrayList<Image>();
			Image img1 = new Image();
			Image img2 = new Image();
			Image img3 = new Image();
			img1.setMap(m.getName());
			img2.setMap(m.getName());
			img3.setMap(m.getName());
			img1.setQuota(1);
			img2.setQuota(2);
			img3.setQuota(3);
			img1.setUrl("url.immagine1");
			img2.setUrl("url.immagine2");
			img3.setUrl("url.immagine3");
			imgList.add(img1);
			imgList.add(img2);
			imgList.add(img3);
			m.setImageList(imgList);
			//test inserimento mappa
			res = testClass.createMap(m, ConfigTest.tokenAdminTest);//AUT
			int returnedStatus=res.getStatus();
			assertTrue("Status diverso da 200", returnedStatus==200);//lo status deve essere 200 OK
			JSONObject jsonobj = new JSONObject(res.getEntity());
			String returnedmapname = jsonobj.getString("nome");
			String returneddatelastupdate = jsonobj.getString("lastUpdateMap");
			Boolean returnedemergency = jsonobj.getBoolean("emergency");
			
			assertFalse("Il valore di emergenza è sbagliato!", returnedemergency);//emergenza deve valere falso
			assertTrue("Il nome della mappa non corrisponde!", ConfigTest.mapNameTest.equals(returnedmapname));//verifico che il nome della mappa corrisponda
			String currentdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			assertTrue("La data di aggiornamento mappa non è corretta!", returneddatelastupdate.substring(0, 10).equals(currentdate.substring(0, 10)));//verifico che il nome della mappa corrisponda
		}

	@Test
	public final void testDeleteMap() {
		MapHandler testClass = new MapHandler();
		Response res;
		int returnedStatus;
		HttpServletRequest request1 = mock(HttpServletRequest.class); 
		HttpServletRequest request2 = mock(HttpServletRequest.class); 
		HttpServletRequest request3 = mock(HttpServletRequest.class); 
		HttpSession session=mock(HttpSession.class);
		ServletContext context=mock(ServletContext.class);
		Mockito.when(request2.getSession()).thenReturn(session);
		Mockito.when(session.getServletContext()).thenReturn(context);
		String realpath = new String("C:\\glassfish4\\glassfish\\domains\\domain1\\docroot");
		Mockito.when(context.getRealPath("/../../docroot/")).thenReturn(realpath);
		//canello mappa test con token errato
		res = testClass.deleteMap(ConfigTest.mapNameTest, "tokeninesistente", request1);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 409 tentando di cancellare una mappa con un token inesistente!", returnedStatus==409);//lo status deve essere 409 CONFLICT
		//canello mappa test
		res = testClass.deleteMap(ConfigTest.mapNameTest, ConfigTest.tokenAdminTest, request2);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200, mappa non cancellata", returnedStatus==200);//lo status deve essere 200 OK
		//ricancello mappa test che non dovrebbe esistere
		res = testClass.deleteMap(ConfigTest.mapNameTest, ConfigTest.tokenAdminTest, request3);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 404, controllare che la mappa non esista", returnedStatus==404);//lo status deve essere 404 NOT FOUND
	}
	
	@Test
	public final void testListMaps() {
		MapHandler testClass = new MapHandler();
		Response res;
		int returnedStatus;
		//test con token utente
		res=testClass.listMaps(ConfigTest.tokenUserTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200 nel tentativo di reperire la lista mappe", returnedStatus==200);//lo status deve essere 200 OK
		//test con token sconosciuto
		res=testClass.listMaps("token_inesistente");//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 403 nel tentativo di reperire la lista mappe", returnedStatus==403);//lo status deve essere 403 FORBIDDEN
	}
	
	@Test
	public final void testGetMap() throws Exception {
		MapHandler testClass = new MapHandler();
		Response res;
		int returnedStatus;
		//test dove recupero la mappa valida
		res=testClass.getMap(ConfigTest.mapNameValid, ConfigTest.tokenAdminTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200 nel tentativo di scaricare la mappa valida", returnedStatus==200);//lo status deve essere 200 OK
		JSONObject jsonobj = new JSONObject(res.getEntity());
		String returnedMapName = jsonobj.getString("nome");
		assertEquals("Il nome mappa non corrisponde a quanto atteso!", ConfigTest.mapNameValid, returnedMapName);
		//test dove recupero la mappa valida usando un token sconosciuto
		res=testClass.getMap(ConfigTest.mapNameValid, "tokeninesistente");//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 403 nel tentativo di scaricare la mappa valida con token sconosciuto", returnedStatus==403);//lo status deve essere 403 FORBIDDEN
		//test dove cerco di recuperare una mappa inesistente
		res=testClass.getMap("mappa inesistente", ConfigTest.tokenAdminTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 404 nel tentativo di scaricare la mappa valida con token sconosciuto", returnedStatus==404);//lo status deve essere 404 NOT FOUND
		
		//test dove cerco di generare una eccezione SQL ###NON FUNZIONA####
	/*	MapHandler mapHandler = mock(MapHandler.class); 
		when(mapHandler.getMap(ConfigTest.mapNameValid, ConfigTest.tokenAdminTest)).thenThrow(new RuntimeException());
		AccessDB access = mock(AccessDB.class);
		Database db = new Database();
		Connection con;
		con = db.getConnection();
		when(access.tokenExist(con, ConfigTest.tokenAdminTest)).thenThrow(new RuntimeException());
		res=mapHandler.getMap(ConfigTest.mapNameValid, ConfigTest.tokenAdminTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 500 nel tentativo di scaricare la mappa valida con token sconosciuto", returnedStatus==500);//lo status deve essere 500 INTERNAL SERVER ERROR
		*/		
	}
	
	@Test
	public final void testUpdateEdge() {
		MapHandler testClass = new MapHandler();
		Response res;
		int returnedStatus;
		Edge e = new Edge();
		e.setFrom("150A3");
		e.setTo("155A7");//arco inesistente da 155A7 a 155A3
		e.setV(0.22);
		e.setI(0.23);
		e.setC(0.24);
		e.setNumPers(19);
		//test su arco inesistente
		res=testClass.updateEdge(e, ConfigTest.mapNameValid, ConfigTest.tokenAdminTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 404 nel tentativo di modificare un arco inesistente", returnedStatus==404);//lo status deve essere 404 NOT FOUND
		//test su arco esistente
		e.setTo("155A3");//arco inesistente da 155A3 a 150A3
		res=testClass.updateEdge(e, ConfigTest.mapNameValid, ConfigTest.tokenAdminTest);//AUT
		JSONObject jsonobj = new JSONObject(res.getEntity());
		returnedStatus=res.getStatus();
		//System.out.println(jsonobj);
		assertTrue("Status diverso da 200 nel tentativo di modificare un arco valido", returnedStatus==200);//lo status deve essere 200 OK
		String returnedFrom = jsonobj.getString("from");
		String returnedTo = jsonobj.getString("to");
		double v = jsonobj.getDouble("v");
		double i = jsonobj.getDouble("i");
		double c = jsonobj.getDouble("c");
		double los = jsonobj.getDouble("los");
		double length = jsonobj.getDouble("length");
		double area = jsonobj.getDouble("area");
		double emgCost = jsonobj.getDouble("emgCost");
		int numPers = jsonobj.getInt("numPers");
		assertEquals("La partenza dell'arco non corrisponde!", "150A3", returnedFrom);//verifico che il nodo di partenza sia quello atteso
		assertEquals("La destinazione dell'arco non corrisponde!", "155A3", returnedTo);//verifico che il nodo di partenza sia quello atteso
		assertTrue("Il valore di V non è quello atteso!", v==0.22);//verifico che il valore di V sia quello atteso
		assertTrue("Il valore di I non è quello atteso!", i==0.23);//verifico che il valore di I sia quello atteso
		assertTrue("Il valore di C non è quello atteso!", c==0.24);//verifico che il valore di C sia quello atteso
		assertTrue("Il valore di LOS non è quello atteso!", los==0.33);//verifico che il valore di LOS sia quello atteso
		assertTrue("Il valore di numPers non è quello atteso!", numPers==19);//verifico che il valore di numPers sia quello atteso
		assertTrue("Il valore di length non è quello atteso!", length==25);//verifico che il valore di C sia quello atteso
		assertTrue("Il valore di emgCost non è quello atteso!", emgCost==0.24590000000000004);//verifico che il valore di emgCost sia quello atteso
		//test su arco esistente ma permessi insufficienti
		res=testClass.updateEdge(e, ConfigTest.mapNameValid, ConfigTest.tokenUserTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 403 nel tentativo di modificare un arco senza permessi di admin", returnedStatus==403);//lo status deve essere 403 FORBIDDEN
	}
	
	@Test
	public final void testGetMapInfo() {
		MapHandler testClass = new MapHandler();
		Response res;
		int returnedStatus;
		//test su mappa inesistente e token corretto
		res=testClass.getMapInfo("mappa_inesistente", ConfigTest.tokenAdminTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 404 nel tentativo di recuperare info su una mappa inesistente", returnedStatus==404);//lo status deve essere 404 NOT FOUND
		//test su mappa esistente e permessi corretti
		res=testClass.getMapInfo(ConfigTest.mapNameValid, ConfigTest.tokenAdminTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200 nel tentativo di recuperare info sulla mappa", returnedStatus==200);//lo status deve essere 200 OK
		JSONObject jsonobj = new JSONObject(res.getEntity());
		String returnedMapName = jsonobj.getString("nome");
		assertEquals("Il nome mappa non corrisponde a quanto atteso!", ConfigTest.mapNameValid, returnedMapName);//il nome mappa deve essere quello atteso
		//test su mappa esistente ma permessi insufficienti
		res=testClass.getMapInfo(ConfigTest.mapNameValid, "token_inesistente");//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 403 nel tentativo di recuperare info sulla mappa senza token valido", returnedStatus==403);//lo status deve essere 403 FORBIDDEN
	}

	@Test
	public final void testHandleAlertMap() {
		MapHandler testClass = new MapHandler();
		Response res;
		int returnedStatus;
		//test su mappa inesistente e token corretto
		res=testClass.handleAlertMap("mappa_inesistente", ConfigTest.tokenAdminTest, true);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 404 nel tentativo di settare l'emergenza su una mappa inesistente", returnedStatus==404);//lo status deve essere 404 NOT FOUND
		//test su mappa esistente e permessi corretti
		res=testClass.handleAlertMap(ConfigTest.mapNameValid, ConfigTest.tokenAdminTest, true);//AUT
		JSONObject jsonobj = new JSONObject(res.getEntity());
		returnedStatus=res.getStatus();
		//System.out.println(jsonobj);
		assertTrue("Status diverso da 200 nel tentativo di modificare un arco valido", returnedStatus==200);//lo status deve essere 200 OK
		String returnedMapName = jsonobj.getString("nome");
		Boolean returnedEmergency = jsonobj.getBoolean("emergency");
		assertTrue("Il valore di nome mappa non è quello atteso!", returnedMapName==ConfigTest.mapNameValid);//verifico che il valore di returnedAlarm sia quello atteso
		assertTrue("Il valore di alarm non è quello atteso!", returnedEmergency);//verifico che il valore di returnedAlarm sia quello atteso
		String returnedSateLastUpdate = jsonobj.getString("lastUpdateMap");
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		assertTrue("La data di aggiornamento mappa non è corretta!", returnedSateLastUpdate.substring(0, 10).equals(currentDate.substring(0, 10)));//verifico che il nome della mappa corrisponda
		//test su mappa esistente ma permessi insufficienti
		res=testClass.handleAlertMap(ConfigTest.mapNameValid, ConfigTest.tokenUserTest, true);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 403 nel tentativo di modificare un arco senza permessi di admin", returnedStatus==403);//lo status deve essere 403 FORBIDDEN
	}
}
