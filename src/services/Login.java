package services;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSON_Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import entities.User;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		User peter = new User();
		peter.firstname = "Peter";
		peter.lastname = "Lustig";
		peter.title = "Mr.";      // Mr. or Mrs. (Dr.)
		peter.email = "peter.lustig@vollbio.de";
		peter.username = "peter.lustig@vollbio.de";   // currently equals email
		peter.usertype = "Customer";   // Examples: Farmer, Customer, Barnbay-Pickup, Barnbay-Financials
		peter.passwordhash = "tisch";
		peter.salt = "lol";
		peter.session = "";
		peter.street = "Waldstraﬂe";
		peter.number = "99";
		peter.zip = "68159";
		peter.town = "Mannheim";
		
		
		
		
		JSONObject jsonobject = new JSONObject();

		jsonobject = JSON_Server.wrap_JSON(peter);
		
		response.getWriter().println(jsonobject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		


		
		StringBuffer jb = new StringBuffer();
		String line;
		JSONObject jsonobject = new JSONObject();
		JSONParser jsonparser = new JSONParser();
				
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		
		jsonobject = JSON_Server.http_post_json(jb.toString());
		
			
		response.getWriter().println(jsonobject.toString());
		
	}

}
	
	




/*

{{"user":{"title":"Mr.","firstname":"Peter":{"GlossEntry":{"SortAs":"SGML","GlossDef":{"GlossSeeAlso":["GML","XML"],"para":"A meta-markup language, used to create markup languages such as DocBook."},"GlossSee":"markup","GlossTerm":"Standard Generalized Markup Language","ID":"SGML","Acronym":"SGML","Abbrev":"ISO 8879:1986"}},"title":"S"}}} 

*/