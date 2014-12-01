package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSON_Server;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**
 * Servlet implementation class TestService
 * TESTSERVICE: No relevance for the application
 * @author Andreas, Anne
 */
public class TestService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor
     * @see HttpServlet#HttpServlet()
     */
    public TestService() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject jsonobject = new JSONObject();
		JSONParser jsonparser = new JSONParser();
		String mJSONData;
		mJSONData = "{\"glossary\": { \"title\": \"example glossary\",	\"GlossDiv\": {  \"title\": \"S\",	\"GlossList\": {  \"GlossEntry\": {  \"ID\": \"SGML\",	\"SortAs\": \"SGML\",\"GlossTerm\": \"Standard Generalized Markup Language\",\"Acronym\": \"SGML\",	\"Abbrev\": \"ISO 8879:1986\",	\"GlossDef\": {\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",	\"GlossSeeAlso\": [\"GML\", \"XML\"]  },\"GlossSee\": \"markup\" } } }  }}";
		
				try {
			jsonparser = new JSONParser();
			jsonobject = (JSONObject)jsonparser.parse(mJSONData);
			
		} 
		catch(ParseException e) {
			// Nothing to do here
		}	
		response.getWriter().println(jsonobject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
