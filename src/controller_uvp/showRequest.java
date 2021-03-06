package controller_uvp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import interfacce.UserInterface;
import model.Attached;
import model_uvp.DAORequest;
import model_uvp.RequestInternship;

/**
 * 
 * Servlet che consente di visualizzare tutte le richieste ad un dato studente.
 * 
 * 
 * @author Antonio Baldi
 *
 */
@WebServlet("/showRequest")
public class showRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public showRequest() {
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
		JSONObject jObj;
		JSONArray jArr = new JSONArray();
		JSONObject mainObj = new JSONObject();
		ArrayList<RequestInternship> requests;
		List<String> attached;
		DAORequest queryobj = new DAORequest();

		if (currUser != null){
			try {
				requests = queryobj.viewRequests(currUser.getEmail());

				if(requests.size()>0)
					for(RequestInternship a : requests){

						attached = new ArrayList<>();
						jObj = new JSONObject();
						jObj.put("id", a.getId_request_i());
						jObj.put("user_serial", a.getStudent().getSerial());

						if(a.getAttached().isEmpty()) {
							jObj.put("attached", "");
						}
						else 
							for (Attached b : a.getAttached())
								attached.add("<a href='" + request.getContextPath() + "/Downloader?flag=1&filename=" + b.getFilename()+ "&idRequest=" + a.getId_request_i() + "'>" + b.getFilename() + "</a>");

						jObj.put("attached", attached);
						if (a.getType() == 0)
							jObj.put("type", "Tirocinio interno");
						else if (a.getType() == 1)
							jObj.put("type", "Tirocinio esterno");
						jObj.put("status", a.getStatus());
						if(a.getStatus().equals("Parzialmente completata"))
							jObj.put("actions", ""
									+ "<label class=\"actionInternship btn btn-default pulse\">"
									+ "<input type='button' data-partial-request='true' data-action='upload' id='"+a.getId_request_i()+"'>" 
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
						else if(a.getStatus().equals("In attesa di caricamento Registro di Tirocinio")) 
							jObj.put("actions", ""
									+ "<label class=\"actionInternship btn btn-default pulse\">"
									+ "<input type='button' data-partial-request='false' data-action='upload' id='"+a.getId_request_i()+"'>" 
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
									+ "<input type='button' data-partial-request='false' data-action='upload' id='"+a.getId_request_i()+"'>" 
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
			catch(Exception e){
				e.printStackTrace();
			}

			mainObj.put("data", jArr);
			PrintWriter out = response.getWriter();
			out.println(mainObj);
			response.setContentType("json");
		}
	}
}
