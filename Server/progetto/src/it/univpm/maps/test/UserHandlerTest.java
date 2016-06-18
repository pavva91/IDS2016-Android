package it.univpm.maps.test;

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
		u.setUsername("test");
		u.setPassword("test");
		res = testClass.createUser(u);
		System.out.println(res.toString());
	//	Equals("Username OK", u.getUsername(), res.getEntity().toString());
		//assertEquals("Username OK", u.getUsername(), u.getUsername());
	}

	@Test
	public final void testUserLogin() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testUpdateUserPosition() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCryptPassword() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetUsersList() {
		fail("Not yet implemented"); // TODO
	}

}
