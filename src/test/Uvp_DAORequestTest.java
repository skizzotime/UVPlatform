package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import controller.DbConnection;
import model.Attached;
import model_uvp.DAORequest;
import model_uvp.RequestInternship;
import model_uvp.User;

class Uvp_DAORequestTest {
	DAORequest app;
	List<Attached> allegati;
	ArrayList<RequestInternship> richieste;
	@BeforeEach
	void setup()
	{
		app = new DAORequest();
	}
	@Test
	void testViewRequestsFail() throws SQLException {
		app.viewRequests("");
	}
	@Test
	void testViewRequestsPassNoAttch() throws SQLException {

		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 3, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'In attesa', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		con.commit();
		RequestInternship richiesta = new RequestInternship();
		richieste = app.viewRequests("a.baldi20@studenti.unisa.it");
		richiesta = richieste.get(0);
		assertEquals(111, richiesta.getId_request_i());
		assertEquals(0, richiesta.getType());
		assertEquals("In attesa", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());
		assertEquals(0, richiesta.getType());


		String DeleteRequest = "DELETE FROM request_internship WHERE ID_REQUEST_I = 111";
		String DeleteInternship = "DELETE FROM internship_i WHERE ID_II = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate(); 
		con.commit();
	}
	@Test
	void testViewRequestsPassAttch() throws SQLException {
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 3, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'In attesa', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";
		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		RequestInternship richiesta = new RequestInternship();
		richieste = app.viewRequests("a.baldi20@studenti.unisa.it");
		richiesta = richieste.get(0);

		assertEquals(111, richiesta.getId_request_i());
		assertEquals(0, richiesta.getType());
		assertEquals("In attesa", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());
		assertEquals(0, richiesta.getType());

		String DeleteAttached = "DELETE FROM attached WHERE ID_ATTACHED = 111";
		String DeleteRequest = "DELETE FROM request_internship WHERE ID_REQUEST_I = 111";
		String DeleteInternship = "DELETE FROM internship_i WHERE ID_II = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}
	@Test
	void testViewRequestsPassAttchExternal() throws SQLException {
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 3, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'In attesa', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";
		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		RequestInternship richiesta = new RequestInternship();
		richieste = app.viewRequests("a.baldi20@studenti.unisa.it");
		richiesta = richieste.get(0);

		assertEquals(111, richiesta.getId_request_i());
		assertEquals(1, richiesta.getType());
		assertEquals("In attesa", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());
		assertEquals(1, richiesta.getType());

		String DeleteAttached = "DELETE FROM attached WHERE ID_ATTACHED = 111";
		String DeleteRequest = "DELETE FROM request_internship WHERE ID_REQUEST_I = 111";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}
	
	@Test
	void testRetrieveLatestAttachedFail() {
		app.retrieveAttached(0);
	}
	@Test
	void testRetrieveLatestAttachedPass() throws SQLException {

		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 3, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'In attesa', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";
		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();
		Attached allegato = new Attached();
		allegato=app.retrieveLatestAttached(111);

		assertEquals("prova", allegato.getFilename());



		String DeleteAttached = "DELETE FROM attached WHERE ID_ATTACHED = 111";
		String DeleteRequest = "DELETE FROM request_internship WHERE ID_REQUEST_I = 111";
		String DeleteInternship = "DELETE FROM internship_i WHERE ID_II = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testAddRequestFail() {
		app.addRequest(new RequestInternship());
	}

	@Test
	void testAddRequestFailWithWrongField() {
		allegati = new ArrayList<Attached>();
		RequestInternship prova = new RequestInternship(111, "Parzialmente completata", 0, 99, allegati);
		prova.setStudent(new User("d.capuano2@studenti.unisa.it", "David", "Capuano", 'M', "", 0, "", ""));
		prova.setTutor(new User("no@info.it", "", "", ' ', "", 4, "", ""));
		app.addRequest(prova);
	}
	@Test
	void testAddRequestPassInternal() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		ResultSet result;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 3, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		con.commit();
		RequestInternship Request1 = new RequestInternship();

		Request1.setTutor(new User("slatorre@unisa.it", "Salvatore", "La torre", 'M', "", 3, "", ""));
		Request1.setType(0);
		Request1.setFk_i(111);
		Request1.setStatus("Parzialmente completata");
		Request1.setStudent(new User("a.baldi20@studenti.unisa.it", "Antonio", "Baldi", 'M', "", 0, "", ""));

		app.addRequest(Request1);

		String selectRequest = "SELECT * FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(selectRequest);
		result = statement.executeQuery();

		if(result.next())
		{
			assertEquals(0, result.getInt(2));
			assertEquals("Parzialmente completata", result.getString(3));
			assertEquals("a.baldi20@studenti.unisa.it", result.getString(4));
			assertEquals("slatorre@unisa.it", result.getString(5));
			assertEquals(111, result.getInt(7));
		}

		String DeleteRequest = "DELETE FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternship = "DELETE FROM internship_i WHERE ID_II = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";

		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}


	@Test
	void testAddRequestPassExternal() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		ResultSet result;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		con.commit();
		RequestInternship Request1 = new RequestInternship();


