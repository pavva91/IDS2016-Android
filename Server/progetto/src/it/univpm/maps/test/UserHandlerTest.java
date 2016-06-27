package it.univpm.maps.test;

import org.json.*;
import static org.junit.Assert.*;
import javax.ws.rs.core.Response;
import org.junit.Test;
import it.univpm.maps.User;
import it.univpm.maps.UserHandler;

public class UserHandlerTest {

	
	@Test
	public final void testCreateUser() {
		UserHandler testClass = new UserHandler();
		User u = new User();
		Response res;
		u.setUsername(ConfigTest.usernameTest);
		u.setPassword(ConfigTest.passwordTest);
		res = testClass.createUser(u);//AUT
		int returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200", returnedStatus==200);//lo status deve essere 200 OK
		JSONObject jsonobj = new JSONObject(res.getEntity());
		String returnedUsername = jsonobj.getString("username");
		String returnedPassword = jsonobj.getString("password");
		String returnedToken = jsonobj.getString("token");	
		assertTrue("Username non è lo stesso!", ConfigTest.usernameTest.equals(returnedUsername));//username deve essere lo stesso
		assertFalse("La password non è stata cifrata ?", ConfigTest.passwordTest.equals(returnedPassword));//la password viene cifrata quindi deve essere diversa da quella che l'utente ha inserito
		assertFalse("Token generato casualmente OK", "".equals(returnedToken));//verifico che il token non sia vuoto
		
	}

	@Test
	public final void testDeleteUser() {
		UserHandler testClass = new UserHandler();
		Response res;
		int returnedStatus;
		//canello utente test
		res = testClass.deleteUser(ConfigTest.usernameTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200, utente non cancellato", returnedStatus==200);//lo status deve essere 200 OK
		//ricancello utente test che non dovrebbe esistere
		res = testClass.deleteUser(ConfigTest.usernameTest);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 409, utente non cancellato", returnedStatus==404);//lo status deve essere 404 NOT FOUND
		
	}
	
	
	@Test
	public final void testUserLogin() {
		UserHandler testClass = new UserHandler();
		Response res;
		int returnedStatus;
		//login con credenziali di admin (o di altro utente, purchè esistente)
		res = testClass.userLogin("admin", "univpm");//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200", returnedStatus==200);//lo status deve essere 200 OK
		JSONObject jsonobj = new JSONObject(res.getEntity());
		String returnedUsername = jsonobj.getString("username");
		String returnedPassword = jsonobj.getString("password");
		String returnedToken = jsonobj.getString("token");	
		assertTrue("Username non è lo stesso!", ConfigTest.usernameAdminTest.equals(returnedUsername));//username deve essere lo stesso
		assertFalse("La password non è stata cifrata ?", ConfigTest.passwordAdminTest.equals(returnedPassword));//la password viene cifrata quindi deve essere diversa da quella che l'utente ha inserito
		assertTrue("Token non corretto!", ConfigTest.tokenAdminTest.equals(returnedToken));//verifico che il token sia quello giusto
		//login con username non valida
		res = testClass.userLogin("utenteinesistente", "passwordinesistente");//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 500", returnedStatus==500);//lo status deve essere 500 INTERNAL SERVER ERROR
		//login con username valida e password errata
		res = testClass.userLogin(ConfigTest.usernameAdminTest, "passwordinesistente");//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 409", returnedStatus==409);//lo status deve essere 409 CONFLICT
	}

	@Test
	public final void testUpdateUserPosition() {
		UserHandler testClass = new UserHandler();
		Response res;
		int newPosition = 1121;//nuova posisione di test
		int returnedStatus;
		User u = new User();//creo un utente
		u.setPosition(newPosition);//setto la nuova posizione
		//login con credenziali di admin (o di altro utente, purchè esistente)
		res = testClass.updateUserPosition(ConfigTest.usernameAdminTest, ConfigTest.tokenAdminTest, u);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200", returnedStatus==200);//lo status deve essere 200 OK
		JSONObject jsonobj = new JSONObject(res.getEntity());
		int returnedPosition = jsonobj.getInt("position");
		assertTrue("La posizione non è quella attesa!", newPosition==returnedPosition);//la posizione deve essere quella attesa
		//ricambio la posizione per evitare errori di ripetizione nel DB
		u.setPosition(u.getPosition()+1);//setto la posizione nel nodo successivo
		res = testClass.updateUserPosition(ConfigTest.usernameAdminTest, ConfigTest.tokenAdminTest, u);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Status diverso da 200", returnedStatus==200);//lo status deve essere 200 OK
		jsonobj = new JSONObject(res.getEntity());
		returnedPosition = jsonobj.getInt("position");
		assertTrue("La posizione non è quella attesa!", newPosition+1==returnedPosition);//la posizione deve essere quella attesa
		//testo il metodo con una posizione inesistente
		u.setPosition(999999999);//setto la posizione nel nodo successivo
		res = testClass.updateUserPosition(ConfigTest.usernameAdminTest, ConfigTest.tokenAdminTest, u);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Test con posizione inesistente ha dato risultato diverso da 409", returnedStatus==409);//lo status deve essere 409 CONFLICT
		//testo il metodo con una token inesistente
		u.setPosition(999999999);//setto la posizione nel nodo successivo
		res = testClass.updateUserPosition(ConfigTest.usernameAdminTest, "ffffffff", u);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Test con token inesistente ha dato risultato diverso da 409", returnedStatus==409);//lo status deve essere 409 CONFLICT
		//testo il metodo con un username inesistente
		u.setPosition(999999999);//setto la posizione nel nodo successivo
		res = testClass.updateUserPosition("usernameinesistente", ConfigTest.tokenAdminTest, u);//AUT
		returnedStatus=res.getStatus();
		assertTrue("Test con username inesistente ha dato risultato diverso da 409", returnedStatus==409);//lo status deve essere 409 CONFLICT
	}

	@Test
	public final void testCryptPassword() {
		UserHandler testClass = new UserHandler();
		String returnedcryptedpassword = new String();
		try{
			returnedcryptedpassword = testClass.cryptPassword(ConfigTest.passwordAdminTest, ConfigTest.saltAdminTest);//AUT
			assertTrue("La password non è stata cifrata nel modo corretto", returnedcryptedpassword.equals(ConfigTest.cryptedPasswordAdminTest));//la password cifrata deve essere corretta
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

	@Test
	public final void testGetUsersList() {
		UserHandler testClass = new UserHandler();
		int returnedStatus;
		Response res = testClass.getUsersList();
		returnedStatus = res.getStatus();
		assertTrue("Status diverso da 200", returnedStatus==200);//lo status deve essere 200 OK
		//JSONObject jsonobj = new JSONObject(res.getEntity());

		/* 
		JSONArray jsonarray = new JSONArray(res.getEntity());
		System.out.println(jsonarray.get(1));
		for (int i = 0; i < jsonarray.length(); i++) {
		    JSONObject jsonobject = jsonarray.getJSONObject(i);
		    String name = jsonobject.getString("username");
		}
		
		assertTrue("Username non è lo stesso!", true);//username deve essere lo stesso
		
		*/
	
	}

}
