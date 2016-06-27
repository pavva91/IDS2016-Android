package it.univpm.maps.test;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.util.ArrayList;
import org.junit.Test;
import it.univpm.maps.AccessDB;
import it.univpm.maps.Database;
import it.univpm.maps.Edge;
import it.univpm.maps.Map;
import it.univpm.maps.Node;
import it.univpm.maps.User;

public class AccessDBTest {

	@Test
	public final void testGetMap() {
		AccessDB testClass = new AccessDB();
		Map m;
		Database db = new Database();
		Connection con;
		try{	
			con = db.getConnection();
			//test inserimento mappa
			m = testClass.getMap(con, ConfigTest.mapNameValid, ConfigTest.tokenAdminTest);//AUT
			String returnedMapName = m.getName();
			ArrayList<Edge> returnedEdgeList = new ArrayList<Edge>(m.getEdgeList());
			ArrayList<Node> returnedNodeList = new ArrayList<Node>(m.getNodeList());
			assertEquals("Il nome mappa non è diverso da quello atteso!", returnedMapName, ConfigTest.mapNameValid);//il nome della mappa recuperata deve corrispondere a quello richiesto
			assertTrue("Il numero di archi è diverso da quello atteso!", returnedEdgeList.size()==70);//il numero di archi deve corrispondere a quanto atteso
			assertTrue("Il numero di nodi è diverso da quello atteso!", returnedNodeList.size()==62);//il numero di archi deve corrispondere a quanto atteso
			
		}catch(Exception e){
			fail("Il test ha generato un'eccezione!");
		}
	}
	

	@Test
	public final void testInsertUser() {
		AccessDB testClass = new AccessDB();
		Database db = new Database();
		Connection con;
		User u = new User();
		u.setUsername(ConfigTest.usernameTest);
		u.setPassword(ConfigTest.passwordTest);
		try{	
			con = db.getConnection();
			//primo test, inserimento nuovo utente
			testClass.insertUser(con, u);
			assertTrue("", true);
		}catch(Exception e){
			fail("Errore nel tentativo di inserire un nuovo utente");
		}
		try{
			con = db.getConnection();
			//secondo test, inserimento utente duplicato
			testClass.insertUser(con, u);
			fail("Errore, è stato possibile inserire un utente duplicato!");
		}catch(Exception e){
			//se viene generata un'eccezione allora va bene
			assertTrue("", true);
		}
	}
	
	@Test
	public final void testDeleteUser() {
		AccessDB testClass = new AccessDB();
		Database db = new Database();
		Connection con;
		Boolean result;
		User u = new User();
		u.setUsername(ConfigTest.usernameTest);
		try{	
			con = db.getConnection();
			//primo test, cancellazione utente esistente
			result = testClass.deleteUser(con, u);
			assertTrue("Errore nel tentativo di cancellare un utente!", result);
		}catch(Exception e){
			fail("Non è stato possibile cancellare l'utente!");
		}
		try{
			con = db.getConnection();
			//secondo test, cancellazione utente non esistente (già cancellato sopra)
			result = testClass.deleteUser(con, u);
			assertFalse("Errore nel tentativo di cancellare un utente!", result);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	@Test
	public final void testLoginUser() {
		AccessDB testClass = new AccessDB();
		Database db = new Database();
		Connection con;
		String returnedToken;
		try{	
			con = db.getConnection();
			//primo test, login con dati corretti
			returnedToken=testClass.loginUser(con, ConfigTest.usernameAdminTest, ConfigTest.cryptedPasswordAdminTest);
			assertEquals("Errore nel tentativo di eseguire login con credenziali corrette", returnedToken, ConfigTest.tokenAdminTest);
		}catch(Exception e){
			fail("Errore nel tentativo di eseguire login con credenziali corrette");
		}
		try{
			con = db.getConnection();
			//secondo test, login con password errata
			returnedToken=testClass.loginUser(con, ConfigTest.usernameAdminTest, "password_errata");
			fail("Errore nel tentativo di effettuare login con password errata");
		}catch(Exception e){
			//se viene generata un'eccezione allora va bene
			assertTrue("", true);
		}
	}
		
	@Test
	public final void testGetUsersList() {
		AccessDB testClass = new AccessDB();
		Database db = new Database();
		Connection con;
		User u = new User();
		ArrayList<User> usersList = new ArrayList<User>();
		try{	
			con = db.getConnection();
			usersList=testClass.getUsersList(con);
			u=usersList.get(0);
			assertEquals("Username non corrisponde", ConfigTest.usernameAdminTest, u.getUsername());
			assertEquals("Password cifrata non corrisponde", ConfigTest.cryptedPasswordAdminTest, u.getPassword());
			assertEquals("Salt non corrisponde", ConfigTest.saltAdminTest, u.getSalt());
			assertEquals("Token non corrisponde", ConfigTest.tokenAdminTest, u.getToken());
		}catch(Exception e){
			fail("Si è generata un'eccezione nel tentativo di reperire la lista utenti");
		}
	}
	
	@Test
	public final void testSetEmergency() {
		AccessDB testClass = new AccessDB();
		Database db = new Database();
		Connection con;
		try{	
			con = db.getConnection();
			//primo test, setto valore di emergenza su mappa esistente
			testClass.setEmergency(con, ConfigTest.mapNameTest, true);
			assertTrue("", true);
		}catch(Exception e){
			fail("Errore nel tentativo di settare in emergenza una mappa esistente");
		}
		try{
			con = db.getConnection();
			//secondo test, setto valore di emergenza su mappa inesistente
			testClass.setEmergency(con, "mappa_inesistente", true);
			fail("Errore nel tentativo di settare in emergenza una mappa inesistente");
		}catch(Exception e){
			//se viene generata un'eccezione allora va bene
			assertTrue("", true);
		}
		
	}
	
	/*
	@Test
	public final void testNotifyEmergencyToDevice() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testInsertDevice() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testDeleteDevice() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testGetUser() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testUpdatePositionUser() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testUpdateMapImage() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testUpdateEdge() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testGetNodeID() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testEdgeExist() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testGetEdge() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testUpdateMapDate() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testGetMapInfo() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testGetMapList() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInsertMap() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInsertNode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInsertEdge() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDeleteMap() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSearchMap() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetSaltUser() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetIdNode() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testTokenExists() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testVerifyToken() {
		fail("Not yet implemented"); // TODO
	}
	*/
}
