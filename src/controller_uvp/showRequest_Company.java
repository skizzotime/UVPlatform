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
import model_uvp.DAORequest;
import model_uvp.ExternalInternship;
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserInterface currUser = (UserInterface) request.getSession().getAttribute("user"); 
		String email="";
		ArrayList<RequestInternship> requests;
		DAORequest queryobj = new DAORequest();
		List<String> attached;
		JSONObject jObj;
		JSONArray jArr = new JSONArray();
		JSONObject mainObj = new JSONObject();

		if (currUser != null) 
		{
			email = currUser.getEmail();

			try
			{
				requests = queryobj.viewRequestsCompany(email);

				for(RequestInternship a : requests)
				{
					attached = new ArrayList<>();
					jObj = new JSONObject();
					jObj.put("id", a.getId_request_i());
					jObj.put("theme", ((ExternalInternship) a.getInternship()).getName());

					if(a.getAttached().isEmpty()) {
						jObj.put("attached", "");
					}
					else 
						for (Attached b : a.getAttached())
							attached.add("<a href='" + request.getContextPath() + "/Downloader?flag=1&filename=" + b.getFilename()+ "&idRequest=" + a.getId_request_i() + "'>" + b.getFilename() + "</a>");

					jObj.put("attached", attached);
					jObj.put("name", a.getStudent().getName());
					jObj.put("surname", a.getStudent().getSurname());
					jObj.put("type", "Tirocinio esterno");
					jObj.put("state",a.getStatus());
					if(a.getStatus().equals("[AZIENDA] In attesa di accettazione"))
						jObj.put("actions", ""
								+ "<label class=\"actionInternship btn btn-default\" disabled>" 
								+ "<input type=\"button\" data-action=\"accept\" id=\""+a.getId_request_i() +"\">"
								+ "<span class=\"acceptBtn glyphicon glyphicon-ok\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\">" 
								+ "<input type=\"button\" data-action=\"reject\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"refuseBtn glyphicon glyphicon-remove\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\">"
								+ "<input type=\"button\" data-action=\"upload\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"uploadBtn glyphicon glyphicon-open\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\">"
								+ "<input type=\"button\" data-action=\"download\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"downloadBtn glyphicon glyphicon-save\"></span>" 
								+ "</label>"
								+ "<label class=\"info btn btn-default\">"
								+ "<input type='button' data-type-info='0' data-toggle='modal' data-target='#details' id='"+a.getId_request_i()+"'>" 
								+ "<span class=\"infoBtn glyphicon glyphicon-info-sign\"></span>" 
								+ "</label>");
					else if(a.getStatus().equals("[AZIENDA] Richiesta firmata"))
						jObj.put("actions", ""
								+ "<label class=\"actionInternship btn btn-default\">" 
								+ "<input type=\"button\" data-action=\"accept\" id=\""+a.getId_request_i() +"\">"
								+ "<span class=\"acceptBtn glyphicon glyphicon-ok\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\">" 
								+ "<input type=\"button\" data-action=\"reject\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"refuseBtn glyphicon glyphicon-remove\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\">"
								+ "<input type=\"button\" data-action=\"upload\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"uploadBtn glyphicon glyphicon-open\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\">"
								+ "<input type=\"button\" data-action=\"download\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"downloadBtn glyphicon glyphicon-save\"></span>" 
								+ "</label>"
								+ "<label class=\"info btn btn-default\">"
								+ "<input type='button' data-type-info='0' data-toggle='modal' data-target='#details' id='"+a.getId_request_i()+"'>" 
								+ "<span class=\"infoBtn glyphicon glyphicon-info-sign\"></span>" 
								+ "</label>");
					else
						jObj.put("actions", ""
								+ "<label class=\"actionInternship btn btn-default\" disabled>" 
								+ "<input type=\"button\" data-action=\"accept\" id=\""+a.getId_request_i() +"\">"
								+ "<span class=\"acceptBtn glyphicon glyphicon-ok\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\" disabled>" 
								+ "<input type=\"button\" data-action=\"reject\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"refuseBtn glyphicon glyphicon-remove\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\" disabled>"
								+ "<input type=\"button\" data-action=\"upload\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"uploadBtn glyphicon glyphicon-open\"></span>" 
								+ "</label>"
								+ "<label class=\"actionInternship btn btn-default\">"
								+ "<input type=\"button\" data-action=\"download\" id=\""+a.getId_request_i()+"\">" 
								+ "<span class=\"downloadBtn glyphicon glyphicon-save\"></span>" 
								+ "</label>"
								+ "<label class=\"info btn btn-default\">"
								+ "<input type='button' data-type-info='0' data-toggle='modal' data-target='#details' id='"+a.getId_request_i()+"'>" 
								+ "<span class=\"infoBtn glyphicon glyphicon-info-sign\"></span>" 
								+ "</label>");

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

