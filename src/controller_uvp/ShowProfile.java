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
import model_uvp.DAORichiesta;
import model_uvp.DAOUtente;
import model_uvp.RequestInternship;
import model_uvp.User;

/**
 * Servlet implementation class ShowProfile
 */
@WebServlet("/ShowProfile")
public class ShowProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		UserInterface currUser = (UserInterface) request.getSession().getAttribute("user"); 
		String email="";
		Integer result = 0;
		String error = "";
		String redirect = "";
		ArrayList<RequestInternship> richieste;
		List<Attached> allegati;
		DAOUtente queryobj = new DAOUtente();
		User userDate = null;
		JSONObject res = new JSONObject();
		try
		{
			
		userDate = queryobj.showUser(currUser.getEmail());
		if(userDate == null)
		{
			result = 0;
			error = "Errore nel caricamento dei dati";
		}
		else
		{
			res.put("name", userDate.getName());
			res.put("surname", userDate.getSurname());
			res.put("email", userDate.getEmail());
			res.put("phone", userDate.getPhone());
			result = 1;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		res.put("result", result);
		res.put("error", error);
		res.put("redirect", redirect);
		PrintWriter out = response.getWriter();
		out.println(res);
		response.setContentType("json");
		
	}

}
