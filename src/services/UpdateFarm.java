package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSON_Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import database.DB_Connection;
import entities.RandomString;
import entities.User;

public class UpdateFarm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFarm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().println("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	StringBuffer jb = new StringBuffer();
	String line;
	String teststring = "{\"username\":\"johnb\",\"session\":\"asdfusw\",\"farm\":{\"name\":\"Johns Farm\",\"picture\":\"johns_farm.png\",\"description\":\"John's Farm liegt im Umland von Mannheim. Sie ist für die gute Milch bekannt.\",\"opening_hours\":\"10:00 am - 4:00 pm\"}}";
	JSONObject jsonobject = new JSONObject();
	JSONParser jsonparser = new JSONParser();
	User db_user = new User();
	
	try {
	    BufferedReader reader = request.getReader();
	    while ((line = reader.readLine()) != null)
	      jb.append(line);
	  } catch (Exception e) { /*report an error*/ }

	
	
	
	// jsonobject = JSON_Server.http_post_json(jb.toString());
	jsonobject = JSON_Server.http_post_json(teststring);
	
	
	JSONObject farm_json;
	farm_json = (JSONObject) jsonobject.get("farm");
	
	
	
	
	
	
	response.getWriter().println(farm_json.toString());

	}

}
