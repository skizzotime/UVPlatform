package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import controller.DbConnection;
import controller.ServletSignup;
import controller_uvp.showRequest_Company;
import interfacce.UserInterface;
import model_uvp.DAOUser;
import model_uvp.User;

public class ShowRequest_CompanyTest {
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private showRequest_Company servlet;
	private JSONObject res;
	private static MockHttpSession session;

	@BeforeAll
	public static void setUp() throws ServletException, IOException, SQLException {
		ServletSignup signup = new ServletSignup();
		MockHttpServletRequest signup_req = new MockHttpServletRequest();
		MockHttpServletResponse signup_res = new MockHttpServletResponse();
		// nuovo studente per il test
		signup_req.addParameter("name", "TESTER");
		signup_req.addParameter("surname", "TESTER");
		signup_req.addParameter("email", "t.tester@studenti.unisa.it");
		signup_req.addParameter("sex", "M");
		signup_req.addParameter("password", "password");
		signup_req.addParameter("flag", "3");
		signup.doPost(signup_req, signup_res);

		UserInterface user = (UserInterface) new User("info@kineton.it", "TESTER", "TESTER", 
				'M', "password", 4, "", "");
		session = new MockHttpSession();
		session.setAttribute("user", user);
		Connection con = new DbConnection().getInstance().getConn();
		PreparedStatement statement = null;

		// nuova richiesta tirocinio esterno
		String req_ext = "INSERT INTO REQUEST_INTERNSHIP (ID_REQUEST_I, TYPE, STATE, FK_USER1, FK_USER2, FK_II, FK_IE) "
				+ "VALUES (1000, 1, \"[AZIENDA] In attesa di accettazione\", \"t.tester@studenti.unisa.it\", \"info@kineton.it\", null, 1)";
		statement = con.prepareStatement(req_ext);

		if(statement.executeUpdate()==1) 
			con.commit();
		else 
			con.rollback();
		
		String req_ext1 = "INSERT INTO REQUEST_INTERNSHIP (ID_REQUEST_I, TYPE, STATE, FK_USER1, FK_USER2, FK_II, FK_IE) "
				+ "VALUES (1001, 1, \"[AZIENDA] Richiesta firmata\", \"t.tester@studenti.unisa.it\", \"info@kineton.it\", null, 1)";
		statement = con.prepareStatement(req_ext1);
		
		if(statement.executeUpdate()==1) 
			con.commit();
		else 
			con.rollback();
		
		String req_ext3 = "INSERT INTO REQUEST_INTERNSHIP (ID_REQUEST_I, TYPE, STATE, FK_USER1, FK_USER2, FK_II, FK_IE) "
				+ "VALUES (1002, 1, \"TESTING\", \"t.tester@studenti.unisa.it\", \"info@kineton.it\", null, 1)";
		statement = con.prepareStatement(req_ext3);
		

		if(statement.executeUpdate()==1) 
			con.commit();
		else 
			con.rollback();
		// nuovo allegato richiesta tirocinio
        String att_int = "INSERT INTO ATTACHED (FILENAME, FK_USER, FK_REQUEST_I) "
                + "VALUES (\"TESTFILE.pdf\", \"t.tester@studenti.unisa.it\" , 1000)";
        statement = con.prepareStatement(att_int);
 
        if(statement.executeUpdate()==1)
            con.commit();
        else
            con.rollback();
	}

	@AfterAll
	public static void tearDown() throws SQLException {
		PreparedStatement statement = null;
		Connection con = new DbConnection().getInstance().getConn();
		// elimina allegati studente
        String sql= "DELETE FROM ATTACHED WHERE FK_USER = \"t.tester@studenti.unisa.it\"";
        statement = con.prepareStatement(sql);
 
        if(statement.executeUpdate()>0)
            con.commit();
        else
            con.rollback();
		// elimina studente per il test
		new DAOUser().removeUser("t.tester@studenti.unisa.it");

		// elimina le richieste effettuate
		String sql1= "DELETE FROM REQUEST_INTERNSHIP WHERE FK_USER1 = \"t.tester@studenti.unisa.it\"";
		statement = con.prepareStatement(sql1);

		if(statement.executeUpdate()>0)
			con.commit();
		else
			con.rollback();
	}
	
	@BeforeEach
	public void init() {
		response = new MockHttpServletResponse();
		request = new MockHttpServletRequest();
		servlet = new showRequest_Company();
		res = new JSONObject();
	}

	@Test
	public void testShowRequest_Company_pass() throws ServletException, IOException, ParseException {
		request.setSession(session);
		servlet.doPost(request, response);
		res = (JSONObject) new JSONParser().parse(response.getContentAsString());
	}
	
	@Test
	public void testShowRequest_Company_fail() throws ServletException, IOException, ParseException {
		servlet.doPost(request, response);
	}
}