		Request1.setTutor(new User("slatorre@unisa.it", "Salvatore", "La torre", 'M', "", 4, "", ""));
		Request1.setType(1);
		Request1.setFk_i(111);
		Request1.setStatus("Parzialmente completata");
		Request1.setStudent(new User("a.baldi20@studenti.unisa.it", "Antonio", "Baldi", 'M', "", 0, "", ""));


		app.addRequest(Request1);

		String selectRequest = "SELECT * FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(selectRequest);
		result = statement.executeQuery();

		if(result.next())
		{
			assertEquals(1, result.getInt(2));
			assertEquals("Parzialmente completata", result.getString(3));
			assertEquals("a.baldi20@studenti.unisa.it", result.getString(4));
			assertEquals("slatorre@unisa.it", result.getString(5));
			assertEquals(111, result.getInt(6));
		}

		String DeleteRequest = "DELETE FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";

		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testCheckLastPartialRequestFail() {
		app.checkLastPartialRequest("m.pirro2@studenti.unisa.it");
	}

	@Test
	void testCheckLastPartialRequestPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		ResultSet result;
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		con.commit();


		app.checkLastPartialRequest("a.baldi20@studenti.unisa.it");
		String selectRequest = "SELECT * FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(selectRequest);
		result = statement.executeQuery();

		if(result.next())
		{
			assertEquals(1, result.getInt(2));
			assertEquals("Parzialmente completata", result.getString(3));
			assertEquals("a.baldi20@studenti.unisa.it", result.getString(4));
			assertEquals("slatorre@unisa.it", result.getString(5));
			assertEquals(111, result.getInt(6));
		}


		String DeleteRequest = "DELETE FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";

		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testGetStatusFail() {
		app.getStatus(0);
	}
	@Test
	void testGetStatusPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		con.commit();


		assertEquals("Parzialmente completata", app.getStatus(111));


		String DeleteRequest = "DELETE FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";

		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testSetStatusFail() {
		app.setStatus(0, "newStatus");
	}
	@Test
	void testSetStatusPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		ResultSet result;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		con.commit();


		app.setStatus(111, "test");
		String selectRequest = "SELECT STATE FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(selectRequest);
		result = statement.executeQuery();
		if(result.next())
		{
			assertEquals("test", result.getString(1));
		}


		String DeleteRequest = "DELETE FROM REQUEST_INTERNSHIP WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";

		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testAddAttachedFail() {
		app.addAttached(" ", " ", 0);
	}
	@Test
	void testAddAttachedPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		ResultSet result;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		con.commit();


		app.addAttached("test", "a.baldi20@studenti.unisa.it", 111);
		String selectAttach = "SELECT * FROM ATTACHED WHERE FK_REQUEST_I = 111";
		statement= con.prepareStatement(selectAttach);
		result=statement.executeQuery();

		if(result.next())
		{
			assertEquals("test", result.getString(2));
			assertEquals("a.baldi20@studenti.unisa.it", result.getString(4));
			assertEquals(111, result.getInt(5));
		}

		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testUpdateAttachedFail() {
		app.updateAttached(" ", 0);
	}
	@Test
	void testUpdateAttachedPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		ResultSet result;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();


		app.updateAttached("testing", 111);
		String selectAttach = "SELECT * FROM ATTACHED WHERE FK_REQUEST_I = 111";
		statement= con.prepareStatement(selectAttach);
		result=statement.executeQuery();

		if(result.next())
		{
			assertEquals("testing", result.getString(2));
			assertEquals("a.baldi20@studenti.unisa.it", result.getString(4));
			assertEquals(111, result.getInt(5));
		}


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testRetrieveAttachedFail() {
		app.retrieveAttached(-1);
	}
	@Test
	void testRetrieveAttachedPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();


		List<String> result = app.retrieveAttached(111);
		assertEquals("prova", result.get(0));

		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testViewRequestsTeacherFail() throws SQLException {
		app.viewRequestsTeacher("");
	}

	@Test
	void testViewRequestsTeacherPassInternal() throws SQLException {
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		ArrayList<RequestInternship> richieste = new ArrayList<RequestInternship>();
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 3, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'In attesa', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";
		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		richieste=app.viewRequestsTeacher("slatorre@unisa.it");
		RequestInternship richiesta = richieste.get(0);
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("In attesa", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());

		String DeleteAttached = "DELETE FROM attached WHERE ID_ATTACHED = 111";
		String DeleteRequest = "DELETE FROM request_internship WHERE ID_REQUEST_I = 111";
		String DeleteInternship = "DELETE FROM internship_i WHERE ID_II = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testViewRequestsCompanyFail() throws SQLException {
		app.viewRequestsCompany("");
	}

	@Test
	void testViewRequestsCompanyPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		ArrayList<RequestInternship> richieste = new ArrayList<RequestInternship>();
		richieste= app.viewRequestsCompany("slatorre@unisa.it");
		RequestInternship richiesta = richieste.get(0);

