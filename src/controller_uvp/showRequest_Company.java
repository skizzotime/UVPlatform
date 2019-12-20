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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import interfacce.UserInterface;
import model.Attached;
import model_uvp.DAORichiesta;
import model_uvp.RequestInternship;

/**
 * Servlet implementation class ShowRequest_Teacher
 */
@WebServlet("/showRequest_Company")
public class showRequest_Company extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public showRequest_Company() {
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
		ArrayList<RequestInternship> richieste;
		List<Attached> allegati;
		DAORichiesta queryobj = new DAORichiesta();
		JSONObject jObj;
		JSONArray jArr = new JSONArray();
		JSONObject mainObj = new JSONObject();

		if (currUser != null) 
		{
			email = currUser.getEmail();
			
			try
			{
				richieste = queryobj.viewRequestsCompany(email);

					for(RequestInternship a : richieste)
					{

						jObj = new JSONObject();
						jObj.put("id",a.getId_request_i());
						allegati = a.getAttached();
						for (Attached b : allegati)
						{
							jObj.put("attached","<a href='" + request.getContextPath() + "/Downloader?filename=" + b.getFilename()+ "&idRequest=" + a.getId_request_i() + "'>" + b.getFilename() + "</a><br>");
						}
						jObj.put("theme", a.getUser1());	
						int index = a.getUser2().indexOf("+");
						String nome = a.getUser2().substring(0, index);
						String cognome = a.getUser2().substring(index+1);
						jObj.put("name",nome);
						jObj.put("surname", cognome);
						jObj.put("type", a.getType());
						jArr.add(jObj);
						
		
					

					}
				
			}
			catch(Exception e)
			{
				
				
				e.printStackTrace();
			}
			mainObj.put("data", jArr);
			PrintWriter out = response.getWriter();
			out.println(mainObj);
			response.setContentType("json");
		}
	}
}

