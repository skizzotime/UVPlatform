package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.DbConnection;
import controller.ServletCommon;
import controller.ServletStudent;
import interfacce.UserInterface;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import model.Request;
import model.Student;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ServletStudentTest extends Mockito {
	private ServletStudent servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	/**
	 * Before.
	 */
	@Before
	public void setUp() {
		servlet = new ServletStudent();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	public void testFirstFormPass() throws ServletException, IOException, SQLException {  
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		con.commit();
		request.addParameter("year", "2019");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("serial", "512104365");
		request.addParameter("idEnte", "1");
		request.addParameter("flag", "2");
		UserInterface user = new Student("a.baldi20@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		servlet.doPost(request, response);
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteRequest = "DELETE FROM request WHERE FK_USER = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();

	}
	


	@Test
	public void testFirstFormYearFail() throws ServletException, IOException {  
		request.addParameter("year", "");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("serial", "512104365");
		request.addParameter("idEnte", "1");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormSerialFail() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "1000000000"); //10 cifre test fallito
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("idEnte", "1");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormSerialFail2() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "1000000"); //serial <9
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("idEnte", "1");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormSerialFail3() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "100000000000"); //serial >10
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("idEnte", "1");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}
	@Test
	public void testFirstFormIdEnteFail() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "0");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormExpiryDateFail() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormReleaseDateFail() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormCertificateSerialFail() throws ServletException, IOException {  

		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "");
		request.addParameter("level", "A2");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormLevelFail() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormLevelFail2() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A1234");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormRequestedCfuFail() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A1");
		request.addParameter("requestedCfu", "13");
		request.addParameter("flag", "2");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testFirstFormAlready() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103578");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A1");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("a.prova@studenti.unisa.it", "Giuseppe", 
				"Cirino", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		servlet.doPost(request, response);
	}

	@Test
	public void testFirstFormCatch() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A1");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "2");
		UserInterface user = new Student("l.l@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		servlet.doPost(request, response);
	}

	@Test
	public void testFirstFormFailFlag() throws ServletException, IOException {  
		request.addParameter("year", "2018");
		request.addParameter("serial", "512103579");
		request.addParameter("idEnte", "1");
		request.addParameter("expiryDate", "2020-02-14");
		request.addParameter("releaseDate", "2015-02-14");
		request.addParameter("certificateSerial", "A00000001");
		request.addParameter("level", "A1");
		request.addParameter("requestedCfu", "6");
		request.addParameter("flag", "10");
		UserInterface user = new Student("b.b@studenti.unisa.it", "fdg", "surname", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		servlet.doPost(request, response);
	}

	@Test
	public void testAttachedDbFilenamesLenght() throws ServletException, IOException {
		String[] file = new String[1];
		file[0] = "allegato1.docx";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "1");
		request.getSession().setAttribute("idRequest", 1);
		UserInterface user = new Student("prova00@unisa.it", "Paolo", "Beningno", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "3");
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testAttachedDbFilenamesFail() throws ServletException, IOException {
		String[] file = new String[2];
		file[0] = "allegato1.docx";
		file[1] = "allegato2.docx";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "1");
		request.getSession().setAttribute("idRequest", 1);
		UserInterface user = new Student("prova00@unisa.it", "Paolo", "Beningno", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "3");
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testAttachedDbFilenamesFail2() throws ServletException, IOException {
		String[] file = new String[2];
		file[0] = "allegato1.pdf";
		file[1] = "allegato2.docx";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "1");
		request.getSession().setAttribute("idRequest", 1);
		UserInterface user = new Student("prova00@unisa.it", "Paolo", "Beningno", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "3");
		assertThrows(IllegalArgumentException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	@Test
	public void testAttachedDb() throws ServletException, IOException {
		String[] file = new String[2];
		file[0] = "richiesta.pdf";
		file[1] = "certificato.pdf";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "8");
		request.getSession().setAttribute("idRequest", 8);
		UserInterface user = new Student("a.prova@studenti.unisa.it", "Giuseppe", 
				"Cirino", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "3");
		servlet.doPost(request, response);
		assertEquals("json", response.getContentType());
	}

	@Test
	public void testAttachedDbFail() throws ServletException, IOException {
		String[] file = new String[2];
		file[0] = "richiesta.pdf";
		file[1] = "certificato.pdf";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "10");
		request.getSession().setAttribute("idRequest", 10);
		UserInterface user = new Student("g.cirinella2@studenti.unisa.it", "Giuseppe", 
				"Cirino", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "3");
		servlet.doPost(request, response);
		assertEquals("json", response.getContentType());
	}

	@Test
	public void testAttachedDbEmpty() throws ServletException, IOException {
		String[] file = new String[2];
		file[0] = "allegato1.pdf";
		file[1] = "allegato2.pdf";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "888");
		request.getSession().setAttribute("idRequest", 888);
		UserInterface user = new Student("p.p@studenti.unisa.it", "Giuseppe", 
				"Cirino", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "3");
		servlet.doPost(request, response);
		assertEquals("json", response.getContentType());
	}
	
	@Test
	public void testAttachedDbPass() throws ServletException, IOException, SQLException {
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addRequestEV = "INSERT INTO request (ID_REQUEST, `CERTIFICATE_SERIAL`, `LEVEL`, `RELEASE_DATE`, `EXPIRY_DATE`, `YEAR`, `REQUESTED_CFU`, `SERIAL`, `VALIDATED_CFU`, `FK_USER`, FK_CERTIFIER, FK_STATE) VALUES (111,'sdds', 'A1', '2011-08-19', '2018-08-19', 2020, '3', '0512105521', '3', 'a.baldi20@studenti.unisa.it', 7, 5)\r\n";
		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequestEV);
		statement.executeUpdate();
		con.commit();
		String[] file = new String[2];
		file[0] = "allegato1.pdf";
		file[1] = "allegato2.pdf";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "111");
		request.getSession().setAttribute("idRequest", 111);
		UserInterface user = new Student("a.baldi20@studenti.unisa.it", "Giuseppe", 
				"Cirino", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "3");
		servlet.doPost(request, response);
		assertEquals("json", response.getContentType());
		
		String DeleteAttached = "DELETE FROM attached WHERE FK_USER = 'a.baldi20@studenti.unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteRequest = "DELETE FROM request WHERE ID_REQUEST = 111";
		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		statement.executeUpdate();
		con.commit();
	}
	@Test
	public void testRetrivePass() throws ServletException, IOException, SQLException {
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addRequestEV = "INSERT INTO request (ID_REQUEST, `CERTIFICATE_SERIAL`, `LEVEL`, `RELEASE_DATE`, `EXPIRY_DATE`, `YEAR`, `REQUESTED_CFU`, `SERIAL`, `VALIDATED_CFU`, `FK_USER`, FK_CERTIFIER, FK_STATE) VALUES (111,'sdds', 'A1', '2011-08-19', '2018-08-19', 2020, '3', '0512105521', '3', 'a.baldi20@studenti.unisa.it', 7, 5)\r\n";
		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequestEV);
		statement.executeUpdate();
		con.commit();
		String[] file = new String[2];
		file[0] = "allegato1.pdf";
		file[1] = "allegato2.pdf";
		request.addParameter("filenames[]", file);
		request.addParameter("idRequest", "111");
		request.getSession().setAttribute("idRequest", 111);
		UserInterface user = new Student("a.baldi20@studenti.unisa.it", "Giuseppe", 
				"Cirino", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "4");
		servlet.doPost(request, response);
		assertEquals("json", response.getContentType());
		
		String DeleteAttached = "DELETE FROM attached WHERE FK_USER = 'a.baldi20@studenti.unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteRequest = "DELETE FROM request WHERE ID_REQUEST = 111";
		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		statement.executeUpdate();
		con.commit();
	}

	@Test
	public void testDoGet() throws ServletException, IOException  {
		request.addParameter("name", "Giuseppe");
		request.addParameter("surname", "Cirino");
		request.addParameter("email", "g.c@studenti.unisa.it");
		request.addParameter("sex", "M");
		request.addParameter("password", "password");
		request.addParameter("flag", "1");
		servlet.doGet(request, response);
		assertEquals("json", response.getContentType());
	}

	@Test
	public void testViewRequest() throws ServletException, IOException  {
		UserInterface user = new Student("g.prova@studenti.unisa.it", "Giacomo", "Lorenzin", 'M', "password", 0);
		request.getSession().setAttribute("user", user);
		request.addParameter("flag", "4");
		servlet.doGet(request, response);
		assertEquals("json", response.getContentType());
	}

	@Test
	public void testViewRequestFail() throws ServletException, IOException  {
		UserInterface user = new Student("g.prova@studenti.unisa.it", "Giacomo", "Lorenzin", 'M', "password", 0);
		request.addParameter("flag", "4");
		servlet.doGet(request, response);
		assertEquals("json", response.getContentType());
	}
}