		assertEquals(111, richiesta.getId_request_i());
		assertEquals("Parzialmente completata", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());

		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testViewRequestsSecretaryFail() throws SQLException {
		app.viewAllRequests();
	}
	@Test
	void testViewRequestsSecretaryPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;

		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		ArrayList<RequestInternship> richieste = new ArrayList<RequestInternship>();
		richieste=app.viewAllRequests();
		RequestInternship richiesta = richieste.get(0);	
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("Parzialmente completata", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testAcceptRequestByProf_CompanyFail() {
		app.acceptRequestByTutor(0);
	}
	
	@Test
	void testAcceptRequestByProf_CompanyPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;

		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		app.acceptRequestByTutor(111);
		
		ArrayList<RequestInternship> richieste = new ArrayList<RequestInternship>();
		richieste=app.viewAllRequests();
		RequestInternship richiesta = richieste.get(0);	
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("In attesa di caricamento Registro di Tirocinio", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testAcceptRequestBySecretaryFail() {
		app.acceptRequestBySecretary(0);
	}
	@Test
	void testAcceptRequestBySecretaryPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;

		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		app.acceptRequestBySecretary(111);
		
		ArrayList<RequestInternship> richieste = new ArrayList<RequestInternship>();
		richieste=app.viewAllRequests();
		RequestInternship richiesta = richieste.get(0);	
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("[ADMIN] In attesa di accettazione", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testAcceptRequestByAdminFail() {
		app.acceptRequestByAdmin(0);
	}
	
	@Test
	void testAcceptRequestByAdminPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;

		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		app.acceptRequestByAdmin(111);
		
		ArrayList<RequestInternship> richieste = new ArrayList<RequestInternship>();
		richieste=app.viewAllRequests();
		RequestInternship richiesta = richieste.get(0);	
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("[CONCLUSA] Convalidata", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testRejectRequestFail() {
		app.rejectRequest(0);
	}
	@Test
	void testRejectRequestPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;

		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();

		app.rejectRequest(111);
		
		ArrayList<RequestInternship> richieste = new ArrayList<RequestInternship>();
		richieste=app.viewAllRequests();
		RequestInternship richiesta = richieste.get(0);	
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("[CONCLUSA] Non convalidata", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testGetEmailByRequestFail() {
		app.getEmailByRequest(0);
	}
	@Test
	void testGetEmailByRequestPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();


		assertEquals("a.baldi20@studenti.unisa.it",app.getEmailByRequest(111));

		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

	@Test
	void testGetRequestFail() {
		app.getRequest(0);
	}

	@Test
	void testGetRequestExternalPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternshipEx = "INSERT INTO internship_e VALUES ('111', 'Salvatore la torre', 3 , '2019-01-01', 30, 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 1,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', 111, null)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();


		RequestInternship richiesta=app.getRequest(111);
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("Parzialmente completata", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternshipEx = "DELETE FROM internship_e WHERE ID_IE = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternshipEx);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}
	@Test
	void testGetRequestInternalPass() throws SQLException {
		allegati = new ArrayList<Attached>();
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement;
		String addUser = "INSERT INTO user VALUES ('a.baldi20@studenti.unisa.it', 'Antonio', 'Baldi', 'M', 'password', 0, '0512105521', 'null', 'null' )";
		String addUser2 = "INSERT INTO user VALUES ('slatorre@unisa.it', 'Salvatore', 'La torre', 'M', 'password', 4, '92372', 'f2', '8233923932')";
		String addInternship = "INSERT INTO internship_i VALUES ('111', 'Salvatore la torre', 'JAVA' , 20, 'risorse', 'usare poo', 'slatorre@unisa.it')";
		String addRequest = "INSERT INTO request_internship VALUES ('111', 0,  'Parzialmente completata', 'a.baldi20@studenti.unisa.it', 'slatorre@unisa.it', null, 111)";
		String addAttached = "INSERT INTO attached VALUES (111, 'prova', null, 'a.baldi20@studenti.unisa.it', 111)";

		statement = con.prepareStatement(addUser);
		statement.executeUpdate();
		statement = con.prepareStatement(addUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(addInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(addRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(addAttached);
		statement.executeUpdate();
		con.commit();


		RequestInternship richiesta=app.getRequest(111);
		
		assertEquals(111, richiesta.getId_request_i());
		assertEquals("Parzialmente completata", richiesta.getStatus());
		assertEquals("a.baldi20@studenti.unisa.it", richiesta.getStudent().getEmail());
		assertEquals("slatorre@unisa.it", richiesta.getTutor().getEmail());


		String DeleteRequest = "DELETE FROM request_internship WHERE FK_USER1 = 'a.baldi20@studenti.unisa.it'";
		String DeleteInternship = "DELETE FROM internship_i WHERE ID_II = 111";
		String DeleteUser2 = "DELETE FROM user WHERE email = 'slatorre@unisa.it'";
		String DeleteUser = "DELETE FROM user WHERE email = 'a.baldi20@studenti.unisa.it'";
		String DeleteAttached = "DELETE FROM attached WHERE FK_REQUEST_I = 111";

		statement = con.prepareStatement(DeleteAttached);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteRequest);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteInternship);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser2);
		statement.executeUpdate();
		statement = con.prepareStatement(DeleteUser);
		statement.executeUpdate();
		con.commit();
	}

}
