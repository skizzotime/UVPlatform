package controller_uvp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import interfacce.UserInterface;
import model.Attached;
import model_uvp.DAORequest;
import model_uvp.DAOUser;
import model_uvp.RequestInternship;
import model_uvp.User;

/**
 * 
 * Servlet per la gestione del profilo utente, questa permette
 * di avere tutti i campi in nella pagina profilo utente
 * 
 * 
 * @author Antonio Baldi
 *
 */
@WebServlet("/showProfile")
public class showProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public showProfile() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer result = 0;
		String error = "";
		String redirect = "";
		DAOUser queryobj = new DAOUser();
		JSONObject res = new JSONObject();

		User user = null;

		UserInterface currUser = null;
		if (request.getSession().getAttribute("user") != null)
			currUser = (UserInterface) request.getSession().getAttribute("user");
		if(currUser != null) {
			try{
				user = queryobj.getUser(currUser.getEmail());

				if(user == null) {
					result = 0;
					error = "Errore nel caricamento dei dati";
				} else {
					res.put("name", user.getName());
					res.put("surname", user.getSurname());
					res.put("email", user.getEmail());
					res.put("phone", user.getPhone());
					result = 1;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		} else {
			result = 0;
			error = "Si è verificato un errore";
		}


		res.put("result", result);
		res.put("error", error);
		res.put("redirect", redirect);
		PrintWriter out = response.getWriter();
		out.println(res);
		response.setContentType("json");
	}
}
